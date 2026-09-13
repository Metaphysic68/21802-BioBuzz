package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.math.Pose;
import com.pedropathing.math.Vector2D;
import com.pedropathing.math.Velocity;

public class ShootingMath {

    private static final int ITERATIONS = 10;
    private static final double MIN_VELOCITY = 1;

    public static double getTOF(double distance) {
        return (0.0072 * distance) - 0.0237;
    }


    public static Pose calculateVirtualRobot(Pose robotPose, Velocity robotVelocity, Pose goal) {
        Vector2D velocity = robotVelocity.toVector2D();
        if (velocity.magnitude() < MIN_VELOCITY) return robotPose;

        Vector2D robotVector = robotPose.toVector2D();
        Vector2D goalVector = goal.toVector2D();
        Vector2D virtualVector = robotVector;

        for (int i = 0; i < ITERATIONS; i++) {
            virtualVector = robotVector.plus(velocity.times(getTOF(goalVector.distance(virtualVector))));
        }

        return virtualVector.toPose(robotPose.heading());
    }
}
