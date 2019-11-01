package seedu.address.logic.commands.remindercommands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.conditions.Condition;

/**
 * Removes condition from reminder.
 */
public class RemoveConditionFromReminderCommand extends Command {

    public static final String COMMAND_WORD = "removeFromReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a condition to reminder. "
            + "Parameters: CONDITIONINDEX, REMINDERINDEX (must be positive integerS)\n"
            + "Example: " + COMMAND_WORD + " 1, 2";

    public static final String MESSAGE_SUCCESS = "Condition Removed: %1$s";
    public static final String REMINDER_UNMODIFIABLE_MESSAGE = "Conditions cannot be removed from this reminder \n";
    public static final String CONDITION_ABSENT_MESSAGE = "Reminder does not have that condition \n";
    public static final String CONDITION_NOT_REMOVABLE = "Reminder must have at least one condition \n";

    private Index reminderIndex;
    private Index conditionIndex;

    public RemoveConditionFromReminderCommand(Index reminderIndex, Index conditionIndex) {
        this.reminderIndex = reminderIndex;
        this.conditionIndex = conditionIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        List<Reminder> reminders = model.getFilteredReminders();
        List<Condition> conditions = model.getFilteredConditions();
        Reminder reminder = reminders.get(reminderIndex.getZeroBased());
        if (reminder.getEntrySpecificity() || reminder.getConditions().size() == 1) {
            throw new CommandException(REMINDER_UNMODIFIABLE_MESSAGE);
        }
        Condition condition = conditions.get(conditionIndex.getZeroBased());
        if (reminder.getConditions().size() == 1) {
            throw new CommandException(CONDITION_NOT_REMOVABLE);
        }
        if (!reminder.removeCondition(condition)) {
            throw new CommandException(CONDITION_ABSENT_MESSAGE);
        }
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, reminder));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof RemoveConditionFromReminderCommand)) {
            return false;
        } else {
            RemoveConditionFromReminderCommand otherCommand = (RemoveConditionFromReminderCommand) other;
            return this.conditionIndex.equals(otherCommand.conditionIndex)
                    && this.reminderIndex.equals(otherCommand.reminderIndex);
        }
    }
}
