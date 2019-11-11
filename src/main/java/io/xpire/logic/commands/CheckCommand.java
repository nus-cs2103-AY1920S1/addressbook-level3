package io.xpire.logic.commands;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;
import static io.xpire.model.ListType.XPIRE;

import java.util.function.Predicate;

import io.xpire.model.Model;
import io.xpire.model.item.ExpiringSoonPredicate;
import io.xpire.model.item.ReminderThresholdExceededPredicate;
import io.xpire.model.item.XpireItem;
import io.xpire.model.state.FilteredState;
import io.xpire.model.state.StateManager;

/**
 * Displays all items whose expiry date falls within the specified duration (in days).
 */
public class CheckCommand extends Command {
    public static final String COMMAND_WORD = "check";
    public static final String COMMAND_SHORTHAND = "ch";

    public static final String MESSAGE_SUCCESS_DAYS_LEFT = "Listed expired items and items with no more than "
            + "%d day(s) left before their expiry date.";
    public static final String MESSAGE_SUCCESS_REMIND = "Listed expired items and items with active reminders.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all items whose expiry date is within"
            + "the specified duration (in days). Expired items, if any, are also included in the list.\n"
            + "Format: check[|<days>] (days must be a non negative number)\n"
            + "Example: " + COMMAND_WORD + "|7\n"
            + "If no duration is specified, expired items and items whose reminder "
            + "has been activated will be displayed.";

    public static final String MESSAGE_EXCEEDED_MAX = "Maximum number of days that can be checked is 36500 days";
    private String message;
    private final Predicate<XpireItem> predicate;

    public CheckCommand(ExpiringSoonPredicate predicate, int days) {
        this.predicate = predicate;
        this.message = String.format(MESSAGE_SUCCESS_DAYS_LEFT, days);
    }

    public CheckCommand(ReminderThresholdExceededPredicate predicate) {
        this.predicate = predicate;
        this.message = MESSAGE_SUCCESS_REMIND;
    }

    @Override
    public CommandResult execute(Model model, StateManager stateManager) {
        requireAllNonNull(model, stateManager);
        stateManager.saveState(new FilteredState(model));

        model.filterCurrentList(XPIRE, this.predicate);

        setShowInHistory(true);
        return new CommandResult(this.message);
    }

    /**
     * Returns the message corresponding to the command.
     */
    public String getMessage() {
        return this.message;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof CheckCommand)) {
            return false;
        } else {
            CheckCommand other = (CheckCommand) obj;
            return this.predicate.equals(other.predicate);
        }
    }

    @Override
    public int hashCode() {
        return this.predicate.hashCode();
    }

    @Override
    public String toString() {
        return "Check command";
    }
}
