package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;

public interface AnyPIDmotor extends Subsystem {
    void setSetpoint(int setpoint);
    DcMotor getMotorRotate();
}