package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.utils.RobotConstants;

public class Stopper extends SubsystemBase {
    private final Robot bot;
    private Servo stopperServo;

    public Stopper(Robot bot) {
        this.bot = bot;
        stopperServo = bot.hMap.get(Servo.class, RobotConstants.Stopper.SERVO_ID_STRING);
        stopperServo.setDirection(Servo.Direction.FORWARD);
        setPos(RobotConstants.Stopper.POSITION_CLOSED);
    }

    public void setPos(double pos) {
        stopperServo.setPosition(pos);
    }

    public double getPos() {
        return stopperServo.getPosition();
    }
}