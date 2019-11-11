package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all reminders in the address book to the user.
 */
public class ListReminderCommand extends Command {
    public static final String COMMAND_WORD = "list_reminder";

    public static final String MESSAGE_SUCCESS = "Listed all Reminders";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        return new CommandResult(MESSAGE_SUCCESS,
                false, false, false, false, false,
                false, false, true);
    }
}
