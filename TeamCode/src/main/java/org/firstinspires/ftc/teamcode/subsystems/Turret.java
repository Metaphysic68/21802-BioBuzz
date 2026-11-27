package org.firstinspires.ftc.teamcode.subsystems;

import static com.seattlesolvers.solverslib.util.MathUtils.clamp;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Velocity;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.utils.LookUpTable;
import org.firstinspires.ftc.teamcode.utils.RobotConstants;
import org.firstinspires.ftc.teamcode.utils.SOTM;

public class Turret extends SubsystemBase {

    private final Servo turret;
    private final Servo turret2;
    private final LookUpTable storage;
    public double targetAngle;
    public double targetPosition;


    @Config
    public static class TurretConstants {
        public static double servoRange = 320;
        public static double centerOffsetDegrees = 0;
        public static double pivotOffsetX = -1.81;
        public static double pivotOffsetY = 0.0;
    }

    public Turret(HardwareMap hardwareMap, LookUpTable storage) {
        turret = hardwareMap.get(Servo.class, "turret");
        turret2 = hardwareMap.get(Servo.class, "turret2");

        turret.setPosition(0.5);
        turret2.setPosition(0.5);
        this.storage = storage;
    }
    public void setTargetAngleRED(Pose robotPose, Velocity robotVelocity){
        if (robotPose.y() > 72){
            setAngle(RobotConstants.RED_GOAL_LEFT, robotPose, robotVelocity);
        } else {
            setAngle(RobotConstants.RED_GOAL_RIGHT, robotPose, robotVelocity);
        }
    }
    public void setTargetAngleBLUE(Pose robotPose, Velocity robotVelocity){
        if (robotPose.y() < 72){
            setAngle(RobotConstants.BLUE_GOAL_LEFT, robotPose, robotVelocity);
        } else {
            setAngle(RobotConstants.BLUE_GOAL_RIGHT, robotPose, robotVelocity);
        }
    }

    private void setAngle(Pose goal, Pose robotPose, Velocity robotVelocity){
        Pose virtualPose = SOTM.calculateVirtualRobot(robotPose, robotVelocity,storage.getTime(goal.distance(robotPose)));

        Pose turretPose = getTurretPose(robotPose);

        double fieldRadians = Math.atan2(goal.y() - turretPose.y(), goal.x() - turretPose.x());
        double angleDegrees = Math.toDegrees(fieldRadians - turretPose.heading());

        while (angleDegrees > 180) angleDegrees -= 360;
        while (angleDegrees <= -180) angleDegrees += 360;

        double TURRET_MIN_ANGLE_DEGREES = -110.0;
        // Can turn 135 deg right
        double TURRET_MAX_ANGLE_DEGREES = 110.0;
        double MaxAngleDegrees = Math.max(TURRET_MIN_ANGLE_DEGREES,
                Math.min(TURRET_MAX_ANGLE_DEGREES, angleDegrees));

        targetAngle = MaxAngleDegrees;

        double pos = 0.5 + (MaxAngleDegrees+TurretConstants.centerOffsetDegrees) / TurretConstants.servoRange;

        targetPosition = clamp(pos,0,1);
        turret.setPosition(targetPosition);
        turret2.setPosition(targetPosition);

    }

    /** Field position of the turret pivot, from Pedro's pose rotated by heading. */
    private Pose getTurretPose(Pose robotPose) {
        double h = robotPose.heading();
        double cos = Math.cos(h);
        double sin = Math.sin(h);
        double x = robotPose.x() + TurretConstants.pivotOffsetX * cos - TurretConstants.pivotOffsetY * sin;
        double y = robotPose.y() + TurretConstants.pivotOffsetX * sin + TurretConstants.pivotOffsetY * cos;
        return new Pose(x, y, h);
    }

    public double getTargetAngle(){
        return targetAngle;
    }

    public double getPosition(){
        return turret.getPosition();
    }

    public void setTargetPosition(double targetPosition) {
        turret.setPosition(targetPosition);
        turret2.setPosition(targetPosition);

    }
}
