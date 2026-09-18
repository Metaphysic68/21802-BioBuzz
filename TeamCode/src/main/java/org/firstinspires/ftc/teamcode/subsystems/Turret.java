package org.firstinspires.ftc.teamcode.subsystems;

import static com.seattlesolvers.solverslib.util.MathUtils.clamp;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Velocity;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.utils.RobotConstants;
import org.firstinspires.ftc.teamcode.utils.ShootingMath;

public class Turret extends SubsystemBase {

    private final Servo turret;
    public double targetAngle;
    public double targetPosition;

    @Config
    public static class TurretConstants {
        public static double servoRange = 350;
        public static double centerOffsetDegrees = 0;
    }

    public Turret(HardwareMap hardwareMap) {
        turret = hardwareMap.get(Servo.class, "turret");
        turret.setDirection(Servo.Direction.FORWARD);
    }
    public void setTargetAngleRED(Pose robotPose, Velocity robotVelocity){
        setTargetAngleForGoal(RobotConstants.RED_GOAL_RIGHT, robotPose, robotVelocity);
        if (robotPose.y() > 72){
            setTargetAngleForGoal(RobotConstants.RED_GOAL_LEFT, robotPose, robotVelocity);

        }
    }
    public void setTargetAngleBLUE(Pose robotPose, Velocity robotVelocity){
        setTargetAngleForGoal(RobotConstants.BLUE_GOAL_RIGHT, robotPose, robotVelocity);
        if (robotPose.y() < 72){
            setTargetAngleForGoal(RobotConstants.BLUE_GOAL_LEFT, robotPose, robotVelocity);

        }
    }

    private void setTargetAngleForGoal(Pose goal, Pose robotPose, Velocity robotVelocity){
        Pose virtualPose = ShootingMath.calculateVirtualRobot(robotPose, robotVelocity, goal);

        double fieldRadians = Math.atan2(goal.y() - virtualPose.y(), goal.x() - virtualPose.x());
        double angleDegrees = Math.toDegrees(fieldRadians - robotPose.heading());

        while (angleDegrees > 180) angleDegrees -= 360;
        while (angleDegrees <= -180) angleDegrees += 360;

        targetAngle = angleDegrees;

        double pos = 0.5 + (angleDegrees+TurretConstants.centerOffsetDegrees) / TurretConstants.servoRange;

        targetPosition = clamp(pos,0,1);
        turret.setPosition(targetPosition);

    }

    public double getTargetAngle(){
        return targetAngle;
    }

    public double getPosition(){
        return turret.getPosition();
    }
}
