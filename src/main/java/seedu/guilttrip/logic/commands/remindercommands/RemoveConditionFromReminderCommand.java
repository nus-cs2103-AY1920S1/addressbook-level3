package seedu.guilttrip.logic.commands.remindercommands;

import java.util.List;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.reminders.EntryReminder;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.conditions.TypeCondition;

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
    public static final String CANNOT_REMOVE_TYPE_CONDITION = "Cannot remove Entry Type Condition \n";
    public static final String CONDITION_NOT_REMOVABLE = "GeneralReminder must have at least one condition \n";
    public static final String INCORRECT_TYPE = "Cannot remove conditions from Entry Reminder.\n";
    public static final String REMINDER_NOT_SELECTED = "Please select a reminder to edit";

    private Index conditionIndex;

    public RemoveConditionFromReminderCommand(Index conditionIndex) {
        this.conditionIndex = conditionIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Reminder reminder = model.getReminderSelected();
        if (reminder == null) {
            throw new CommandException(REMINDER_NOT_SELECTED);
        }
        if (reminder instanceof EntryReminder) {
            throw new CommandException(INCORRECT_TYPE);
        }
        GeneralReminder generalReminder = (GeneralReminder) reminder;
        List<Condition> conditions = generalReminder.getConditions();
        if (conditionIndex.getZeroBased() >= conditions.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }
        if (conditions.size() <= 1) {
            throw new CommandException(CONDITION_NOT_REMOVABLE);
        }
        Condition toRemove = conditions.get(conditionIndex.getZeroBased());
        if (toRemove instanceof TypeCondition) {
            throw new CommandException(CANNOT_REMOVE_TYPE_CONDITION);
        }
        conditions.remove(toRemove);
        GeneralReminder newReminder = new GeneralReminder(reminder.getHeader(), conditions);
        model.setReminder(reminder, newReminder);
        model.selectReminder(newReminder);
        model.updateFilteredReminders(model.PREDICATE_SHOW_ALL_REMINDERS);
        model.commitGuiltTrip();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof RemoveConditionFromReminderCommand)) {
            return false;
        } else {
            RemoveConditionFromReminderCommand otherCommand = (RemoveConditionFromReminderCommand) other;
            return this.conditionIndex.equals(otherCommand.conditionIndex);
        }
    }
}
