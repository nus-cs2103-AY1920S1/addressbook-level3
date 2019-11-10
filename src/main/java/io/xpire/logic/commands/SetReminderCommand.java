package io.xpire.logic.commands;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;
import static io.xpire.model.ListType.XPIRE;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.item.Item;
import io.xpire.model.item.ReminderThreshold;
import io.xpire.model.item.XpireItem;
import io.xpire.model.state.ModifiedState;
import io.xpire.model.state.StateManager;
import javafx.collections.ObservableList;


/**
 * Changes the reminder threshold for an xpireItem identified with its displayed index.
 */

public class SetReminderCommand extends Command {

    public static final String COMMAND_WORD = "set reminder";
    public static final String COMMAND_SHORTHAND = "sr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the reminder threshold of the item "
            + "identified with its displayed index. "
            + "Existing threshold will be overwritten by the input.\n"
            + "Format: set reminder|<index>|<threshold> (both index and threshold must be positive numbers)\n"
            + "Example: " + COMMAND_WORD + "|1|7";
    public static final String MESSAGE_SUCCESS_SET = "Reminder for item %s has been set to %s day(s)"
            + " before expiry date";
    public static final String MESSAGE_SUCCESS_RESET = "Disabled reminder for item %s";
    public static final String MESSAGE_REMINDER_THRESHOLD_EXCEEDED =
            "The item has only %s day(s) left before expiring. \nReminder will start from today!";
    public static final String MESSAGE_THRESHOLD_ITEM_EXPIRED = "Cannot set reminder for expired item!";

    private final Index index;
    private final ReminderThreshold threshold;
    private XpireItem item = null;

    /**
     * @param index Index of the xpireItem in the list.
     * @param threshold New threshold.
     */
    public SetReminderCommand(Index index, ReminderThreshold threshold) {
        requireAllNonNull(index, threshold);

        this.index = index;
        this.threshold = threshold;
    }

    @Override
    public CommandResult execute(Model model, StateManager stateManager) throws CommandException {
        requireAllNonNull(model, stateManager);
        this.requireNonEmptyCurrentList(model);
        ObservableList<? extends Item> currentList = model.getCurrentList();

        if (this.index.getZeroBased() >= currentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
        }

        XpireItem targetItem = (XpireItem) currentList.get(this.index.getZeroBased());
        XpireItem xpireItemToSetReminder = new XpireItem(targetItem);

        if (xpireItemToSetReminder.isExpired()) {
            throw new CommandException(MESSAGE_THRESHOLD_ITEM_EXPIRED);
        }

        String daysLeft = xpireItemToSetReminder.getExpiryDate().getStatus();
        setShowInHistory(true);

        if (!ReminderThreshold.isValidReminderThreshold(this.threshold.toString(), daysLeft)) {
            ReminderThreshold newThreshold = new ReminderThreshold(daysLeft);
            xpireItemToSetReminder.setReminderThreshold(newThreshold);
            this.item = xpireItemToSetReminder;
            stateManager.saveState(new ModifiedState(model));
            model.setItem(XPIRE, targetItem, xpireItemToSetReminder);
            return new CommandResult(String.format(MESSAGE_REMINDER_THRESHOLD_EXCEEDED, daysLeft));
        } else {
            xpireItemToSetReminder.setReminderThreshold(this.threshold);
            this.item = xpireItemToSetReminder;
            stateManager.saveState(new ModifiedState(model));
            model.setItem(XPIRE, targetItem, xpireItemToSetReminder);
            return new CommandResult(this.threshold.getValue() > 0
                    ? String.format(MESSAGE_SUCCESS_SET, this.item.getName(), this.threshold)
                    : String.format(MESSAGE_SUCCESS_RESET, this.item.getName()));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SetReminderCommand)) {
            return false;
        } else {
            SetReminderCommand other = (SetReminderCommand) obj;
            return this.index.equals(other.index) && this.threshold.equals(other.threshold);
        }
    }

    @Override
    public String toString() {
        return "Set Reminder command";
    }
}
