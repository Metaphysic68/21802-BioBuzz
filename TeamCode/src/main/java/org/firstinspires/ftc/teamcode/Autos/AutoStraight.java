package org.firstinspires.ftc.teamcode.Autos;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.AutoStraightRutine;
import org.firstinspires.ftc.teamcode.pedroPathing.FieldLocations;
import org.firstinspires.ftc.teamcode.utils.PoseStorage;
//import org.firstinspires.ftc.teamcode.subsystems.Intake;
//import org.firstinspires.ftc.teamcode.commands.RunIntake;

@Autonomous(name = "AutoStraight", group = "Autos", preselectTeleOp = "Drive")
public class AutoStraight extends LinearOpMode {

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().reset();

        PoseStorage.currentPose = null;
        PoseStorage.isRedAlliance = true;

        Robot bot = new Robot(hardwareMap, telemetry, new GamepadEx(gamepad1), new GamepadEx(gamepad2));
        bot.isRedAlliance = true;

        bot.follower.setStartingPose(FieldLocations.AutoStraight.StartPose);

        waitForStart();

        if (opModeIsActive()) {
            CommandScheduler.getInstance().schedule(new AutoStraightRutine(bot.follower, null));
        }

        while (opModeIsActive() && !isStopRequested()) {
            bot.run();

            PoseStorage.currentPose = bot.follower.getPose();
            telemetry.update();
        }

        CommandScheduler.getInstance().reset();
    }
}