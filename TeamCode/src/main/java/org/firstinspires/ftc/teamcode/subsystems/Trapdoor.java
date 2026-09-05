package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Trapdoor extends SubsystemBase {
    private final Servo trapdoor;


    public Trapdoor(HardwareMap hMap) {

        trapdoor = hMap.get(Servo.class, "trapdoor");
        trapdoor.setDirection(Servo.Direction.FORWARD);


    }

    public void setPos(double pos) {
        trapdoor.setPosition(pos);
    }

    public double getPos() {
        return trapdoor.getPosition();
    }
}