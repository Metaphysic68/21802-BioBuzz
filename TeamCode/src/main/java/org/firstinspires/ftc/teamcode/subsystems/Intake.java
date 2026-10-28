package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.utils.Timer;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.utils.CachedMotor;


public class Intake extends SubsystemBase {
    private final CachedMotor intake1;

    private final CachedMotor intake2;
    private final DigitalChannel laserInput;
    private final Timer timer;
    private final Servo light;

    public Intake(HardwareMap hMap) {
        timer = new Timer();

        intake1 = new CachedMotor(hMap.get(DcMotorEx.class, "intake1"));
        intake1.setDirection(DcMotorSimple.Direction.REVERSE);
        intake2 = new CachedMotor(hMap.get(DcMotorEx.class, "intake2"));
        intake2.setDirection(DcMotorSimple.Direction.FORWARD);

        laserInput = hMap.get(DigitalChannel.class, "laser");
        laserInput.setMode(DigitalChannel.Mode.INPUT);

        light = hMap.get(Servo.class,"led1");
        light.setPosition(0.72);

    }
    @Override
    public void periodic(){
        boolean detected = laserInput.getState();

        if (detected) {
            if (timer.milliseconds() > 150) {
                light.setPosition(0.5);
            } else {
                light.setPosition(0.333);
            }
        } else {
            timer.reset();
            light.setPosition(0.333);
        }
    }


    public void setPower(double vel){
        intake1.setPower(vel);
        intake2.setPower(vel);
    }


}