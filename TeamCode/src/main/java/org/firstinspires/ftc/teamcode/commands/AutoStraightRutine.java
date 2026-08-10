package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;

import org.firstinspires.ftc.teamcode.pedroPathing.FieldLocations;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Stopper;
import org.firstinspires.ftc.teamcode.utils.RobotConstants;

public class AutoStraightRutine extends SequentialCommandGroup {

    public AutoStraightRutine(Follower f, Intake intake) {
        addCommands(
                new RunIntake(intake, RobotConstants.Intake.SPEED_INTAKE),

                new FollowPathCommand(f, f.pathBuilder()
                        .addPath(new BezierLine(FieldLocations.AutoStraight.StartPose, FieldLocations.AutoStraight.Intake))
                        .setLinearHeadingInterpolation(FieldLocations.AutoStraight.StartPose.getHeading(), FieldLocations.AutoStraight.Intake.getHeading())
                        .build())
        );
    }
}