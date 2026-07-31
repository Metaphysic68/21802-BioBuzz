package org.firstinspires.ftc.teamcode.vision;

import android.util.Log;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.util.List;

public class Limelight {
    private static Limelight3A limelight;

    public static void init(HardwareMap hardwareMap) {
        init(hardwareMap, 0);
    }

    public static void init(HardwareMap hardwareMap, int pipelineIndex) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(pipelineIndex);
    }

    public static void start() {
        if (limelight != null) limelight.start();
    }

    public static void stop() {
        if (limelight != null) limelight.stop();
    }

    public static void saveSnapshot() {
        if (limelight != null) {
            limelight.captureSnapshot("cache-" + System.currentTimeMillis());
        }
    }

    public static void changePipeline(int newIndex) {
        if (limelight == null) return;
        try {
            limelight.pipelineSwitch(newIndex);
            Log.i("Limelight", "Switched to pipeline " + newIndex);
        } catch (Exception e) {
            Log.e("Limelight", "Failed to switch pipeline: " + e.getMessage());
        }
    }

    public static Integer getAprilTagId() {
        if (limelight == null) return null;
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) return null;

        List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
        if (fiducials != null && !fiducials.isEmpty()) {
            return fiducials.get(0).getFiducialId();
        }
        return null;
    }

    public static Double getTx() {
        if (limelight == null) return null;
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            return result.getTx();
        }
        return null;
    }

    public static Double getTy() {
        if (limelight == null) return null;
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            return result.getTy();
        }
        return null;
    }

    public static boolean isCentered(double toleranceDeg) {
        Double tx = getTx();
        if (tx == null) return false;
        return Math.abs(tx) < toleranceDeg;
    }

    public static boolean isCentered() {
        return isCentered(2.0); // Default 2 degree tolerance
    }


    public static Double getDistanceToAprilTag(double tagHeightM, double cameraHeightM, double cameraPitchDeg) {
        Double tyDeg = getTy();
        if (tyDeg == null) return null;

        double angleTotalRad = Math.toRadians(cameraPitchDeg + tyDeg);
        if (angleTotalRad == 0.0) return null;

        double distance = (tagHeightM - cameraHeightM) / Math.tan(angleTotalRad);
        if (Double.isFinite(distance)) {
            return Math.abs(distance);
        }
        return null;
    }

    public static Double getDistanceToAprilTag(double tagHeightM, double cameraHeightM) {
        return getDistanceToAprilTag(tagHeightM, cameraHeightM, 0.0);
    }
}