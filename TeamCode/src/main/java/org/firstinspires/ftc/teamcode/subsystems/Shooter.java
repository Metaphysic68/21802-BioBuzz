package org.firstinspires.ftc.teamcode.subsystems;
import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Velocity;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.utils.CachedMotor;
import org.firstinspires.ftc.teamcode.utils.RobotConstants;
import org.firstinspires.ftc.teamcode.utils.ShootingMath;


public class Shooter extends SubsystemBase {

    private final CachedMotor motor1;
    private final CachedMotor motor2;
    private final Servo hood;
    double currentDistance;
    public double targetVelocity;
    @Config
    public static class ShooterPIDF{
        public static double kp = 520;
        public static double ki = 0.00;
        public static double kd = 0.00;
        public static double   kf = 14.87;
    }



    public Shooter(HardwareMap hardwareMap) {

        motor1 = new CachedMotor(hardwareMap.get(DcMotorEx.class, "shooter"));
        motor2 = new CachedMotor(hardwareMap.get(DcMotorEx.class, "shooter2"));

        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);

        motor1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        hood = hardwareMap.get(Servo.class,"hood");
        hood.setDirection(Servo.Direction.FORWARD);
        hood.setPosition(0);

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


    public double getPower(){
        return motor1.getPower();
    }
    public double getCurrentDistanceShooter(){
        return currentDistance;
    }
    public void setTargetVelocity(double vel){
        motor1.setVelocity(vel);
        motor2.setVelocity(vel);


    }
    public void setVelocityRED(Pose robotPose, Velocity robotVelocity){
        setVelocityForGoal(RobotConstants.RED_GOAL_RIGHT, robotPose, robotVelocity);
        if (robotPose.y() > 72){
            setVelocityForGoal(RobotConstants.RED_GOAL_LEFT, robotPose, robotVelocity);

        }
    }
    public void setVelocityBLUE(Pose robotPose, Velocity robotVelocity){
        setVelocityForGoal(RobotConstants.BLUE_GOAL_RIGHT, robotPose, robotVelocity);
        if (robotPose.y() < 72){
            setVelocityForGoal(RobotConstants.BLUE_GOAL_LEFT, robotPose, robotVelocity);

        }
    }

    private void setVelocityForGoal(Pose goal, Pose robotPose, Velocity robotVelocity){
        currentDistance = goal.distance(robotPose);

        Pose virtualPose = ShootingMath.calculateVirtualRobot(robotPose, robotVelocity, goal);
        double virtualDistance = goal.distance(virtualPose);

        targetVelocity = (0.0068 * Math.pow(virtualDistance, 2)) + (5.2211 * virtualDistance) + 1262.9;
        motor1.setVelocity(targetVelocity);
        motor2.setVelocity(targetVelocity);

        double targetServoPos = (-3e-5 * Math.pow(virtualDistance, 2)) + (0.0123 * virtualDistance) - 0.4772;
        double safeHoodPos = Range.clip(targetServoPos, 0, 0.8);

        hood.setPosition(safeHoodPos);    }
    public double getCurrentVelocity(){
        return motor1.getVelocity();
    }

}
