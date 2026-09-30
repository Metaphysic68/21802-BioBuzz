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

        if (PoseStorage.currentPose != null) {
            bot.follower.setPose(new Pose(0,0));
            PoseStorage.currentPose = null;
        } else {
            bot.follower.setPose(Robot.isRedAlliance ? new Pose(53.02800466744456,6.183780630105013,Math.toRadians(180)) : new Pose(0,0,Math.toRadians(180)));
        }
    }

    @Override
    public void run() {
        if (!hasStarted) {
            hasStarted = true;
        }

        super.run();
        bot.run();


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