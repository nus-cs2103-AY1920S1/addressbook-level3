package seedu.moneygowhere.logic.commands;

import seedu.moneygowhere.model.Model;

/**
 * Represents the Currency command.
 */
public class CurrencyCommand extends Command {

    public static final String COMMAND_WORD = "currency";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the currency used in the application.\n"
            + "Example: " + COMMAND_WORD + " sgd";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("It works");
    }
}
