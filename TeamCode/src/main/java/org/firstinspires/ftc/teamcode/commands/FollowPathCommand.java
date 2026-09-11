package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.Path;
import com.seattlesolvers.solverslib.command.CommandBase;


public class FollowPathCommand extends CommandBase {
    private final Follower follower;
    private final Path path;

    public FollowPathCommand(Follower follower, Path path ) {
        this.follower = follower;
        this.path = path;
    }

    @Override
    public void initialize() {
        follower.follow(path);
    }

    @Override
    public void execute() {
        follower.update();
    }

    @Override
    public boolean isFinished() {
        return follower.atParametricEnd();
    }
}