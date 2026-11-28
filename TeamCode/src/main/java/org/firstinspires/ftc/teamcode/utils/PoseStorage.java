package org.firstinspires.ftc.teamcode.utils;


import com.pedropathing.math.Pose;


public class PoseStorage {
    public static volatile Pose currentPose = null;
    public static boolean isRedAlliance = true;

    public static void save(Pose pose) {
        if (pose == null) return;
        if (Double.isNaN(pose.x()) || Double.isNaN(pose.y()) || Double.isNaN(pose.heading())) return;
        currentPose = new Pose(pose.x(), pose.y(), pose.heading());
    }

    public static boolean hasPose() {
        return currentPose != null;
    }

    public static void clear() {
        currentPose = null;
    }
}
