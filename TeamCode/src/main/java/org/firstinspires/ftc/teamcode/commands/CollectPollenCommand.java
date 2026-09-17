package org.firstinspires.ftc.teamcode.commands;

import static com.pedropathing.api.Paths.line;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.vision.Limelight;

/**
 * Repeats until the timer runs out: wait still -> find the closest allowed ball -> drive a straight
 * line to it with the robot facing along the line -> look again.
 */
public class CollectPollenCommand extends CommandBase {

    @Config
    public static class PollenConstants {
        public static double cameraHeightIn = 8.0;
        public static double cameraPitchDownDeg = 20.0;
        public static double cameraForwardOffsetIn = 6.0;
        public static double cameraLeftOffsetIn = 0.0;
        public static double pollenCenterHeightIn = 2.5;
        public static double pickupOffsetIn = 4.0;
        public static double forbiddenMinX = 62;
        public static double forbiddenMinY = 62;
        public static double settleMs = 200;
        public static int confirmFrames = 3;
        public static double confirmToleranceIn = 4.0;
        public static double driveTimeoutMs = 3000;
        public static long maxStalenessMs = 100;
    }

    private static final double METERS_PER_INCH = 0.0254;

    private enum State { SEARCHING, DRIVING }

    private final Follower follower;
    private final int pipeline;
    private final double timeoutMs;
    private final ElapsedTime timer = new ElapsedTime();
    private final ElapsedTime stateTimer = new ElapsedTime();

    private State state;
    private Pose candidate;
    private int confirmedFrames;
    private double lastFrameTimestamp;

    public CollectPollenCommand(Follower follower, int pipeline, double timeoutMs) {
        this.follower = follower;
        this.pipeline = pipeline;
        this.timeoutMs = timeoutMs;
    }

    @Override
    public void initialize() {
        timer.reset();
        Limelight.changePipeline(pipeline);
        startSearching();
    }

    @Override
    public void execute() {
        if (state == State.DRIVING) {
            if (follower.atParametricEnd() || stateTimer.milliseconds() > PollenConstants.driveTimeoutMs) {
                startSearching();
            }
            return;
        }

        // measure only once the robot has stopped, so the pose matches the frame
        if (stateTimer.milliseconds() < PollenConstants.settleMs) return;

        LLResult result = Limelight.getLatestResult();
        // the scheduler can run twice per loop; only use each camera frame once
        if (result == null || !result.isValid() || result.getStaleness() > PollenConstants.maxStalenessMs
                || result.getTimestamp() == lastFrameTimestamp) {
            return;
        }
        lastFrameTimestamp = result.getTimestamp();

        Pose robot = follower.pose();
        Pose ball = closestAllowedBall(result, robot);
        if (ball == null) {
            candidate = null;
            confirmedFrames = 0;
            return;
        }

        // a single frame can be a reflection; require the same spot a few frames in a row
        boolean sameBall = candidate != null && candidate.distance(ball) <= PollenConstants.confirmToleranceIn;
        confirmedFrames = sameBall ? confirmedFrames + 1 : 1;
        candidate = ball;
        if (confirmedFrames >= PollenConstants.confirmFrames) {
            driveTo(robot, candidate);
        }
    }

    @Override
    public boolean isFinished() {
        return timer.milliseconds() >= timeoutMs;
    }

    @Override
    public void end(boolean interrupted) {
        follower.hold(follower.pose());
    }

    private Pose closestAllowedBall(LLResult result, Pose robot) {
        Pose best = null;
        for (LLResultTypes.ColorResult target : result.getColorResults()) {
            Pose ball = ballPosition(target.getTargetXDegrees(), target.getTargetYDegrees(), robot);
            if (ball == null || isForbidden(ball)) continue;
            if (best == null || robot.distance(ball) < robot.distance(best)) best = ball;
        }
        return best;
    }

    private static boolean isForbidden(Pose ball) {
        return ball.x() > PollenConstants.forbiddenMinX && ball.y() > PollenConstants.forbiddenMinY;
    }

    /**
     * tx/ty of one target -> the ball's field position. getDistanceToPollen gives the distance along
     * the camera's forward axis. Because the camera is tilted down, the sideways offset is slightly
     * more than forward * tan(tx); dividing by (cos(pitch) + tan(ty) * sin(pitch)) makes it exact.
     */
    public static Pose ballPosition(double txDeg, double tyDeg, Pose robot) {
        Double forwardM = Limelight.getDistanceToPollen(
                tyDeg,
                PollenConstants.pollenCenterHeightIn * METERS_PER_INCH,
                PollenConstants.cameraHeightIn * METERS_PER_INCH,
                PollenConstants.cameraPitchDownDeg);
        if (forwardM == null) return null;

        double forward = forwardM / METERS_PER_INCH;
        double pitch = Math.toRadians(PollenConstants.cameraPitchDownDeg);
        // tx is positive to the right; the robot frame's +y is left
        double left = -forward * Math.tan(Math.toRadians(txDeg))
                / (Math.cos(pitch) + Math.tan(Math.toRadians(tyDeg)) * Math.sin(pitch));

        double robotX = PollenConstants.cameraForwardOffsetIn + forward;
        double robotY = PollenConstants.cameraLeftOffsetIn + left;

        double h = robot.heading();
        return new Pose(
                robot.x() + robotX * Math.cos(h) - robotY * Math.sin(h),
                robot.y() + robotX * Math.sin(h) + robotY * Math.cos(h));
    }

    private void driveTo(Pose robot, Pose ball) {
        double distance = robot.distance(ball);
        if (distance <= PollenConstants.pickupOffsetIn) {
            startSearching();
            return;
        }

        double ux = (ball.x() - robot.x()) / distance;
        double uy = (ball.y() - robot.y()) / distance;
        Pose end = new Pose(ball.x() - ux * PollenConstants.pickupOffsetIn, ball.y() - uy * PollenConstants.pickupOffsetIn);

        follower.follow(line(robot, end).tangent());
        state = State.DRIVING;
        stateTimer.reset();
    }

    private void startSearching() {
        follower.hold(follower.pose());
        candidate = null;
        confirmedFrames = 0;
        state = State.SEARCHING;
        stateTimer.reset();
    }
}
