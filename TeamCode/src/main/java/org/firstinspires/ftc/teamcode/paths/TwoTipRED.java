package org.firstinspires.ftc.teamcode.paths;


import com.pedropathing.api.Paths;
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


public class TwoTipRED extends SequentialCommandGroup {


    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(52.6736, 7.0458, 90);
    private final Pose intakeSide = poseFactory.of(8, 8, 270);
    private final Pose intakeSideControl1 = poseFactory.of(27.106, 80.7713, 0);
    private final Pose shoot2 = poseFactory.of(58.6541, 131.7953, -90.0337);
    private final Pose shoot2Control1 = poseFactory.of(30.4181, 136.6372, 0);
    private final Pose shoot2Control2 = poseFactory.of(58.6366, 102.0651, 0);
    private final Pose flower1 = poseFactory.of(46.7574, 125.107, 89.5404);
    private final Pose flower1Control1 = poseFactory.of(46.5461, 98.7601, 0);
    private final Pose shoot3 = poseFactory.of(57.8916, 131.2542, -90.1193);
    private final Pose shoot3Control1 = poseFactory.of(57.8275, 100.4671, 0);
    private final Pose flower2 = poseFactory.of(15.2715, 46.2036, 179.6326);
    private final Pose flower2Control1 = poseFactory.of(21.6455, 117.3189, 0);
    private final Pose flower2Control2 = poseFactory.of(35.8972, 46.0713, 0);
    private final Pose shoot3_2 = poseFactory.of(59.3112, 132.8861, -87.5007);
    private final Pose shoot3_2Control1 = poseFactory.of(19.6052, 100.1118, 0);
    private final Pose shoot3_2Control2 = poseFactory.of(60.4272, 107.3162, 0);
    private final Pose pushStart = poseFactory.of(59.3112, 132.8861, -90);
    private final Pose push = poseFactory.of(7.22, 68.6731, 270);
    private final Pose push2 = poseFactory.of(7.22, 102.7686, -90);

    public Path intakeSide() {
        return Paths.curve(start, intakeSideControl1, intakeSide).linear(start, intakeSide);
    }

    public Path shoot2() {
        return Paths.curve(intakeSide, shoot2Control1, shoot2Control2, shoot2).reverseTangent();
    }

    public Path flower1() {
        return Paths.curve(shoot2, flower1Control1, flower1).tangent();
    }

    public Path shoot3() {
        return Paths.curve(flower1, shoot3Control1, shoot3).reverseTangent();
    }

    public Path flower2() {
        return Paths.curve(shoot3, flower2Control1, flower2Control2, flower2).tangent();
    }

    public Path shoot4() {
        return Paths.curve(flower2, shoot3_2Control1, shoot3_2Control2, shoot3_2).reverseTangent();
    }

    public Path push() {
        return Paths.line(pushStart, push).linear(pushStart, push);
    }

    public Path push2() {
        return Paths.line(push, push2).reverseTangent();
    }

    public TwoTipRED(Follower f, Intake i, Stopper s, Flower fl) {
        addCommands(
                new WaitCommand(3500),
                new ShootCommand(s,i),
                new InstantCommand(()->i.setPower(1)),
                new FollowPathCommand(f,intakeSide()),
                new WaitCommand(500),
                new InstantCommand(()->i.setPower(1)),
                new FollowPathCommand(f,shoot2()),
                new ShootCommand(s,i),
                new ParallelCommandGroup(
                        new InstantCommand(()->i.setPower(1)),
                        new InstantCommand(()->fl.setPos(0.425)),
                        new FollowPathCommand(f,flower1()),
                        new WaitCommand(1000)

                        ),
                new InstantCommand(()->fl.setPos(0)),
                new InstantCommand(()->i.setPower(1)),
                new FollowPathCommand(f,shoot3()),
                new ShootCommand(s,i),
                new ParallelCommandGroup(
                        new InstantCommand(()->i.setPower(1)),
                        new InstantCommand(()->fl.setPos(0.425)),
                        new FollowPathCommand(f,flower2()),
                        new WaitCommand(1000)

                ),
                new InstantCommand(()->fl.setPos(0)),
                new InstantCommand(()->i.setPower(1)),
                new FollowPathCommand(f,shoot4()),
                new ShootCommand(s,i),
                new InstantCommand(()->i.setPower(0)),
                new FollowPathCommand(f,push()),
                new FollowPathCommand(f,push2())



                );
    }

}