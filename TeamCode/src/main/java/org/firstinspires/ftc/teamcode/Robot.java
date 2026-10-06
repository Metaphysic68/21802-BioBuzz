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
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.utils.PoseStorage;

import java.util.List;

public class Robot extends com.seattlesolvers.solverslib.command.Robot {
    public static boolean isAutoAligned;
    public final HardwareMap hMap;
    public final Telemetry telemetry;
    public static GamepadEx DriverGamepad;
    public static GamepadEx OperatorGamepad;
    public Follower follower;



    public Drive Manejar;
    public static boolean isRedAlliance;
    private List<LynxModule> hubs;
    private final Timer loop = new Timer();
    public double loops = 0, lastLoop = 0, loopTime = 0;

    public double motor1 ;
    public double motor2 ;
    public double motor3;
    public double motor4;



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
        Manejar = new Drive(hMap);


        hubs = hMap.getAll(LynxModule.class);
        for (LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }


        //register(elevator);
        //register(intake);
        //register(trapdoor);

    }

    private void configureButtons() {
        GamepadButton rightBumper = new GamepadButton(DriverGamepad, GamepadKeys.Button.RIGHT_BUMPER);

        rightBumper.whileHeld(new ConditionalCommand(
                new RotationOnlyAutoAlignCommand(0, follower),
                new RotationOnlyAutoAlignCommand(0, follower),
                () -> isRedAlliance


        double y = -DriverGamepad.getLeftY();
        double x = DriverGamepad.getLeftX();
        double rot = DriverGamepad.getRightX();


           motor1 = y + x + rot;
          motor2 = y - x - rot;
         motor3 = y - x + rot;
        motor4 = y + x - rot;






    }






    }

    public double getLoopTimeHz() {
        return 1000 / loopTime;
    }


    @Override
    public void run() {
        if (follower != null) {


            loops++;

            if (loops > 10) {
                double now = loop.milliseconds();
                loopTime = (now - lastLoop) / loops;
                lastLoop = now;
                loops = 0;
            }

            telemetry.addData("Loop Time (ms)", "%.2f", loopTime);
            telemetry.addData("Loop Rate (Hz)", "%.1f", getLoopTimeHz());
            telemetry.addData("Team", isRedAlliance ? "Red" : "Blue");
            //telemetry.addData("Elevator Target", elevator.getTargetPosition());
            //telemetry.addData("Elevator Position", elevator.getPos());
            //telemetry.addData("Elevator At Target", elevator.isAtTarget());
            telemetry.update();
            follower.update();
        }
        super.run();
    }
}