package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class MotorAnna extends SubsystemBase{

    DcMotorEx Anna;

    public MotorAnna(HardwareMap Robertito) {

        Anna = Robertito.get(DcMotorEx.class, "Motor111");


    }}