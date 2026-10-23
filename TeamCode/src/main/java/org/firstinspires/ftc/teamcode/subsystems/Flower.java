package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Flower extends SubsystemBase {
    private final Servo flower;


    public Flower(HardwareMap hMap) {

        flower = hMap.get(Servo.class, "flower");
        flower.setDirection(Servo.Direction.REVERSE);
        flower.setPosition(0);


    }

    public void setPos(double pos) {
        flower.setPosition(pos);
    }

    public double getPos() {
        return flower.getPosition();
    }
}