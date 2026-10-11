package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    private double velocidadObjetivo = 0;
    private final DcMotorEx Shooter1 ;
    private double kP = 0.0001;
    private double kF = 0.0005;

    public Shooter(HardwareMap hardwareMap) {
        Shooter1 = hardwareMap.get(DcMotorEx.class,"Disparador");

        Shooter1.setDirection(DcMotorSimple.Direction.FORWARD);
        Shooter1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void setVelocidad(double velocidad) {
        velocidadObjetivo = velocidad;
    }

    public void actualizarPF() {

        double velocidadReal = Shooter1.getVelocity();

        double error = velocidadObjetivo - velocidadReal;

        double P = kP * error;

        double F = kF * velocidadObjetivo;

        double potenciaPF = P + F;

        potenciaPF = Math.max(0, Math.min(1, potenciaPF));


    }

    public void setPower5(double potenciaPF) {




    }
    public void detener() {
        velocidadObjetivo = 0;
        Shooter1.setPower(0);
    }}






