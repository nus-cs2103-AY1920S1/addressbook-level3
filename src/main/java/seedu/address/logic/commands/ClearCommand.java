package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_UNUSED_ARGUMENT;

import seedu.address.model.Model;

/**
 * Displays all books - clears the search filter
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Resets filtered book list. \n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Search results have been cleared!";
    private String unusedArguments = null;

    public ClearCommand() {}

    public ClearCommand(String unusedArguments) {
        if (!unusedArguments.equals("")) {
            this.unusedArguments = unusedArguments;
        }
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetFilteredBookList();
        if (unusedArguments != null) {
            return new CommandResult(String.format(MESSAGE_SUCCESS
                + MESSAGE_UNUSED_ARGUMENT, unusedArguments, COMMAND_WORD));
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
