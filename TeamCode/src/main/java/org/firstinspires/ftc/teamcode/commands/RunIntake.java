package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class RunIntake extends CommandBase {
    private final Intake intake;
    private final double speed;

    public RunIntake(Intake intake, double speed) {
        this.intake = intake;
        this.speed = speed;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.spin(speed);
    }

    @Override
    public void end(boolean interrupted) {
        intake.spin(0);
    }
}