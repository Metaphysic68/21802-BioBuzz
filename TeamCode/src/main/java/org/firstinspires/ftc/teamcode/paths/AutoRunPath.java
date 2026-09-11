package org.firstinspires.ftc.teamcode.paths;


import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import static com.pedropathing.api.Paths.*;

import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.commands.follow;

public class AutoRunPath extends SequentialCommandGroup {
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(85.1301, 9.9183, 90);
    private final Pose intake1Start = poseFactory.of(85.1301, 9.9183, 0);
    private final Pose intake1 = poseFactory.of(133.3648, 34.8496, 0);
    private final Pose intake1Control1 = poseFactory.of(75.1982, 38.311, 0);
    private final Pose shoot1 = poseFactory.of(85.3933, 9.9096, 0);
    private final Pose intake2 = poseFactory.of(130.2774, 9.564, 0);
    private final Pose shoot3 = poseFactory.of(84.2673, 9.9183, 0);

    public Path intake1() {
        return curve(intake1Start, intake1Control1, intake1).linear(intake1Start, intake1);
    }

    public Path shoot1() {
        return line(intake1, shoot1).linear(intake1, shoot1);
    }

    public Path intake2() {
        return line(shoot1, intake2).linear(shoot1, intake2);
    }

    public Path shoot2() {
        return line(intake2, shoot3).linear(intake2, shoot3);
    }

    public AutoRunPath(Follower f) {
        addCommands(
                new follow(f,intake1()),
                new follow(f,shoot1()),
                new follow(f,intake2()),
                new follow(f,shoot2())

        );
    }

}