package seedu.guilttrip.logic.commands.remindercommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.reminders.Reminder;


/**
 * Deletes a entry identified using it's displayed index from the guilttrip book.
 */
public class DeleteReminderCommand extends Command {

    public static final String COMMAND_WORD = "deleteReminder";

    public static final String ONE_LINER_DESC = COMMAND_WORD
            + ": Deletes the expense reminder identified by the index number used in the displayed reminder list.\n";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ENTRY_SUCCESS = "Deleted GeneralReminder: %1$s";

    private final Index targetIndex;

    public DeleteReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Reminder> lastShownList = model.getFilteredReminders();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }
        Reminder reminderToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteReminder(reminderToDelete);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, reminderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReminderCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteReminderCommand) other).targetIndex)); // state check
    }
}
