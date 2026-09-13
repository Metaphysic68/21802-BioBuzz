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
    public void setTargetAngleRED(double x, double y, double heading){
        double fieldRadians = Math.atan2(RedScore.y() - y, RedScore.x()-x);
        double angleDegrees = Math.toDegrees(fieldRadians - heading);

        while (angleDegrees > 180) angleDegrees -= 360;
        while (angleDegrees <= -180) angleDegrees += 360;

        targetAngle = angleDegrees;

        double pos = 0.5 + (angleDegrees+TurretConstants.centerOffsetDegrees) / TurretConstants.servoRange;

        targetPosition = clamp(pos,0,1);
        turret.setPosition(targetPosition);

    }
    public void setTargetAngleBLUE(double x, double y, double heading){
        double fieldRadians = Math.atan2(BlueScore.y() - y, BlueScore.x()-x);
        double angleDegrees = Math.toDegrees(fieldRadians - heading);

        while (angleDegrees > 180) angleDegrees -= 360;
        while (angleDegrees <= -180) angleDegrees += 360;

        targetAngle = angleDegrees;

        double pos = 0.5 + (angleDegrees+TurretConstants.centerOffsetDegrees) / TurretConstants.servoRange;

        targetPosition = clamp(pos,0,1);
        turret.setPosition(targetPosition);

    }



    public double getTargetAngle(){
        return targetAngle;
    }

    public double getPosition(){
        return turret.getPosition();
    }
}
