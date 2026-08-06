package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.config.Config;

@Config
public class RobotConstants {
    private RobotConstants() {}

    public static class Intake {
        public static final String MOTOR1_ID_STRING = "Intake";
        public static double SPEED_INTAKE = 1;
        public static double SPEED_OFF = 0;
    }

    public static class Outtake {
        public static final String MOTOR1_ID_STRING = "Outtake";
        public static double SPEED_OUTTAKE = 1;
        public static double SPEED_OFF = 0;
    }

    public static class Stopper {
        public static final String SERVO_ID_STRING = "Stopper";
        public static double POSITION_CLOSED = 1;
        public static double POSITION_OPEN = 0;
    }
}