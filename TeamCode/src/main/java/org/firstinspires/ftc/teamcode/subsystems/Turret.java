package org.firstinspires.ftc.teamcode.subsystems;

import static com.seattlesolvers.solverslib.util.MathUtils.clamp;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Turret extends SubsystemBase {

    private final Servo turret;
    public double targetAngle;
    public double targetPosition;

    @Config
    public static class TurretConstants {
        public static double servoRange = 350;
        public static double centerOffsetDegrees = 0;
    }

    private final Pose RedScore = new Pose(57.32088681446908, 60.10618436406067);
    private final Pose BlueScore = new Pose(141.5-57.32088681446908,141.5-60.10618436406067);

    public Turret(HardwareMap hardwareMap) {
        turret = hardwareMap.get(Servo.class, "turret");
        turret.setDirection(Servo.Direction.FORWARD);
    }

    public double angleToRED(double x, double y, double heading){
        double fieldAngle = Math.atan2(RedScore.y() - y, RedScore.x() - x);
        return targetAngle = wrapAngle(fieldAngle - heading);
    }

    public double angleToBLUE(double x, double y, double heading){
        double fieldAngle = Math.atan2(BlueScore.y() - y, BlueScore.x() - x);
        return targetAngle = wrapAngle(fieldAngle - heading);
    }

    public void aimRED(double x, double y, double heading){
        setTurretAngle(angleToRED(x, y, heading));
    }

    public void aimBLUE(double x, double y, double heading){
        setTurretAngle(angleToBLUE(x, y, heading));
    }


    public void setTurretAngle(double angleRadians){
        double angleDegrees = Math.toDegrees(angleRadians);
        double position = 0.5 + (angleDegrees + TurretConstants.centerOffsetDegrees) / TurretConstants.servoRange;
        targetPosition = clamp(position, 0.0, 1.0);
        turret.setPosition(targetPosition);
    }

    private double wrapAngle(double angle){
        while (angle > Math.PI) angle -= 2 * Math.PI;
        while (angle < -Math.PI) angle += 2 * Math.PI;
        return angle;
    }

    public double getTargetAngle(){
        return targetAngle;
    }

    public double getPosition(){
        return turret.getPosition();
    }
}
