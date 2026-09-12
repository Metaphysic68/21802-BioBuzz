package org.firstinspires.ftc.teamcode.subsystems;
import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.utils.CachedMotor;


public class Shooter extends SubsystemBase {

    private final DcMotorEx motor1;
    private DcMotorEx motor2;
    double currentDistance;
    public double targetVelocity;
    @Config
    public static class ShooterPIDF{
        public static double kp = 520;
        public static double ki = 0.00;
        public static double kd = 0.00;
        public static double   kf = 14.87;
    }



    private final Pose RedScore = new Pose(57.32088681446908, 60.10618436406067);
    private final Pose BlueScore = new Pose (141.5-57.32088681446908,141.5-60.10618436406067);


    public Shooter(HardwareMap hardwareMap) {

        motor1 = new CachedMotor(hardwareMap.get(DcMotorEx.class, "shooter"));
        motor2 = new CachedMotor(hardwareMap.get(DcMotorEx.class, "shooter2"));


        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);

        motor1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);



        PIDFCoefficients pid = new PIDFCoefficients(
                ShooterPIDF.kp,ShooterPIDF.ki,ShooterPIDF.kd,ShooterPIDF.kf);


        motor1.setPIDFCoefficients(
                DcMotorEx.RunMode.RUN_USING_ENCODER,
                pid
        );

        motor2.setPIDFCoefficients(
                DcMotorEx.RunMode.RUN_USING_ENCODER,
                pid
        );
    }

    @Override
    public void periodic(){
        PIDFCoefficients pid = new PIDFCoefficients(
                ShooterPIDF.kp,ShooterPIDF.ki,ShooterPIDF.kd,ShooterPIDF.kf);


        motor1.setPIDFCoefficients(
                DcMotorEx.RunMode.RUN_USING_ENCODER,
                pid
        );
        motor2.setPIDFCoefficients(
                DcMotorEx.RunMode.RUN_USING_ENCODER,
                pid
        );
    }


    public double tarVelRED(double x, double y){
        currentDistance = Math.hypot(RedScore.x() - x, RedScore.y() - y);

        return targetVelocity = (0.0068 * Math.pow(currentDistance, 2)) + (5.2211 * currentDistance) + 1262.9;

    }
    public double getPower(){
        return motor1.getPower();
    }
    public double getCurrentDistanceShooter(){
        return currentDistance;
    }
    public double tarVelBLUE(double x, double y){
        currentDistance = Math.hypot(BlueScore.x() - x, BlueScore.y() - y);

        return targetVelocity = (0.0068 * Math.pow(currentDistance, 2)) + (5.2211 * currentDistance) + 1262.9;


    }
    public void setTargetVelocity(double vel){
        motor1.setVelocity(vel);
        motor2.setVelocity(vel);


    }

    public void setVelocityRED(double x, double y){
        currentDistance = Math.hypot(RedScore.x() - x, RedScore.y() - y);
        targetVelocity = (0.0068 * Math.pow(currentDistance, 2)) + (5.2211 * currentDistance) + 1262.9;
        motor1.setVelocity(targetVelocity);
        motor2.setVelocity(targetVelocity);

    }
    public void setVelocityBLUE(double x, double y){
        currentDistance = Math.hypot(BlueScore.x() - x, BlueScore.y() - y);

        targetVelocity = (0.0068 * Math.pow(currentDistance, 2)) + (5.2211 * currentDistance) + 1262.9;

        motor1.setVelocity(targetVelocity);
        motor2.setVelocity(targetVelocity);


    }
    public double getCurrentVelocity(){
        return motor1.getVelocity();
    }

}