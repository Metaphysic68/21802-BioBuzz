package org.firstinspires.ftc.teamcode.auto;


import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.paths.AutoRunPath;
import org.firstinspires.ftc.teamcode.utils.PoseStorage;

@Autonomous(name = "AutoRun", group = "Autos", preselectTeleOp = "Drive")
public class AutoRun extends LinearOpMode {

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().reset();

        PoseStorage.currentPose = null;
        PoseStorage.isRedAlliance = true;

        Robot bot = new Robot(hardwareMap, telemetry, new GamepadEx(gamepad1), new GamepadEx(gamepad2));
        bot.isRedAlliance = true;

        bot.follower.setPose(new Pose(72,72,0));

        waitForStart();

        if (opModeIsActive()) {
            CommandScheduler.getInstance().schedule(new AutoRunPath(bot.follower));
        }

        while (opModeIsActive() && !isStopRequested()) {
            CommandScheduler.getInstance().run();
            bot.run();

            PoseStorage.currentPose = bot.follower.pose();
            telemetry.update();
        }

        CommandScheduler.getInstance().reset();
    }
}