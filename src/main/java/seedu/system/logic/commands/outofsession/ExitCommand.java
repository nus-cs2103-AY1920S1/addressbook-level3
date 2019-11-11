package seedu.system.logic.commands.outofsession;

import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.InSessionCommandException;
import seedu.system.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final CommandType COMMAND_TYPE = CommandType.GENERAL;
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model) throws InSessionCommandException {
        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof ExitCommand; // instanceof handles nulls
    }

}
