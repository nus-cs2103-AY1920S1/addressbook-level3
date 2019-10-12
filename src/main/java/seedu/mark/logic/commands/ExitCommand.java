package seedu.mark.logic.commands;

import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.ExitCommandResult;
import seedu.mark.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Mark as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new ExitCommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
