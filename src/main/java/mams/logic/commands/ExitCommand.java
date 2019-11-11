package mams.logic.commands;

import mams.logic.history.FilterOnlyCommandHistory;
import mams.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting MAMS as requested ...";

    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, false,
                false, true);
    }

}
