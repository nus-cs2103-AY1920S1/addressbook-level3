package seedu.guilttrip.logic.commands.remindercommands;

import java.util.List;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;

/**
 * Add condition to reminder.
 */
public class AddConditionToReminderCommand extends Command {

    public static final String COMMAND_WORD = "addToReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a condition to reminder. "
            + "Parameters: REMINDERINDEX, CONDITIONINDEX, (must be positive integerS)\n"
            + "Example: " + COMMAND_WORD + " 1, 2";

    public static final String MESSAGE_SUCCESS = "New Condition added: %1$s";
    public static final String REMINDER_UNMODIFIABLE_MESSAGE = "Conditions cannot be added to this reminder \n"
            + "This reminder is entry specific";
    public static final String CONDITION_PRESENT_MESSAGE = "Reminder already has that condition \n";

    private Index reminderIndex;
    private Index conditionIndex;

    public AddConditionToReminderCommand(Index reminderIndex, Index conditionIndex) {
        this.reminderIndex = reminderIndex;
        this.conditionIndex = conditionIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Reminder> reminders = model.getFilteredReminders();
        List<Condition> conditions = model.getFilteredConditions();
        Reminder reminder = reminders.get(reminderIndex.getZeroBased());
        if (reminder.getEntrySpecificity()) {
            throw new CommandException(REMINDER_UNMODIFIABLE_MESSAGE);
        }
        Condition condition = conditions.get(conditionIndex.getZeroBased());
        if (!reminder.addCondition(condition)) {
            throw new CommandException(CONDITION_PRESENT_MESSAGE);
        }
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, reminder));
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
