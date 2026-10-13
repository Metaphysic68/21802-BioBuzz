package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Indexer extends SubsystemBase {

    private final CRServo Index1;

    public Indexer(HardwareMap hMap) {
        Index1 = hMap.get(CRServo.class, "Index1");

        Index1.setDirection(CRServo.Direction.FORWARD);
        Index1.setPower(0);
    }

    public void setPowerServo(double power) {
        Index1.setPower(power);
    }}

