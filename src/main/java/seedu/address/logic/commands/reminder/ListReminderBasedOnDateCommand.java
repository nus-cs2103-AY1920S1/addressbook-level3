package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.reminder.ReminderContainsDatePredicate;

/**
 * Lists all tasks in the address book on a certain date to the user.
 */
public class ListReminderBasedOnDateCommand extends Command {
    public static final String COMMAND_WORD = "find_reminder_by_date";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all reminders on the specific date.\n "
            + "Parameters: DATE\n"
            + "Example: " + COMMAND_WORD + " 20/10/2019";

    private final ReminderContainsDatePredicate predicate;

    public ListReminderBasedOnDateCommand(ReminderContainsDatePredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredReminderList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_REMINDER_LISTED_OVERVIEW, model.getFilteredReminderList().size()),
                false, false, false, false, false,
                false, false, true);
    }
}
