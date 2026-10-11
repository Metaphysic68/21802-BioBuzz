package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.utils.Timer;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.PoseStorage;

import java.util.List;

public class Robot extends com.seattlesolvers.solverslib.command.Robot {
    public static boolean isAutoAligned;
    public final HardwareMap hMap;
    public final Telemetry telemetry;
    public static GamepadEx DriverGamepad;
    public static GamepadEx OperatorGamepad;



    public Drive Manejar;
    public Shooter Flywheel;
    public static boolean isRedAlliance;
    private List<LynxModule> hubs;
    private final Timer loop = new Timer();
    public double loops = 0, lastLoop = 0, loopTime = 0;

    public double motor1 ;
    public double motor2 ;
    public double motor3;
    public double motor4;

    public double TriggerD;
    Gamepad g1;
    Gamepad g2;



    public Robot(HardwareMap hMap, Telemetry telemetry, GamepadEx driverGamepad, GamepadEx operatorGamepad, Gamepad g1, Gamepad g2) {
        this.hMap = hMap;
        this.telemetry = telemetry;
        this.g1 = g1;
        this.g2 = g2;

        DriverGamepad = driverGamepad;
        OperatorGamepad = operatorGamepad;
        this.isRedAlliance = PoseStorage.isRedAlliance;

        initSubsystems();
        configureButtons();
    }

    private void initSubsystems() {
        Manejar = new Drive(hMap);

        Flywheel = new Shooter(hMap);


        hubs = hMap.getAll(LynxModule.class);
        for (LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }


        //register(elevator);
        //register(intake);
        //register(trapdoor);

    }

    private void configureButtons() {



    }







    public double getLoopTimeHz() {
        return 1000 / loopTime;
    }


    @Override
    public void run() {

        double y = -g1.left_stick_y;
        double x = g1.right_stick_x;
        double rot = g1.left_stick_x;



        motor1 = y + x + rot;
        motor2 = y - x - rot;
        motor3 = y - x + rot;


        TriggerD = g1.right_trigger;

        Manejar.setPower1(motor1);
        Manejar.setPower2(motor2);
        Manejar.setPower3(motor3);
        Manejar.setPower4(motor4);

        Flywheel.setPower5(TriggerD);




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

        super.run();
    }
}