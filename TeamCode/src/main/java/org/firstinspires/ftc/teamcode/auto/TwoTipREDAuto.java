package org.firstinspires.ftc.teamcode.auto;


import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.paths.TwoTipRED;
import org.firstinspires.ftc.teamcode.utils.PoseStorage;

@Autonomous(name = "TwoTipREDAuto", group = "Autos", preselectTeleOp = "Drive")
public class TwoTipREDAuto extends LinearOpMode {

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().reset();

        PoseStorage.currentPose = null;
        PoseStorage.isRedAlliance = true;

        Robot bot = new Robot(hardwareMap, telemetry, new GamepadEx(gamepad1), new GamepadEx(gamepad2));
        bot.isRedAlliance = true;

        bot.follower.setPose(new Pose(54.4172, 6, Math.toRadians(180)));

        waitForStart();

        if (opModeIsActive()) {
           CommandScheduler.getInstance().schedule(new TwoTipRED(bot.follower, bot.intake,bot.stopper,bot.flower));
        }

        while (opModeIsActive() && !isStopRequested()) {
            bot.run();

            PoseStorage.currentPose = bot.follower.pose();
        }

        CommandScheduler.getInstance().reset();
    }
}