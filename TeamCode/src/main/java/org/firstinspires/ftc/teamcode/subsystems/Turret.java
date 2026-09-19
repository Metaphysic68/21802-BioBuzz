package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.opencv.core.Mat;

public class Turret extends SubsystemBase {
    private Servo turret;
    private HardwareMap hMap;
    private double targetAngles;
    public Turret(HardwareMap hMap){
        this.hMap = hMap;
        turret = hMap.get(Servo.class,"turret");
        turret.setPosition(0.5);
        turret.setDirection(Servo.Direction.FORWARD);
    }
    public void setTurretAngles(Pose robotPose, Pose hive){
        double targetRadians = Math.atan2(hive.y() - robotPose.y(), hive.x()- robotPose.x());
        targetAngles = Math.toDegrees(targetRadians)- robotPose.heading();
        while (targetAngles > 180) targetAngles -= 360;
        while (targetAngles <= -180) targetAngles += 360;
        double pos = (targetAngles+0.5)/360;
        turret.setPosition(pos);

    }
}
