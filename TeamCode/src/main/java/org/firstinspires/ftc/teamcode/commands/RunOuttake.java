package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

public class RunOuttake extends CommandBase {
    private final Outtake outtake;
    private final double speed;

    public RunOuttake(Outtake outtake, double speed) {
        this.outtake = outtake;
        this.speed = speed;
        addRequirements(outtake);
    }

    @Override
    public void execute() {
        outtake.spin(speed);
    }

    @Override
    public void end(boolean interrupted) {
        outtake.spin(0);
    }
}