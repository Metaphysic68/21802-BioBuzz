package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.geometry.Pose;

public enum FieldLocations {;

    public static class AutoStraight {
        public static class Rojo {
            public static Pose StartPose = new Pose(56, 8, Math.toRadians(90));
            public static Pose Intake    = new Pose(56, 99, Math.toRadians(90));
        }

        public static class Azul {
            public static Pose StartPose = Rojo.StartPose.mirror(144);
            public static Pose Intake    = Rojo.Intake.mirror(144);
        }
    }
}