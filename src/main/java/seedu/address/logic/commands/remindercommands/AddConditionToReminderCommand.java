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
 * Add condition to reminder.
 */
public class AddConditionToReminderCommand extends Command {

    public static final String COMMAND_WORD = "addToReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a condition to reminder. "
            + "Parameters: CONDITIONINDEX, REMINDERINDEX (must be positive integerS)\n"
            + "Example: " + COMMAND_WORD + " 1, 2";

    public static final String MESSAGE_SUCCESS = "New Reminder added: %1$s";

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
        Condition condition = conditions.get(conditionIndex.getZeroBased());
        reminder.addCondition(condition);
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
