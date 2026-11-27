package org.firstinspires.ftc.teamcode.commands;


import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Stopper;

public class ShootCommand extends SequentialCommandGroup {
    public ShootCommand(Stopper stopper, Intake intake) {
        addCommands(
                new InstantCommand(() -> {
                    stopper.setPos(0);
                    intake.setPower(1);
                }),
                new WaitCommand(500),
                new InstantCommand(() -> {
                    stopper.setPos(0.2);
                })
        );
    }

}