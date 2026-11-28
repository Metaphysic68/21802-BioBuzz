package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.math.Pose;

public class RobotConstants {

    public static final double FIELD_SIZE = 141.5;
    public static final Pose RED_GOAL_RIGHT = new Pose(59.25, 57.5); //59.25     58,4
    public static final Pose RED_GOAL_LEFT = new Pose(58.4, FIELD_SIZE-57.5);
    public static final Pose BLUE_GOAL_RIGHT = new Pose(FIELD_SIZE - RED_GOAL_RIGHT.x(), FIELD_SIZE - RED_GOAL_RIGHT.y());
    public static final Pose BLUE_GOAL_LEFT = new Pose(FIELD_SIZE - RED_GOAL_RIGHT.x(), RED_GOAL_RIGHT.y());

}
