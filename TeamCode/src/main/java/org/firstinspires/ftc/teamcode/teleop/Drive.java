package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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

        bot.isRedAlliance = PoseStorage.isRedAlliance;

        if (PoseStorage.currentPose != null) {
            bot.follower.setStartingPose(PoseStorage.currentPose);
            PoseStorage.currentPose = null;
        } else {
            bot.follower.setStartingPose(bot.isRedAlliance ? new Pose(0,0,0) : new Pose(0,0,Math.toRadians(180)));
        }
    }

    @Override
    public void run() {
        if (!hasStarted) {
            bot.follower.startTeleopDrive(true);
            hasStarted = true;
        }

        super.run();
        bot.run();


        double fwd;
        double lat;
        double rot;

        if (!bot.isRedAlliance) {
            fwd = gamepad1.left_stick_y;
            lat = gamepad1.left_stick_x;
            rot = -gamepad1.right_stick_x;
        } else {
            fwd = -gamepad1.left_stick_y;
            lat = -gamepad1.left_stick_x;
            rot = -gamepad1.right_stick_x;
        }

        if (!Robot.isAutoAligned){
            bot.follower.setTeleOpDrive(fwd, lat, rot, false);
        }


    }
}