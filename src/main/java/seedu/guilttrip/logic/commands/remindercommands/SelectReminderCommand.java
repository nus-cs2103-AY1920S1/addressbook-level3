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
 * Select the generalReminder for editing.
 */
public class SelectReminderCommand extends Command {
    public static final String COMMAND_WORD = "selectReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Select the reminder identified by the index number used in the "
            + "displayed generalReminder list for editing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMINDER_SELECTED_SUCCESS = "Selected reminder: %1$s";
    public final Index targetIndex;

    public SelectReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Reminder> lastShownList = model.getFilteredReminders();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }
        Reminder reminderToInspect = lastShownList.get(targetIndex.getZeroBased());
        model.selectReminder(reminderToInspect);
        return new CommandResult(String.format(MESSAGE_REMINDER_SELECTED_SUCCESS, reminderToInspect));
    }
}
