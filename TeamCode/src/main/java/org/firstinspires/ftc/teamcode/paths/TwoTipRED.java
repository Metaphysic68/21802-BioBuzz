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
import org.firstinspires.ftc.teamcode.utils.PoseStorage;


public class TwoTipRED extends SequentialCommandGroup {
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(52.6736, 7.0458, 90);
    private final Pose shoot1 = poseFactory.of(56.0021, 15.8388, 90);
    private final Pose intakeSideStart = poseFactory.of(56.0021, 15.8388, 90);
    private final Pose intakeSide = poseFactory.of(6, 10, 270);
    private final Pose intakeSideControl1 = poseFactory.of(22.1211, 76.8249, 0);
    private final Pose intakeSideBackStart = poseFactory.of(6, 10, 270);
    private final Pose intakeSideBack = poseFactory.of(6, 20, 270);
    private final Pose intakeSideReturnStart = poseFactory.of(6, 20, 270);
    private final Pose intakeSideReturn = poseFactory.of(6, 11, 270);
    private final Pose shoot2 = poseFactory.of(58.6541, 125, -89.1165);
    private final Pose shoot2Control1 = poseFactory.of(22.908, 131.1089, 0);
    private final Pose shoot2Control2 = poseFactory.of(58.9352, 106.7694, 0);
    private final Pose flower1 = poseFactory.of(47.5, 126.2, 88.009);
    private final Pose flower1Control1 = poseFactory.of(46.5461, 98.7601, 0);
    private final Pose flower1BackStart = poseFactory.of(47.5, 126.2, 89.5404);
    private final Pose flower1Back = poseFactory.of(47.5, 120, 89.5404);
    private final Pose flower1ReturnStart = poseFactory.of(47.5, 120, 89.5404);
    private final Pose flower1Return = poseFactory.of(47.5, 126.2, 89.5404);
    private final Pose shoot3 = poseFactory.of(57.8916, 125, -90.327);
    private final Pose shoot3Control1 = poseFactory.of(57.7105, 93.2737, 0);
    private final Pose flower2 = poseFactory.of(26.05, 47.46, 171.9728);
    private final Pose flower2Control1 = poseFactory.of(21.6455, 117.3189, 0);
    private final Pose flower2Control2 = poseFactory.of(35.8972, 46.0713, 0);
    private final Pose point11Start = poseFactory.of(26.05, 47.46, 180);
    private final Pose point11 = poseFactory.of(15.5, 47.46, 180);
    private final Pose flower2BackStart = poseFactory.of(15.5, 47.46, 179.6326);
    private final Pose flower2Back = poseFactory.of(20, 47.46, 179.6326);
    private final Pose flower2ReturnStart = poseFactory.of(20, 47.46, 179.6326);
    private final Pose flower2Return = poseFactory.of(16.5, 47.46, 179.6326);
    private final Pose shoot4 = poseFactory.of(59.3112, 125, -89.6355);
    private final Pose shoot4Control1 = poseFactory.of(19.6052, 100.1118, 0);
    private final Pose shoot4Control2 = poseFactory.of(59.4255, 107.03, 0);
    private final Pose pushStart = poseFactory.of(59.3112, 125, -90);
    private final Pose push = poseFactory.of(12.4127, 118.3149, 0);

    public Path shoot1() {
        return Paths.line(start, shoot1).linear(start, shoot1);
    }

    public Path intakeSide() {
        return Paths.curve(intakeSideStart, intakeSideControl1, intakeSide).linear(intakeSideStart, intakeSide);
    }

    public Path intakeSideBack() {
        return Paths.line(intakeSideBackStart, intakeSideBack).linear(intakeSideBackStart, intakeSideBack);
    }

    public Path intakeSideReturn() {
        return Paths.line(intakeSideReturnStart, intakeSideReturn).linear(intakeSideReturnStart, intakeSideReturn);
    }

    public Path shoot2() {
        return Paths.curve(intakeSideReturn, shoot2Control1, shoot2Control2, shoot2).reverseTangent();
    }

    public Path flower1() {
        return Paths.curve(shoot2, flower1Control1, flower1).tangent();
    }

    public Path flower1Back() {
        return Paths.line(flower1BackStart, flower1Back).linear(flower1BackStart, flower1Back);
    }

    public Path flower1Return() {
        return Paths.line(flower1ReturnStart, flower1Return).linear(flower1ReturnStart, flower1Return);
    }

    public Path shoot3() {
        return Paths.curve(flower1Return, shoot3Control1, shoot3).reverseTangent();
    }

    public Path flower2() {
        return Paths.curve(shoot3, flower2Control1, flower2Control2, flower2).tangent();
    }

    public Path path11() {
        return Paths.line(point11Start, point11).linear(point11Start, point11);
    }

    public Path flower2Back() {
        return Paths.line(flower2BackStart, flower2Back).linear(flower2BackStart, flower2Back);
    }

    public Path flower2Return() {
        return Paths.line(flower2ReturnStart, flower2Return).linear(flower2ReturnStart, flower2Return);
    }

    public Path shoot4() {
        return Paths.curve(flower2Return, shoot4Control1, shoot4Control2, shoot4).reverseTangent();
    }

    public Path push() {
        return Paths.line(pushStart, push).linear(pushStart, push);
    }
    public TwoTipRED(Follower f, Intake i, Stopper s, Flower fl) {
        addCommands(
                new FollowPathCommand(f,shoot1()),
                new WaitCommand(1500),
                new ShootCommand(s,i),
                new InstantCommand(()->i.setPower(1)),

                new InstantCommand(()->i.setPower(1)),
                new FollowPathCommand(f,intakeSide()),
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new FollowPathCommand(f,intakeSideBack()),
                                new FollowPathCommand(f,intakeSideReturn())
                        ),
                        new WaitCommand(2000)
                ),
                new FollowPathCommand(f,shoot2()),
                new ShootCommand(s,i),
                new InstantCommand(()->i.setPower(1)),

                new ParallelCommandGroup(
                        new InstantCommand(()->fl.setPos(0.5)),
                        new SequentialCommandGroup(
                                new FollowPathCommand(f,flower1()),
                                new WaitCommand(500),
                                new FollowPathCommand(f,flower1Back()),
                                new FollowPathCommand(f,flower1Return()),
                                new WaitCommand(1000)

                        )
                        ),
                new FollowPathCommand(f,shoot3()),
                new InstantCommand(()->fl.setPos(0)),

                new ShootCommand(s,i),
                new InstantCommand(()->i.setPower(1)),

                new ParallelCommandGroup(
                        new InstantCommand(()->fl.setPos(0.5)),

                        new SequentialCommandGroup(
                                new FollowPathCommand(f,flower2()),
                                new WaitCommand(200), //26.05,47.46     15, 47.46

                                new FollowPathCommand(f,path11()),
                                new WaitCommand(500), //26.05,47.46     15, 47.46
                                new FollowPathCommand(f,flower2Back()),
                                new FollowPathCommand(f,flower2Return()),
                                new WaitCommand(500) //26.05,47.46     15, 47.46

                        )

                ),
                new FollowPathCommand(f,shoot4()),
                new InstantCommand(()->fl.setPos(0)),

                new ShootCommand(s,i),
                new InstantCommand(()->i.setPower(0)),


                new FollowPathCommand(f,push()),
                new InstantCommand(()-> PoseStorage.currentPose = f.pose())



                );
    }

}