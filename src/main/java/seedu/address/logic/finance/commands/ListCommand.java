package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.finance.Model.PREDICATE_SHOW_ALL_LOG_ENTRIES;

import seedu.address.model.finance.Model;

/**
 * Lists all log entries in the finance log to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all log entries";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredLogEntryList(PREDICATE_SHOW_ALL_LOG_ENTRIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
