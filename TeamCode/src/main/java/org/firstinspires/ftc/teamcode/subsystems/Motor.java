package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motor {

    private final DcMotorEx Anna;

    public Motor (HardwareMap Carlos) {

Anna = Carlos.get(DcMotorEx.class, "Motorsito");


    }



}
