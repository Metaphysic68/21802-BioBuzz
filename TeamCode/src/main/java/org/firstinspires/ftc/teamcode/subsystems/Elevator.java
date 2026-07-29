package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elevator extends SubsystemBase{

    @Config
    public static class PIDConstants {
        public static double kP = 0.0099;
        public static double kI = 0;
        public static double kD = 0.00000000000005;
        public static double kF = 0.05;
    }

    private final PIDFController pid = new PIDFController(
            PIDConstants.kP, PIDConstants.kI, PIDConstants.kD, PIDConstants.kF);

    private final DcMotor elevator1;
    private final DcMotor elevator2;
    private final Telemetry telemetry;
    private int targetPosition = 0;
    private double profiledPosition = 0;
    public static double vel = 20;

    public Elevator(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;



        elevator1 = hardwareMap.get(DcMotor.class, "elevator");
        elevator1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        elevator1.setDirection(DcMotorSimple.Direction.FORWARD);

        elevator2 = hardwareMap.get(DcMotor.class, "elevator2");
        elevator2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator1.setDirection(DcMotorSimple.Direction.FORWARD);



        resetEncoders(); //remove for match
        pid.setTolerance(20);
        pid.setPIDF(PIDConstants.kP,PIDConstants.kI,PIDConstants.kD,PIDConstants.kF);

    }

    public void resetEncoders() {
        elevator1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevator2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        targetPosition = 0;
    }

    public void setTargetPosition(int ticks) {
        targetPosition = ticks;
    }
    public int getTargetPosition() {
        return targetPosition;
    }
    public int getPos() {
        return elevator1.getCurrentPosition();
    }
    public boolean isAtTarget() {
        return pid.atSetPoint();
    }

    @Override
    public void periodic() {
        pid.setP(PIDConstants.kP);
        pid.setI(PIDConstants.kI);
        pid.setD(PIDConstants.kD);
        pid.setF(PIDConstants.kF);

        if (profiledPosition < targetPosition) {
            profiledPosition = Math.min(targetPosition, profiledPosition + vel);
        } else if (profiledPosition > targetPosition) {
            profiledPosition = Math.max(targetPosition, profiledPosition - vel);
        }

        double power = pid.calculate(getPos(), profiledPosition);

        if (getPos() < 20 && targetPosition < 20) {
            elevator1.setPower(0);
            elevator2.setPower(0);
        } else {
            elevator1.setPower(power);
            elevator2.setPower(power);
        }



    }

}