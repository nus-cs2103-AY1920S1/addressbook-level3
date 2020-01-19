package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;

import seedu.mark.model.Model;
import seedu.mark.model.reminder.Reminder;

import seedu.mark.storage.Storage;

/**
 * Deletes a reminder identified using its displayed index from Mark.
 */
public class DeleteReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder-delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by the index number used in the displayed reminder list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 \n"
            + "You can find reminder in dashboard.";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Bookmark: %1$s";

    private final Index targetIndex;

    public DeleteReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNonNull(model);

        List<Reminder> lastShownList = model.getReminders();

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getOneBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        Reminder reminderToDelete = lastShownList.get(targetIndex.getZeroBased());

        model.removeReminder(reminderToDelete);
        model.saveMark(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete));
        model.setReminders();
        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReminderCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteReminderCommand) other).targetIndex)); // state check
    }
}
