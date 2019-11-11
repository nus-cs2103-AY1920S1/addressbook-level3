package seedu.weme.logic.commands.generalcommand;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": exit Weme";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION;

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Weme as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
