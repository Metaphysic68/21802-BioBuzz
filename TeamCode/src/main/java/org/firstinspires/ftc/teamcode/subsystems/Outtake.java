package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.utils.RobotConstants;

public class Outtake extends SubsystemBase {
    private final DcMotorEx outtakeMotor;

    public Outtake(Robot bot) {
        outtakeMotor = bot.hMap.get(DcMotorEx.class, RobotConstants.Outtake.MOTOR1_ID_STRING);
        outtakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void setPow(double vel){
        outtakeMotor.setPower(vel);
    }

    public void spin(double velocity) {
        setPow(velocity);
    }

    public void stop() {
        setPow(0);
    }
}