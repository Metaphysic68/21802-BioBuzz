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

    private final Pose start = poseFactory.of(55.2296, 8.6442, 90);
    private final Pose path1 = poseFactory.of(9.4387, 10.0776, -92.666);
    private final Pose path1Control1 = poseFactory.of(10.8623, 45.6231, 0);
    private final Pose point2 = poseFactory.of(54.0747, 119.5583, 179.1695);
    private final Pose point2Control1 = poseFactory.of(29.255, 120.4743, 0);
    private final Pose point3 = poseFactory.of(50.8349, 132.4026, 104.1568);
    private final Pose point4 = poseFactory.of(66.5828, 129.2713, -107.6046);
    private final Pose point4Control1 = poseFactory.of(59.4049, 106.3833, 0);
    private final Pose point5 = poseFactory.of(14.1301, 44.6943, 177.5072);
    private final Pose point5Control1 = poseFactory.of(30.5537, 114.3028, 0);
    private final Pose point5Control2 = poseFactory.of(30.5793, 43.2649, 0);
    private final Pose point6 = poseFactory.of(37.5627, 9.3047, 90.3449);
    private final Pose point6Control1 = poseFactory.of(37.3361, 66.4393, 0);

    public Path path1() {
        return curve(start, path1Control1, path1).tangent();
    }

    public Path path2() {
        return curve(path1, point2Control1, point2).reverseTangent();
    }

    public Path path3() {
        return line(point2, point3).tangent();
    }

    public Path path4() {
        return curve(point3, point4Control1, point4).reverseTangent();
    }

    public Path path5() {
        return curve(point4, point5Control1, point5Control2, point5).tangent();
    }

    public Path path6() {
        return curve(point5, point6Control1, point6).reverseTangent();
    }

    public AutoRunPath(Follower f) {
        addCommands(
                new follow(f,path1()),
                new follow(f,path2()),
                new follow(f,path3()),
                new follow(f,path4()),
                new follow(f,path5()),
                new follow(f,path6())



                );
    }

}