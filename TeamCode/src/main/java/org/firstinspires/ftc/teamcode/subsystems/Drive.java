package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Drive extends SubsystemBase {
    private final DcMotorEx Motor1;
    private final DcMotorEx Motor2;
    private final DcMotorEx Motor3;
    private final DcMotorEx Motor4;

    public Drive(HardwareMap hardwareMap) {
        Motor1 = hardwareMap.get(DcMotorEx.class, "Driver1");
        Motor2 = hardwareMap.get(DcMotorEx.class, "Driver2");
        Motor3 = hardwareMap.get(DcMotorEx.class, "Driver3");
        Motor4 = hardwareMap.get(DcMotorEx.class, "Driver4");

        Motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        Motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        Motor3.setDirection(DcMotorSimple.Direction.REVERSE);
        Motor4.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    public void setPower1(double potencia1) {

        Motor1.setPower(potencia1);

    }

    public void setPower2(double potencia2) {

        Motor2.setPower(potencia2);

    }

    public void setPower3(double potencia3) {

        Motor3.setPower(potencia3);

    }

    public void setPower4(double potencia4) {

        Motor4.setPower(potencia4);

    }
}