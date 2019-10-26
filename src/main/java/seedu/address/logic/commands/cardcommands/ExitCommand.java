package seedu.address.logic.commands.cardcommands;

import seedu.address.logic.commands.CardCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends CardCommand {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Dukemon says bye!";

    /**
     * Creates an ExitCommand.
     */
    public ExitCommand() {
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExitCommand); // instanceof handles nulls
    }
}
