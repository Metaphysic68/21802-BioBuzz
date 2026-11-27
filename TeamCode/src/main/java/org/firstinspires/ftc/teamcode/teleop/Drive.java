package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;


import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.utils.PoseStorage;

@TeleOp(name = "Drive", group = "TeleOp")
public class Drive extends CommandOpMode {

    private Robot bot;
    private boolean hasStarted = false;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();

        MultipleTelemetry telem = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        GamepadEx driver = new GamepadEx(gamepad1);
        GamepadEx operator = new GamepadEx(gamepad2);

        bot = new Robot(hardwareMap, telem, driver, operator);

        Robot.isRedAlliance = PoseStorage.isRedAlliance;

        Pose startPose;
        if (PoseStorage.hasPose()) {
            // Pose handed off from auto (or a previous teleop run if this one was restarted)
            startPose = PoseStorage.currentPose;
            telem.addData("Start pose", "FROM AUTO %s", startPose);
        } else {
            startPose = Robot.isRedAlliance ? new Pose(52.6736,7.0458, Math.toRadians(90)) : new Pose(141.5-52.67364199577121,141.5-7.045788406232082,Math.toRadians(270));
            telem.addData("Start pose", "DEFAULT %s", startPose);
        }
        bot.follower.setPose(startPose);
        telem.update();
    }

    @Override
    public void run() {
        if (!hasStarted) {
            hasStarted = true;
        }

        bot.run();

        // Keep the stored pose current so restarting teleop mid-match resumes from here
        PoseStorage.save(bot.follower.pose());

        double fwd;
        double lat;
        double rot;

        if (!Robot.isRedAlliance) {
            fwd = gamepad1.left_stick_y;
            lat = gamepad1.left_stick_x;
            rot = -gamepad1.right_stick_x;
        } else {
            fwd = -gamepad1.left_stick_y;
            lat = -gamepad1.left_stick_x;
            rot = -gamepad1.right_stick_x;
        }

        if (!Robot.isAutoAligned){
            DrivePowers powers = ManualDrive.fieldCentric(
                    fwd,
                    lat,
                    rot,
                    bot.follower.pose().heading()
            );
            bot.follower.manual(powers);
        }


    }
}