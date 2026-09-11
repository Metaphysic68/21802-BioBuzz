package org.firstinspires.ftc.teamcode.paths;


import com.pedropathing.follower.Follower;
import static com.pedropathing.api.Paths.*;

import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.commands.FollowPathCommand;

public class AutoRunPath extends SequentialCommandGroup {

    public AutoRunPath(Follower f) {
        addCommands(
                new FollowPathCommand(f,park())

        );
    }
    private Path park() {
        return line(new Pose(72,72), new Pose(100,72)).linear(0, 0);
    }
}