package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.math.Pose;
import com.pedropathing.math.Vector2D;
import com.pedropathing.math.Velocity;

public class SOTM {


    public static double getTOF(double distance) {
        return (0.0072 * distance) - 0.0237;
    }


    public static Pose calculateVirtualRobot(Pose robotPose, Velocity robotVelocity, Pose goal) {
        Vector2D velocity = robotVelocity.toVector2D();
        if (velocity.magnitude() < 1) return robotPose;

        Vector2D robotVector = robotPose.toVector2D();
        Vector2D goalVector = goal.toVector2D();
        Vector2D virtual = robotVector;

        for (int i = 0; i < 10; i++) {
            virtual = robotVector.plus(velocity.times(getTOF(goalVector.distance(virtual))));
        }
        return virtual.toPose(robotPose.heading());
    }
}
