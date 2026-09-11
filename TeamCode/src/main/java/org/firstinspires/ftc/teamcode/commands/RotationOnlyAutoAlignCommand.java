package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.teamcode.Robot;

/**
 * A command that uses PID control to hold only the robot's rotation at a specific heading.
 * Unlike PositionHoldCommand, this does NOT hold X/Y position - the robot can drift.
 * This is useful for auto-aligning to specific headings while maintaining manual control.
 */
@Config
public class RotationOnlyAutoAlignCommand extends CommandBase {
    private final double targetHeading;
    Follower follower;

    // --- PID COEFFICIENTS (Tune these values!) ---
    // Heading PID - reused from PositionHoldCommand
    public static double kPH = 0.65;
    public static double kIH = 0.0;
    public static double kDH = 0;
    private double fwd;
    private double lat;

    private final PIDController hController = new PIDController(kPH, kIH, kDH);

    /**
     * Creates a command to auto-align the robot to a specific heading.
     * @param targetHeading The target heading in radians.
     */
    public RotationOnlyAutoAlignCommand(double targetHeading, Follower follower) {
        this.targetHeading = targetHeading;
        this.follower = follower;
    }

    @Override
    public void initialize() {
        hController.reset();
        Robot.isAutoAligned = true;
    }

    @Override
    public void execute() {
        double currentHeading = follower.pose().heading();

        // Calculate heading error with proper angle wrapping
        double errorH = Math.atan2(
                Math.sin(targetHeading - currentHeading),
                Math.cos(targetHeading - currentHeading)
        );

        // Calculate rotation power from PID
        double pH = hController.calculate(0, errorH);

        if (!Robot.isRedAlliance) {
            fwd = Robot.DriverGamepad.gamepad.left_stick_y;
            lat = Robot.DriverGamepad.gamepad.left_stick_x;
        } else {
            fwd = -Robot.DriverGamepad.gamepad.left_stick_y;
            lat = -Robot.DriverGamepad.gamepad.left_stick_x;
        }

        // Pass through X/Y controls from left stick, override rotation with PID
        DrivePowers powers = ManualDrive.fieldCentric(
                fwd,
                lat,
                pH,
                follower.pose().heading()
        );
        follower.manual(powers);

    }

    @Override
    public void end(boolean interrupted) {
        follower.manual(DrivePowers.zero());
        Robot.isAutoAligned = false;
    }
}