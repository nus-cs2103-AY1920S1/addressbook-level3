package seedu.guilttrip.logic.commands.remindercommands;

import java.util.List;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;

/**
 * Removes condition from generalReminder.
 */
public class RemoveConditionFromReminderCommand extends Command {

    public static final String COMMAND_WORD = "removeCondition";
    public static final String ONE_LINER_DESC = COMMAND_WORD + "Removes a condition to generalReminder. \n";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Parameters: CONDITIONINDEX, REMINDERINDEX (must be positive integerS)\n"
            + "Example: " + COMMAND_WORD + " 1, 2";

    public static final String MESSAGE_SUCCESS = "Condition Removed: %1$s";
    public static final String REMINDER_UNMODIFIABLE_MESSAGE =
            "Conditions cannot be removed from this generalReminder \n";
    public static final String CONDITION_ABSENT_MESSAGE = "GeneralReminder does not have that condition \n";
    public static final String CONDITION_NOT_REMOVABLE = "GeneralReminder must have at least one condition \n";

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
        GeneralReminder generalReminder = (GeneralReminder) reminders.get(reminderIndex.getZeroBased());
        if (!(generalReminder instanceof GeneralReminder) || generalReminder.getConditions().size() == 1) {
            throw new CommandException(REMINDER_UNMODIFIABLE_MESSAGE);
        }
        Condition condition = conditions.get(conditionIndex.getZeroBased());
        if (generalReminder.getConditions().size() == 1) {
            throw new CommandException(CONDITION_NOT_REMOVABLE);
        }
        if (!generalReminder.removeCondition(condition)) {
            throw new CommandException(CONDITION_ABSENT_MESSAGE);
        }
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_SUCCESS, generalReminder));
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
