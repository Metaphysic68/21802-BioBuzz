package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Flower extends SubsystemBase {
    private final Servo flowerL;
    private final Servo flowerR;
    public Flower(HardwareMap hMap) {

        flowerL = hMap.get(Servo.class, "flowerL");
        flowerL.setDirection(Servo.Direction.FORWARD);
        flowerL.setPosition(0);


        flowerR = hMap.get(Servo.class, "flowerR");
        flowerR.setDirection(Servo.Direction.REVERSE);
        flowerR.setPosition(0);


    }

    public void setPos(double pos) {
        flowerL.setPosition(pos);
        flowerR.setPosition(pos);

    }

    public double getPos() {
        return flowerL.getPosition();
    }
}