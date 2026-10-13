package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Intake extends SubsystemBase{


    DcMotorEx MotorIntake;

    public Intake (HardwareMap hardwareMap) {

        MotorIntake = hardwareMap.get(DcMotorEx.class, "Intake1");

        MotorIntake.setDirection(DcMotorSimple.Direction.FORWARD);




    }

    public void setPowerIntake(double PotenciaIntake) {

        MotorIntake.setPower(PotenciaIntake);

    }

    }
