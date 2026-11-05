package org.firstinspires.ftc.teamcode.subsystems;
import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Velocity;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.utils.LookUpTable;
import org.firstinspires.ftc.teamcode.utils.RobotConstants;
import org.firstinspires.ftc.teamcode.utils.SOTM;

@Config
public class Shooter extends SubsystemBase {

    private final DcMotorEx top;
    private final DcMotorEx bottom;
    private final Servo hood;
    private final LookUpTable storage;
    private double currentDistance;
    public static double targetVelocity; //1380
    public static double targetPos = 0.34;
    public static double kS = 0, kV = 0.0004223, kP = 0.0025, bangThreshold = 60 ;

    public double voltage = 12.0;
    private final VoltageSensor battery;

    public Shooter(HardwareMap hardwareMap, LookUpTable storage) {

        top = hardwareMap.get(DcMotorEx.class, "shooter");
        bottom = hardwareMap.get(DcMotorEx.class, "shooter2");

        top.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bottom.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        top.setDirection(DcMotorSimple.Direction.REVERSE);
        bottom.setDirection(DcMotorSimple.Direction.REVERSE);

        top.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        bottom.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        hood = hardwareMap.get(Servo.class,"hood");
        hood.setDirection(Servo.Direction.FORWARD);
        hood.setPosition(0);


        battery = hardwareMap.voltageSensor.iterator().next();
        voltage = battery.getVoltage();

        this.storage = storage;


        targetVelocity = 0;
    }

    @Override
    public void periodic(){


        voltage = battery.getVoltage();


        hood.setPosition(targetPos);

        double error = getTargetVelocity() - getCurrentVelocity();

        if (error > bangThreshold) {
            setPower(1.0);
        }
        else{
            double powerCompensated = (kP * error + kV*getTargetVelocity() + kS) * (12.0/voltage);
            setPower(Math.max(0, Math.min(1,powerCompensated)));
        }
    }

    public void setPower(double p) {
        top.setPower(p);
        bottom.setPower(p);
    }
    public double getVoltage(){
        return voltage;
    }
    public void setTargetVelocity(double vel){
       targetVelocity = vel;
    }
    public void setPos(double pos){
        targetPos = pos;
        hood.setPosition(pos);
    }
    public boolean atTarget(double tolerance) {
        return getTargetVelocity() > 0
                && Math.abs(getTargetVelocity() - getCurrentVelocity()) < tolerance;
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

        Pose virtualPose = SOTM.calculateVirtualRobot(robotPose, robotVelocity,storage.getTime(currentDistance));
        double virtualDistance = goal.distance(virtualPose);

        setTargetVelocity(storage.getRPM(virtualDistance));
        setPos(storage.getHood(virtualDistance));

        /*targetVelocity = (0.0068 * Math.pow (virtualDistance, 2)) + (5.2211 * virtualDistance) + 1262.9;

        double targetServoPos = (-3e-5 * Math.pow(virtualDistance, 2)) + (0.0123 * virtualDistance) - 0.4772;
        targetPos = targetServoPos;
        double safeHoodPos = Range.clip(targetServoPos, 0, 0.8);

        hood.setPosition(safeHoodPos);*/
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