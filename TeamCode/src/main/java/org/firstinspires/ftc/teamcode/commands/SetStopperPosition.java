package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.Stopper;

public class SetStopperPosition extends CommandBase {
    private final Stopper stopper;
    private final double targetPosition;

    public SetStopperPosition(Stopper stopper, double targetPosition) {
        this.stopper = stopper;
        this.targetPosition = targetPosition;
        addRequirements(stopper);
    }

    @Override
    public void initialize() {
        stopper.setPos(targetPosition);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}