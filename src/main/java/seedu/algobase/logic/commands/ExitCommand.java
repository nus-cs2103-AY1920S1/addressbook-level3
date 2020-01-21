package seedu.algobase.logic.commands;

import seedu.algobase.logic.CommandHistory;
import seedu.algobase.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": exits AlgoBase.\n"
            + "Example:\n"
            + COMMAND_WORD;

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting AlgoBase as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
