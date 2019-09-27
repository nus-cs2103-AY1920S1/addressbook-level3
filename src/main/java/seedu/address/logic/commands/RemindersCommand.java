package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import seedu.address.model.Model;

/**
 * Lists all reminders to the user.
 */
public class RemindersCommand extends Command {

    public static final String COMMAND_WORD = "reminders";

    public static final String MESSAGE_SUCCESS = "Listed all reminders";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
