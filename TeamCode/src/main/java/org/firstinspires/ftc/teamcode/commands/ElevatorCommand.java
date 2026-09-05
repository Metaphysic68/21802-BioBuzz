package org.firstinspires.ftc.teamcode.commands;


import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Elevator;

public class ElevatorCommand extends CommandBase {

    private final Elevator elevator;
    private final int targetTicks;

    public ElevatorCommand(Elevator elevator, int targetTicks) {
        this.elevator = elevator;
        this.targetTicks = targetTicks;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setTargetPosition(targetTicks);
    }

    @Override
    public boolean isFinished() {
        return elevator.isAtTarget();
    }
}