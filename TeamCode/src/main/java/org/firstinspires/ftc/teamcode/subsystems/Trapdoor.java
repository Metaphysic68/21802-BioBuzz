package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Trapdoor extends SubsystemBase {
    private final Servo trapdoor;
    private final Telemetry telemetry;


    public Trapdoor(HardwareMap hMap, Telemetry telemetry) {
        this.telemetry = telemetry;

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