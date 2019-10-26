package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists the available policies in the address book.
 */
public class ReportCommand extends Command {

    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates report of Key Performance Indicators (KPI).";

    public static final String MESSAGE_SUCCESS = "Report generated";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false,
            false, true, false, false, false, false, false);
    }
}
