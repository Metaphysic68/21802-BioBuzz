package org.firstinspires.ftc.teamcode.commands;


import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.Flower;


public class FlowerCommand extends SequentialCommandGroup {
    public FlowerCommand(Flower flower) {
        addCommands(
                new InstantCommand(() -> {
                    flower.setPos(0.425);
                }),
                new WaitCommand(1000),
                new InstantCommand(() -> {
                   flower.setPos(0);
                })
        );
    }

}