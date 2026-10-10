package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.math.Pose;
import com.pedropathing.math.Vector2D;
import com.pedropathing.math.Velocity;

public class SOTM {
    public static Pose calculateVirtualRobot(Pose robotPose, Velocity robotVelocity, double TOF) {
        Vector2D velocity = robotVelocity.toVector2D();
        if (velocity.magnitude() < 1) return robotPose;

        Vector2D robotVector = robotPose.toVector2D();
        Vector2D virtual = robotVector;

        for (int i = 0; i < 10; i++) {
            virtual = robotVector.plus(velocity.times(TOF));
        }
        return virtual.toPose(robotPose.heading());
    }
}