package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp (name = "Teleop", group = "Teleop")
public class teleop extends CommandOpMode {
    private Robot robot;
    @Override
    public void initialize() {

        GamepadEx driver = new GamepadEx(gamepad1);
        GamepadEx operator = new GamepadEx(gamepad2);


        robot = new Robot(hardwareMap,telemetry,driver,operator,gamepad1,gamepad2);
    }
    @Override
    public void run(){
        super.run();
        robot.run();

    }

}
