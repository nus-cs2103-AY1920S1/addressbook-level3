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

    public static final String SHOWING_SAMPLE_MESSAGE = "sample";
    // todo-this-week: above showing_..._message should be the same as toString() value of the page which this command shows, see SamplePage

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_SAMPLE_MESSAGE, false, false, true);
    }
}
