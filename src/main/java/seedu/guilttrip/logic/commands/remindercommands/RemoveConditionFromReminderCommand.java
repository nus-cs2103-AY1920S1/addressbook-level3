package seedu.guilttrip.logic.commands.remindercommands;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.conditions.DateCondition;
import seedu.guilttrip.model.reminders.conditions.QuotaCondition;
import seedu.guilttrip.model.reminders.conditions.TagsCondition;

/**
 * Removes condition from generalReminder.
 */
public class RemoveConditionFromReminderCommand extends Command {

    public static final String COMMAND_WORD = "removeFromReminder";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Removes a condition to reminder. ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Parameters: CONDITIONINDEX, REMINDERINDEX (must be positive integerS)\n"
            + "Example: " + COMMAND_WORD + " 1, 2";

    public static final String MESSAGE_SUCCESS = "Conditions removed from reminder.";
    public static final String REMINDER_UNMODIFIABLE_MESSAGE =
            "Conditions cannot be removed from this generalReminder \n";
    public static final String CONDITION_ABSENT_MESSAGE = "GeneralReminder does not have that condition \n";
    public static final String REMINDER_NOT_SELECTED = "Please select a reminder to edit";
    public static final String CONDITION_NOT_REMOVABLE = "Reminder must have at least one condition \n";
    public static final String UNSUPPORTED_REMINDER = "Conditions can only be removed from general reminders. \n";

    private boolean toRemoveLowerBound;
    private boolean toRemoveUpperBound;
    private boolean toRemoveStart;
    private boolean toRemoveEnd;
    private boolean toRemoveTags;

    public RemoveConditionFromReminderCommand(boolean toRemoveLowerBound, boolean toRemoveUpperBound,
                                              boolean toRemoveStart, boolean toRemoveEnd, boolean toRemoveTags) {
        this.toRemoveLowerBound = toRemoveLowerBound;
        this.toRemoveUpperBound = toRemoveUpperBound;
        this.toRemoveStart = toRemoveStart;
        this.toRemoveEnd = toRemoveEnd;
        this.toRemoveTags = toRemoveTags;
    }

    private int getDeleteCount() {
        int count = 0;
        if (toRemoveLowerBound) {
            count += 1;
        }
        if (toRemoveUpperBound) {
            count += 1;
        }
        if (toRemoveStart) {
            count += 1;
        }
        if (toRemoveEnd) {
            count += 1;
        }
        if (toRemoveTags) {
            count += 1;
        }
        return count;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Reminder reminder = model.getReminderSelected();
        if (reminder == null) {
            throw new CommandException(REMINDER_NOT_SELECTED);
        }
        if (!(reminder instanceof GeneralReminder)) {
            throw new CommandException(UNSUPPORTED_REMINDER);
        }
        GeneralReminder generalReminder = (GeneralReminder) reminder;
        if (toRemoveLowerBound) {
            removeLowerBound(generalReminder);
        }
        if (toRemoveUpperBound) {
            removeUpperBound(generalReminder);
        }
        if (toRemoveStart) {
            removeStart(generalReminder);
        }
        if (toRemoveEnd) {
            removeEnd(generalReminder);
        }
        if (toRemoveTags) {
            removeTags(generalReminder);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Removes lower bound condition of general reminder.
     * @param reminder
     */
    private void removeLowerBound(GeneralReminder reminder) {
        for (Condition condition : (reminder.getConditions())) {
            if (condition instanceof QuotaCondition) {
                if (((QuotaCondition) condition).isLowerBound()) {
                    reminder.getConditions().remove(condition);
                    break;
                }
            }
        }
    }


    /**
     * Removes upper bound condition of general reminder.
     * @param reminder
     */
    private void removeUpperBound(GeneralReminder reminder) {
        for (Condition condition : (reminder.getConditions())) {
            if (condition instanceof QuotaCondition) {
                if (!((QuotaCondition) condition).isLowerBound()) {
                    reminder.getConditions().remove(condition);
                    break;
                }
            }
        }
    }


    /**
     * Removes start condition of general reminder.
     * @param reminder
     */
    private void removeStart(GeneralReminder reminder) {
        for (Condition condition : (reminder.getConditions())) {
            if (condition instanceof DateCondition) {
                if (((DateCondition) condition).isStart()) {
                    reminder.getConditions().remove(condition);
                }
            }
        }
    }


    /**
     * Removes end condition of general reminder.
     * @param reminder
     */
    private void removeEnd(GeneralReminder reminder) {
        for (Condition condition : (reminder.getConditions())) {
            if (condition instanceof DateCondition) {
                if (!((DateCondition) condition).isStart()) {
                    reminder.getConditions().remove(condition);
                }
            }
        }
    }

    /**
     * Removes tags condition of general reminder.
     * @param reminder
     */
    private void removeTags(GeneralReminder reminder) {
        for (Condition condition : (reminder.getConditions())) {
            if (condition instanceof TagsCondition) {
                reminder.getConditions().remove(condition);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof RemoveConditionFromReminderCommand)) {
            return false;
        } else {
            RemoveConditionFromReminderCommand otherCommand = (RemoveConditionFromReminderCommand) other;
            return this.toRemoveLowerBound == otherCommand.toRemoveLowerBound
                    && this.toRemoveUpperBound == otherCommand.toRemoveUpperBound
                    && this.toRemoveStart == otherCommand.toRemoveStart
                    && this.toRemoveEnd == otherCommand.toRemoveEnd
                    && this.toRemoveTags == otherCommand.toRemoveTags;
        }
    }
}
