package org.firstinspires.ftc.teamcode.utils;


import com.pedropathing.math.Pose;

/**
 * Carries the robot pose from auto into teleop. Static fields survive between OpModes
 * as long as the Robot Controller app is not restarted.
 */
public class PoseStorage {
    public static volatile Pose currentPose = null;
    public static boolean isRedAlliance = true;

    /** Stores a copy of the pose; ignores null or NaN poses so a glitch can't overwrite a good one. */
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
