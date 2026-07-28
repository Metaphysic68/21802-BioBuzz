package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elevator extends SubsystemBase implements AnyPIDmotor {

    @Config
    public static class PIDConstants {
        public static double kP = 0.05;
        public static double kI = 0;
        public static double kD = 0;
        public static double kF = -0.0005;
    }

    @Config
    public static class Setpoints {
        public static int CLIMB = 1;
        public static int SAVE = 0;
    }

    // Tune these to your elevator's real travel range so it can never
    // be commanded to slam into the top or bottom of the slides.
    public static int MIN_POSITION = 0;
    public static int MAX_POSITION = 3000;

    // Max change in setpoint per periodic() cycle — this smooths the motion
    // instead of asking the PID to jump straight to a far-away target.
    public static double MAX_TICKS_PER_CYCLE = 15;

    private final PIDFController pid = new PIDFController(
            PIDConstants.kP, PIDConstants.kI, PIDConstants.kD, PIDConstants.kF);

    private final DcMotor motor1;
    private final DcMotor motor2;
    private final Telemetry telemetry;

    private int targetPosition = 0;      // where we ultimately want to be
    private double profiledPosition = 0; // ramped intermediate goal the PID chases a

    public Elevator(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        pid.setTolerance(20);

        motor1 = hardwareMap.get(DcMotor.class, "elevator");
        motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motor2 = hardwareMap.get(DcMotor.class, "elevator2");
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void resetEncoders() {
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        profiledPosition = 0;
        targetPosition = 0;
    }

    /** Commands the elevator to an arbitrary tick position, clamped to safe limits. */
    public void setTargetPosition(int ticks) {
        targetPosition = Math.max(MIN_POSITION, Math.min(MAX_POSITION, ticks));
    }
    @Override
    public void setSetpoint(int ticks) {
        setTargetPosition(ticks);
    }

    public int getTargetPosition() {
        return targetPosition;
    }

    public int getCurrentPosition() {
        return motor1.getCurrentPosition();
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

        // Ramp toward the target instead of jumping straight to it
        if (profiledPosition < targetPosition) {
            profiledPosition = Math.min(targetPosition, profiledPosition + MAX_TICKS_PER_CYCLE);
        } else if (profiledPosition > targetPosition) {
            profiledPosition = Math.max(targetPosition, profiledPosition - MAX_TICKS_PER_CYCLE);
        }

        double power = pid.calculate(getCurrentPosition(), profiledPosition);
        power = Math.max(-1, Math.min(1, power));

        motor1.setPower(power);
        motor2.setPower(power);

        telemetry.addData("Elevator Target", targetPosition);
        telemetry.addData("Elevator Position", getCurrentPosition());
        telemetry.addData("Elevator At Target", isAtTarget());
    }

    @Override
    public DcMotor getMotorRotate() {
        return motor1;
    }
}