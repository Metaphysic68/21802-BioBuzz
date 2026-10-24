package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.utils.Timer;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Flower;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Stopper;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.utils.LookUpTable;
import org.firstinspires.ftc.teamcode.utils.PoseStorage;

import java.util.List;

public class Robot extends com.seattlesolvers.solverslib.command.Robot {
    public static boolean isAutoAligned;
    public final HardwareMap hMap;
    public final Telemetry telemetry;
    public static GamepadEx DriverGamepad;
    public static GamepadEx OperatorGamepad;
    public Follower follower;
    public Flower flower;
    public Intake intake;
    public Shooter shooter;
    public Stopper stopper;
    public Turret turret;

    public static boolean isRedAlliance;
    private List<LynxModule> hubs;
    private final Timer loop = new Timer();
    private final Timer volt = new Timer();

    public double loops = 0, lastLoop = 0, loopTime = 0;
    public LookUpTable storage;


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
        follower = Constants.create(hMap);
        storage = new LookUpTable();
        shooter = new Shooter(hMap,storage);
        turret = new Turret(hMap,storage);
        stopper = new Stopper(hMap);
        intake = new Intake(hMap);
        flower = new Flower(hMap);

        hubs = hMap.getAll(LynxModule.class);
        for (LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        register(shooter);
        register(turret);
        register(stopper);
        register(intake);
        register(flower);

    }

    private void configureButtons() {
        new Trigger(() -> DriverGamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5)
                .whenActive(new InstantCommand(() -> {
                    stopper.setPos(0);
                    intake.setPower(1);
                }))
                .whenInactive(new InstantCommand(() -> {
                    intake.setPower(0.2);
                    stopper.setPos(0);
                }));

        new Trigger(() -> DriverGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5)
                .whenActive(new InstantCommand(() -> {
                    intake.setPower(1);
                    stopper.setPos(0.2);
                }))
                .whenInactive(new InstantCommand(() ->
                        intake.setPower(0)
                ));

        GamepadButton dPadDown = new GamepadButton(DriverGamepad, GamepadKeys.Button.DPAD_DOWN);

        dPadDown.whenActive(new InstantCommand(() -> {
                    intake.setPower(-1);
                    stopper.setPos(0.2);
                }))
                .whenInactive(new InstantCommand(() -> intake.setPower(0)));

        GamepadButton leftBumper = new GamepadButton(DriverGamepad, GamepadKeys.Button.LEFT_BUMPER);

        leftBumper.whenActive(new InstantCommand(() -> {
                    flower.setPos(0.425);
                }))
                .whenInactive(new InstantCommand(() -> {
                    flower.setPos(0);
                }));



    }

    public double getLoopTimeHz() {
        return 1000 / loopTime;
    }


    @Override
    public void run() {
        if (follower != null) {
            double voltage = 12.0;

            if (volt.milliseconds()>250 && !hubs.isEmpty()){
                voltage = hubs.get(0).getInputVoltage(VoltageUnit.VOLTS);
                volt.reset();
            }

            loops++;

            if (loops > 10) {
                double now = loop.milliseconds();
                loopTime = (now - lastLoop) / loops;
                lastLoop = now;
                loops = 0;
            }
            shooter.setVoltage(voltage);

            turret.setTargetAngleRED(follower.pose(),follower.velocity());

            telemetry.addData("Loop Time (ms)", "%.2f", loopTime);
            telemetry.addData("Loop Rate (Hz)", "%.1f", getLoopTimeHz());
            telemetry.addData("Team", isRedAlliance ? "Red" : "Blue");

            telemetry.addData("Current Velocity - Shooter","%.2f",shooter.getCurrentVelocity());
            telemetry.addData("Target Velocity - Shooter","%.2f", shooter.getTargetVelocity());
            telemetry.addData("Off Velocity - Shooter", "%.2f", Math.abs(shooter.getTargetVelocity() -shooter.getCurrentVelocity()));
            telemetry.addData("Power - ShooterT", "%.2f", shooter.getPowerTop());
            telemetry.addData("Power - ShooterB", "%.2f", shooter.getPowerBottom());


            telemetry.addData("Current Pos - Hood", "%.2f",shooter.getHoodPos());
            telemetry.addData("Target Pos - Hood", "%.2f",shooter.getTargetPos());

            telemetry.addData("Pose", follower.pose());
            telemetry.addData("Turret Degrees", turret.getTargetAngle());

            telemetry.addData("Voltage", voltage);


            telemetry.update();
            follower.update();
        }
        super.run();
    }
}