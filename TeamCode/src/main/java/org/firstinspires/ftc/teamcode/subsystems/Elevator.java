package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.teamcode.utils.CachedMotor;

public class Elevator extends SubsystemBase {

    @Config
    public static class PIDConstants {
        public static double kP = 0.017;
        public static double kI = 0;
        public static double kD = 0;
        public static double kF = 0;
    }

    private final PIDFController pid = new PIDFController(
            PIDConstants.kP, PIDConstants.kI, PIDConstants.kD, PIDConstants.kF);

    private final DcMotor elevator1;
    private final DcMotor elevator2;
    private int targetPosition = 0;
    private double profiledPosition = 0;
    public static double vel = 50;

    public Elevator(HardwareMap hardwareMap) {



        elevator1 = new CachedMotor(hardwareMap.get(DcMotorEx.class, "elevator1"));
        elevator1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        elevator1.setDirection(DcMotorSimple.Direction.FORWARD);

        elevator2 = new CachedMotor(hardwareMap.get(DcMotorEx.class, "elevator2"));
        elevator2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator2.setDirection(DcMotorSimple.Direction.FORWARD);



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

        double power = pid.calculate(getPos(), profiledPosition) + PIDConstants.kF;
        elevator1.setPower(power);
        elevator2.setPower(power);



    }

}