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
        Motor4 = hardwareMap.get(DcMotorEx.class, "Motor4");

        Motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        Motor2.setDirection(DcMotorSimple.Direction.FORWARD);
        Motor3.setDirection(DcMotorSimple.Direction.FORWARD);
        Motor4.setDirection(DcMotorSimple.Direction.FORWARD);



    }
    public void setPower(double potencia) {

        Motor1.setPower(motor1);
        Motor2.setPower(motor2);
        Motor3.setPower(motor3);
        Motor4.setPower(motor4);

    }

}











