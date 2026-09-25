package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Stopper extends SubsystemBase {
    private final Servo stopper;


    public Stopper(HardwareMap hMap) {

        stopper = hMap.get(Servo.class, "stopper");
        stopper.setDirection(Servo.Direction.FORWARD);
        stopper.setPosition(0);


    }

    public void setPos(double pos) {
        stopper.setPosition(pos);
    }

    public double getPos() {
        return stopper.getPosition();
    }
}