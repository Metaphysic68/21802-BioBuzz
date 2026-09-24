package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.utils.Timer;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.ConditionalCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.RotationOnlyAutoAlignCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.PoseStorage;

import java.util.List;

public class Robot extends com.seattlesolvers.solverslib.command.Robot {
    public static boolean isAutoAligned;
    public final HardwareMap hMap;
    public final Telemetry telemetry;
    public static GamepadEx DriverGamepad;
    public static GamepadEx OperatorGamepad;
    //public Follower follower;
    //public Elevator elevator;
    //public Intake intake;
    //public Trapdoor trapdoor;
    public Shooter shooter;
    public static boolean isRedAlliance;
    private List<LynxModule> hubs;
    private final Timer loop = new Timer();
    public double loops = 0, lastLoop = 0, loopTime = 0;


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
        //follower = Constants.create(hMap);
        shooter = new Shooter(hMap);
        //elevator = new Elevator(hMap, telemetry);
        //intake = new Intake(hMap,telemetry);
        //trapdoor = new Trapdoor(hMap,telemetry);

        hubs = hMap.getAll(LynxModule.class);
        for (LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }


        //register(elevator);
        //register(intake);
        //register(trapdoor);

    }

    private void configureButtons() {
        /*GamepadButton rightBumper = new GamepadButton(DriverGamepad, GamepadKeys.Button.RIGHT_BUMPER);

        rightBumper.whileHeld(new ConditionalCommand(
                new RotationOnlyAutoAlignCommand(0, follower),
                new RotationOnlyAutoAlignCommand(0, follower),
                () -> isRedAlliance
        ));*/
        /*new Trigger(() -> DriverGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5)
                .whenActive(new ElevatorCommand(elevator, 1200))
                .whenInactive(new ElevatorCommand(elevator, 0));ol*/

        /*new Trigger(() -> DriverGamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5)
                .whenActive(new InstantCommand(()-> intake.setPower(1)))
                .whenInactive(new InstantCommand(()->intake.setPower(0)));

        new Trigger(() -> DriverGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5)
                .whenActive(new InstantCommand(()-> intake.setPower(-1)))
                .whenInactive(new InstantCommand(()->intake.setPower(0)));*/

        /*new Trigger(() -> DriverGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5)
                .whenActive(new InstantCommand(()-> trapdoor.setPos(1)))
                .whenInactive(new InstantCommand(()->trapdoor.setPos(0)));*/





    }

    public double getLoopTimeHz() {
        return 1000 / loopTime;
    }


    @Override
    public void run() {
        //if (follower != null) {


            loops++;

            if (loops > 10) {
                double now = loop.milliseconds();
                loopTime = (now - lastLoop) / loops;
                lastLoop = now;
                loops = 0;
            }
            shooter.setTargetVelocity(1000);

            telemetry.addData("Loop Time (ms)", "%.2f", loopTime);
            telemetry.addData("Loop Rate (Hz)", "%.1f", getLoopTimeHz());
            telemetry.addData("Team", isRedAlliance ? "Red" : "Blue");
            //telemetry.addData("Elevator Target", elevator.getTargetPosition());
            //telemetry.addData("Elevator Position", elevator.getPos());
            //telemetry.addData("Elevator At Target", elevator.isAtTarget());
            telemetry.update();
            //follower.update();
        //}
        super.run();
    }
}