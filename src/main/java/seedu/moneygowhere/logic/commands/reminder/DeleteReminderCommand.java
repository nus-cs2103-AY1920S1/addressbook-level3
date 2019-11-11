package seedu.moneygowhere.logic.commands.reminder;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.commons.core.Messages;
import seedu.moneygowhere.commons.core.index.Index;
import seedu.moneygowhere.logic.commands.CommandResult;
import seedu.moneygowhere.logic.commands.ReminderCommand;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.reminder.Reminder;

/**
 * Deletes a Spending from the Reminder list.
 */
public class DeleteReminderCommand extends ReminderCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = ReminderCommand.COMMAND_WORD
            + " " + COMMAND_WORD
            + ": Deletes the Reminder identified by the index number used in the displayed Reminder list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + ReminderCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";

    private final Index targetIndex;

    private final Logger logger = LogsCenter.getLogger(DeleteReminderCommand.class);

    public DeleteReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Reminder> lastShownReminderList = model.getSortedReminderList();

        if (targetIndex.getZeroBased() >= lastShownReminderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        Reminder reminderToDelete = lastShownReminderList.get(targetIndex.getZeroBased());
        model.deleteReminder(reminderToDelete);
        logger.log(Level.INFO, String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete));
        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReminderCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteReminderCommand) other).targetIndex)); // state check
    }
}
