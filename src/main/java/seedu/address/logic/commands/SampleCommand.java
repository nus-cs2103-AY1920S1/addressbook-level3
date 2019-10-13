package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Just a sample command.
 */
// todo: remove this class before v1.3
public class SampleCommand extends Command {
    public static final String COMMAND_WORD = "sample";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows you your calendar.\n"
            + "Example: " + COMMAND_WORD;
    // todo-this-week: update value of showing_...__message
    // value should be "diary", "itinerary", "calendar", "financial_tracker", or "address_book"
    public static final String SHOWING_SAMPLE_MESSAGE = "sample";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_SAMPLE_MESSAGE, false, false, true);
    }
}
