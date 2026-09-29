package org.firstinspires.ftc.teamcode.subsystems;
import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Velocity;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.utils.CachedMotor;
import org.firstinspires.ftc.teamcode.utils.RobotConstants;
import org.firstinspires.ftc.teamcode.utils.SOTM;

@Config
public class Shooter extends SubsystemBase {

    private final DcMotorEx top;
    private final DcMotorEx bottom;
    private final Servo hood;
    double currentDistance;
    public static double targetVelocity;
    public static double targetPos;
    public static double kS = 0.07, kV = 0.000346, kP = 0.01, useRaw = 60;
    private final VoltageSensor battery;
    private final ElapsedTime voltageTimer = new ElapsedTime();
    private double filteredVoltage = 12.0;
    public static double nominalVolts = 12.0;


    public Shooter(HardwareMap hardwareMap) {

        top = new CachedMotor(hardwareMap.get(DcMotorEx.class, "shooter"));
        bottom = new CachedMotor(hardwareMap.get(DcMotorEx.class, "shooter2"));

        top.setDirection(DcMotorSimple.Direction.REVERSE);
        bottom.setDirection(DcMotorSimple.Direction.FORWARD);

        top.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        bottom.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        hood = hardwareMap.get(Servo.class,"hood");
        hood.setDirection(Servo.Direction.FORWARD);
        hood.setPosition(0);

        battery = hardwareMap.voltageSensor.iterator().next();
    }

    @Override
    public void periodic(){

        if (voltageTimer.milliseconds() > 250) {
            voltageTimer.reset();
            double v = battery.getVoltage();
            if (v > 6) {
                filteredVoltage += 0.3 * (v - filteredVoltage);
            }
        }

        if (Math.abs(getTargetVelocity() - getCurrentVelocity()) > useRaw) {
            setPower(Math.signum(getTargetVelocity() - getCurrentVelocity()));
        }
        else{
            double feedForwardCompensated = (kV * getTargetVelocity() + kS) * (nominalVolts/filteredVoltage);
            setPower(Math.max(0, feedForwardCompensated + (kP * (getTargetVelocity() - getCurrentVelocity()))));
        }
    }

    public void setPower(double p) {
        top.setPower(p);
        bottom.setPower(p);
    }

    public void setTargetVelocity(double vel){
       targetVelocity = vel;
    }


    public void setPos(double pos){
        targetPos = pos;
        hood.setPosition(pos);
    }

    public void setVelocityRED(Pose robotPose, Velocity robotVelocity){
        if (robotPose.y() > 72){
            setVelocityForHive(RobotConstants.RED_GOAL_LEFT, robotPose, robotVelocity);
        } else {
            setVelocityForHive(RobotConstants.RED_GOAL_RIGHT, robotPose, robotVelocity);
        }
    }
    public void setVelocityBLUE(Pose robotPose, Velocity robotVelocity){
        if (robotPose.y() < 72){
            setVelocityForHive(RobotConstants.BLUE_GOAL_LEFT, robotPose, robotVelocity);
        } else {
            setVelocityForHive(RobotConstants.BLUE_GOAL_RIGHT, robotPose, robotVelocity);
        }
    }

    private void setVelocityForHive(Pose goal, Pose robotPose, Velocity robotVelocity){
        currentDistance = goal.distance(robotPose);

        Pose virtualPose = SOTM.calculateVirtualRobot(robotPose, robotVelocity, goal);
        double virtualDistance = goal.distance(virtualPose);

        targetVelocity = (0.0068 * Math.pow (virtualDistance, 2)) + (5.2211 * virtualDistance) + 1262.9;

        double targetServoPos = (-3e-5 * Math.pow(virtualDistance, 2)) + (0.0123 * virtualDistance) - 0.4772;
        targetPos = targetServoPos;
        double safeHoodPos = Range.clip(targetServoPos, 0, 0.8);

        hood.setPosition(safeHoodPos);
    }
    public double getCurrentVelocity(){
        return top.getVelocity();
    }
    public double getTargetVelocity(){
        return targetVelocity;
    }
    public double getTargetPos(){
        return targetPos;
    }
    public double getHoodPos(){
        return hood.getPosition();
    }
    public double getPowerTop(){
        return top.getPower();
    }
    public double getPowerBottom(){
        return bottom.getPower();
    }
    public double getCurrentDistanceShooter(){
        return currentDistance;
    }

}