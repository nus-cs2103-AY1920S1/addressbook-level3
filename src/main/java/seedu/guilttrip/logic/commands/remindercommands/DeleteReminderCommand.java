package seedu.guilttrip.logic.commands.remindercommands;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.reminders.Reminder;

/**
 * Deletes a reminder identified using it's displayed index from GuiltTrip.
 */
public class DeleteReminderCommand extends Command {

    public static final String COMMAND_WORD = "deleteReminder";

    public static final String ONE_LINER_DESC = COMMAND_WORD
            + ": Deletes the expense reminder identified by the index number used in the displayed reminder list.\n";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " ";

    public static final String MESSAGE_DELETE_ENTRY_SUCCESS = "Deleted GeneralReminder: %1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Reminder reminder = model.getReminderSelected();
        model.deleteReminder(reminder);
        return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, reminder));
    }

}
