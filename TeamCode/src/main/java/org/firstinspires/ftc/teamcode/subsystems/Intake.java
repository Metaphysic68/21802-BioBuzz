package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.utils.RobotConstants;

public class Intake extends SubsystemBase {
    private final DcMotorEx intakeMotor;

    public Intake(Robot bot) {
        intakeMotor = bot.hMap.get(DcMotorEx.class, RobotConstants.Intake.MOTOR1_ID_STRING);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void setPow(double vel){
        intakeMotor.setPower(vel);
    }

    public void spin(double velocity) {
        setPow(velocity);
    }

    public void stop() {
        setPow(0);
    }

    public double getPower() {
        return intakeMotor.getPower();
    }
}