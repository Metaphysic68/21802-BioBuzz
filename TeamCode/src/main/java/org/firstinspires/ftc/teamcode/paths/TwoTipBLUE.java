package org.firstinspires.ftc.teamcode.paths;


import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import static com.pedropathing.api.Paths.*;

import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.commands.ShootCommand;
import org.firstinspires.ftc.teamcode.subsystems.Flower;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Stopper;


public class TwoTipBLUE extends SequentialCommandGroup {


    private final PoseFactory poseFactory = PoseFactory.degrees().mirrorY(70.75).mirrorX(70.75);
    private final Pose intakeSideStart = poseFactory.of(54.4172, 6, 180);
    private final Pose intakeSide = poseFactory.of(8, 6, 180);
    private final Pose secondTip1 = poseFactory.of(55.2953, 118.7919, -178.1826);
    private final Pose secondTip1Control1 = poseFactory.of(33.3154, 118.6566, 0);
    private final Pose flowerIntake1 = poseFactory.of(47.3009, 129.7416, 90.1435);
    private final Pose flowerIntake1Control1 = poseFactory.of(47.3249, 104.0674, 0);
    private final Pose secondTip2 = poseFactory.of(55.2953, 118.7919, -90.1);
    private final Pose secondTip2Control1 = poseFactory.of(55.3166, 107.7852, 0);
    private final Pose flowerIntake2 = poseFactory.of(14.0492, 47.1655, -178.2204);
    private final Pose flowerIntake2Control1 = poseFactory.of(13.6029, 118.67, 0);
    private final Pose flowerIntake2Control2 = poseFactory.of(40.8333, 47.2729, 0);
    private final Pose secondTip3 = poseFactory.of(55.5101, 118.6409, -178.9862);
    private final Pose secondTip3Control1 = poseFactory.of(21.5391, 118.3971, 0);
    private final Pose parkStart = poseFactory.of(55.5101, 118.6409, 180);
    private final Pose park = poseFactory.of(11.2377, 109.7654, 180);

    public Path intakeSide() {
        return line(intakeSideStart, intakeSide).linear(intakeSideStart, intakeSide);
    }

    public Path secondTip1() {
        return curve(intakeSide, secondTip1Control1, secondTip1).reverseTangent();
    }

    public Path flowerIntake1() {
        return curve(secondTip1, flowerIntake1Control1, flowerIntake1).tangent();
    }

    public Path secondTip2() {
        return curve(flowerIntake1, secondTip2Control1, secondTip2).reverseTangent();
    }

    public Path flowerIntake2() {
        return curve(secondTip2, flowerIntake2Control1, flowerIntake2Control2, flowerIntake2).tangent();
    }

    public Path secondTip3() {
        return curve(flowerIntake2, secondTip3Control1, secondTip3).reverseTangent();
    }

    public Path park() {
        return line(parkStart, park).linear(parkStart, park);
    }

    public TwoTipBLUE(Follower f, Intake i, Stopper s, Flower fl) {

        addCommands(
                new WaitCommand(500),
                new ShootCommand(s,i),
                new InstantCommand(()->i.setPower(1)),
                new FollowPathCommand(f,intakeSide()),
                new WaitCommand(500),
                new InstantCommand(()->i.setPower(1)),
                new FollowPathCommand(f,secondTip1()),
                new ShootCommand(s,i),
                new ParallelCommandGroup(
                        new InstantCommand(()->i.setPower(1)),
                        new InstantCommand(()->fl.setPos(0.425)),
                        new FollowPathCommand(f,flowerIntake1()),
                        new WaitCommand(1000)

                ),
                new InstantCommand(()->fl.setPos(0)),
                new InstantCommand(()->i.setPower(1)),
                new FollowPathCommand(f,secondTip2()),
                new ShootCommand(s,i),
                new ParallelCommandGroup(
                        new InstantCommand(()->i.setPower(1)),
                        new InstantCommand(()->fl.setPos(0.425)),
                        new FollowPathCommand(f,flowerIntake2()),
                        new WaitCommand(1000)

                ),
                new InstantCommand(()->fl.setPos(0)),
                new InstantCommand(()->i.setPower(1)),
                new FollowPathCommand(f,secondTip3()),
                new ShootCommand(s,i),
                new InstantCommand(()->i.setPower(0)),
                new FollowPathCommand(f,park())


        );
    }

}