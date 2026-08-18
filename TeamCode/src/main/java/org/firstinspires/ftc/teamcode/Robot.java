package org.firstinspires.ftc.teamcode;

import static com.arcrobotics.ftclib.kotlin.extensions.gamepad.GamepadExExtKt.whenInactive;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.commands.ElevatorCommand;
//import org.firstinspires.ftc.teamcode.commands.RunIntake;
//import org.firstinspires.ftc.teamcode.commands.RunOuttake;
//import org.firstinspires.ftc.teamcode.commands.SetStopperPosition;
import org.firstinspires.ftc.teamcode.commands.SetStopperPosition;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.FieldLocations;
//import org.firstinspires.ftc.teamcode.subsystems.Elevator;
//import org.firstinspires.ftc.teamcode.subsystems.Intake;
//import org.firstinspires.ftc.teamcode.subsystems.Outtake;
//import org.firstinspires.ftc.teamcode.subsystems.Stopper;
import org.firstinspires.ftc.teamcode.utils.PoseStorage;
import org.firstinspires.ftc.teamcode.utils.RobotConstants;

public class Robot extends com.arcrobotics.ftclib.command.Robot {
    public static boolean isAutoAligned;
    public final HardwareMap hMap;
    public final Telemetry telemetry;
    public static GamepadEx DriverGamepad;
    public static GamepadEx OperatorGamepad;
    public Follower follower;
   // public Elevator elevator;
    // public Stopper stopper;
   // public Intake intake;
  //  public Outtake outtake;
    public static boolean isRedAlliance;

    public Robot(HardwareMap hMap, Telemetry telemetry, GamepadEx driverGamepad, GamepadEx operatorGamepad) {
        this.hMap = hMap;
        this.telemetry = telemetry;
        DriverGamepad = driverGamepad;
        OperatorGamepad = operatorGamepad;
        this.isRedAlliance = PoseStorage.isRedAlliance;

        initSubsystems();
        configureButtons();
    }

    private void initSubsystems() {
        follower = Constants.createFollower(hMap);
        //  stopper  = new Stopper(this);
     //   intake   = new Intake(this);
       // outtake  = new Outtake(this);
       // elevator = new Elevator(hMap, telemetry);
      //  register(elevator);
    }

    private void configureButtons() {
        new Trigger(() -> DriverGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5)
                .whenActive(new ParallelCommandGroup(
                 //       new ElevatorCommand(elevator, 1200)

                        //new SetStopperPosition(stopper, RobotConstants.Stopper.POSITION_OPEN)
                ));
              //  .whenInactive(new ElevatorCommand(elevator, 0));
      //  .whenInactive(new SetStopperPosition(stopper, RobotConstants.Stopper.POSITION_OPEN);



        new Trigger(() -> DriverGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5)
                .whileActiveOnce(new ParallelCommandGroup(
                    //    new RunIntake(intake, RobotConstants.Intake.SPEED_INTAKE),
                     //   new RunOuttake(outtake, RobotConstants.Outtake.SPEED_OUTTAKE)
                        //new SetStopperPosition(stopper, RobotConstants.Stopper.POSITION_CLOSED)
                ));

        new GamepadButton(DriverGamepad, GamepadKeys.Button.Y)
                .whileActiveOnce(new InstantCommand(() -> follower.setPose(isRedAlliance
                        ? new Pose(56, 99, 0)
                        : new Pose(144 - 56, 99, Math.PI))));



        new GamepadButton(DriverGamepad, GamepadKeys.Button.X)
                .whenPressed(new SequentialCommandGroup(
                        new InstantCommand(this::prepareNewTask),
                        new InstantCommand(() -> {
                            isAutoAligned = true;
                            Pose current = follower.getPose();
                            Pose target  = FieldLocations.AutoStraight.StartPose;
                            Path path = new Path(new BezierLine(current, target));
                            path.setLinearHeadingInterpolation(current.getHeading(), target.getHeading());
                            follower.followPath(path, true);
                        }),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> {
                            isAutoAligned = false;
                            follower.startTeleopDrive(true);
                        })
                ));

        new GamepadButton(DriverGamepad, GamepadKeys.Button.A)
                .whenPressed(new SequentialCommandGroup(
                        new InstantCommand(this::prepareNewTask),
                        new InstantCommand(() -> {
                            isAutoAligned = true;
                            Pose current = follower.getPose();
                            Pose target  = FieldLocations.AutoStraight.Intake;
                            Path path = new Path(new BezierLine(current, target));
                            path.setLinearHeadingInterpolation(current.getHeading(), target.getHeading());
                            follower.followPath(path, true);
                        }),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> {
                            isAutoAligned = false;
                            follower.startTeleopDrive(true);
                        })
                ));
    }
    //ola

    public void prepareNewTask() {
        follower.breakFollowing();
        follower.startTeleopDrive(true);
        isAutoAligned = false;
    }

    @Override
    public void run() {
        if (follower != null) {
            follower.update();

            Pose currentPose = follower.getPose();

            telemetry.addData("RobotPose", new double[]{currentPose.getX(), currentPose.getY(), currentPose.getHeading()});

            telemetry.addData("Chassis X", "%.4f", currentPose.getX());
            telemetry.addData("Chassis Y", "%.4f", currentPose.getY());
            telemetry.addData("Chassis Heading", "%.6f", currentPose.getHeading());
            telemetry.addData("Team", isRedAlliance ? "Red" : "Blue");

          //  telemetry.addData("Elevator Target", elevator.getTargetPosition());
         //   telemetry.addData("Elevator Position", elevator.getPos());
         //   telemetry.addData("Elevator At Target", elevator.isAtTarget());

         //   telemetry.addData("Intake Power", intake.getPower());
           // telemetry.addData("Outtake Power", outtake.getPower());

            // telemetry.addData("Stopper Status", stopper);

            telemetry.update();
        }
        super.run();
    }
}