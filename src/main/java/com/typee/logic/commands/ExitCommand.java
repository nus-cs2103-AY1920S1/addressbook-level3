package com.typee.logic.commands;

import com.typee.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Typee as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return other instanceof ExitCommand;
        }
    }
}
