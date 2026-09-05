package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.utils.CachedMotor;


public class Intake extends SubsystemBase {
    private final CachedMotor intake;
    //private final DcMotorEx intakeMotor2;


    public Intake(HardwareMap hMap) {

        intake = new CachedMotor(hMap.get(DcMotorEx.class, "intake"));
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        //intakeMotor2 = hMap.get(DcMotorEx.class, "Intake2");
        //intakeMotor2.setDirection(DcMotorSimple.Direction.REVERSE);



    }


    public void setPower(double vel){
        intake.setPower(vel);
        //intakeMotor2.setPower(vel);
    }


}