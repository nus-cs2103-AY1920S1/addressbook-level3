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
 * Add condition to generalReminder.
 */
public class AddConditionToReminderCommand extends Command {

    public static final String COMMAND_WORD = "addToReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a condition to generalReminder. "
            + "Parameters: REMINDERINDEX, CONDITIONINDEX, (must be positive integerS)\n"
            + "Example: " + COMMAND_WORD + " 1, 2";

    public static final String MESSAGE_SUCCESS = "New Condition added: %1$s";
    public static final String REMINDER_UNMODIFIABLE_MESSAGE = "Conditions cannot be added to this generalReminder \n"
            + "This generalReminder is entry specific";
    public static final String CONDITION_PRESENT_MESSAGE = "GeneralReminder already has that condition \n";

    private Index reminderIndex;
    private Index conditionIndex;

    public AddConditionToReminderCommand(Index reminderIndex, Index conditionIndex) {
        this.reminderIndex = reminderIndex;
        this.conditionIndex = conditionIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Reminder> Reminders = model.getFilteredReminders();
        List<Condition> conditions = model.getFilteredConditions();
        Reminder reminder = Reminders.get(reminderIndex.getZeroBased());
        if (!(reminder instanceof GeneralReminder)) {
            throw new CommandException(REMINDER_UNMODIFIABLE_MESSAGE);
        }
        GeneralReminder generalReminder = (GeneralReminder) reminder;
        Condition condition = conditions.get(conditionIndex.getZeroBased());
        if (!generalReminder.addCondition(condition)) {
            throw new CommandException(CONDITION_PRESENT_MESSAGE);
        }
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, generalReminder));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof AddConditionToReminderCommand)) {
            return false;
        } else {
            AddConditionToReminderCommand otherCommand = (AddConditionToReminderCommand) other;
            return this.conditionIndex.equals(otherCommand.conditionIndex)
                    && this.reminderIndex.equals(otherCommand.reminderIndex);
        }
    }
}
