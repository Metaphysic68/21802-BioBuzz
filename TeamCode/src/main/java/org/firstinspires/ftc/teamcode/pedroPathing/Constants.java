package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PredictiveBrakingCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(9)
            .forwardZeroPowerAcceleration(-38.80294188)
            .lateralZeroPowerAcceleration(-68.28185744)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.04, 0, 0.003, 0))
            .headingPIDFCoefficients(new PIDFCoefficients(2, 0, 0.17, 0))
            .predictiveBrakingCoefficients(new PredictiveBrakingCoefficients(0.067, 0.12067844868410166, 0.0016697580251930728))

            //.drivePIDFCoefficients(new FilteredPIDFCoefficients(0.03, 0, 0.00055, 0, 0.0025))
            .centripetalScaling(0.0005);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .leftFrontMotorName("FL").rightFrontMotorName("FR")
            .leftRearMotorName("BL").rightRearMotorName("BR")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(74.47661007)
            .yVelocity(87.741444);

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(-1.41)
            .strafePodX(3.81)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);

    public static PathConstraints pathConstraints = new PathConstraints(0.95, 100, 1.5, 1);

    public static Follower createFollower(HardwareMap h) {
        return new FollowerBuilder(followerConstants, h)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .build();
    }
}