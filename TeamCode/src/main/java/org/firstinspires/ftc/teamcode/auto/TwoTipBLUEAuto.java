package org.firstinspires.ftc.teamcode.auto;


import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.paths.TwoTipBLUE;
import org.firstinspires.ftc.teamcode.paths.TwoTipRED;
import org.firstinspires.ftc.teamcode.utils.PoseStorage;

@Autonomous(name = "TwoTipBLUEAuto", group = "Autos", preselectTeleOp = "Drive")
public class TwoTipBLUEAuto extends LinearOpMode {

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().reset();

        PoseStorage.currentPose = null;
        PoseStorage.isRedAlliance = false;

        Robot bot = new Robot(hardwareMap, telemetry, new GamepadEx(gamepad1), new GamepadEx(gamepad2));
        bot.isRedAlliance = false;

        // Same start as TwoTipRED, mirrored through (70.75, 70.75) to match the TwoTipBLUE path
        bot.follower.setPose(new Pose(87.0828, 135.5, Math.toRadians(0)));

        waitForStart();

        if (opModeIsActive()) {
            CommandScheduler.getInstance().schedule(new TwoTipBLUE(bot.follower, bot.intake,bot.stopper,bot.flower));
        }

        while (opModeIsActive() && !isStopRequested()) {
            bot.run();

            PoseStorage.currentPose = bot.follower.pose();
        }

        CommandScheduler.getInstance().reset();
    }
}