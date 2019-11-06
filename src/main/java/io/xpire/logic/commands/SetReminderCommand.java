package io.xpire.logic.commands;

import static io.xpire.commons.core.Messages.MESSAGE_REMINDER_THRESHOLD_EXCEEDED;
import static io.xpire.commons.core.Messages.MESSAGE_THRESHOLD_ITEM_EXPIRED;
import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.List;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.item.ReminderThreshold;
import io.xpire.model.item.XpireItem;
import io.xpire.model.state.ModifiedState;
import io.xpire.model.state.StateManager;


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
        requireNonNull(model);
        stateManager.saveState(new ModifiedState(model));
        List<XpireItem> lastShownList = model.getFilteredXpireItemList();

        if (this.index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        XpireItem targetItem = lastShownList.get(this.index.getZeroBased());
        XpireItem xpireItemToSetReminder = new XpireItem(targetItem);

        if (xpireItemToSetReminder.isExpired()) {
            throw new CommandException(MESSAGE_THRESHOLD_ITEM_EXPIRED);
        }

        String daysLeft = xpireItemToSetReminder.getExpiryDate().getStatus();
        ReminderThreshold finalThreshold = getValidThreshold(daysLeft);
        xpireItemToSetReminder.setReminderThreshold(finalThreshold);
        this.item = xpireItemToSetReminder;
        model.setItem(targetItem, xpireItemToSetReminder);
        model.updateFilteredItemList(Model.PREDICATE_SHOW_ALL_ITEMS);
        if (isThresholdExceeded(daysLeft)) {
            setShowInHistory(true);
            return new CommandResult(String.format(MESSAGE_REMINDER_THRESHOLD_EXCEEDED, daysLeft));
        } else {
            setShowInHistory(true);
            return new CommandResult(this.threshold.getValue() > 0
                    ? String.format(MESSAGE_SUCCESS_SET, this.item.getName(), this.threshold)
                    : String.format(MESSAGE_SUCCESS_RESET, this.item.getName()));
        }
    }

    private boolean isThresholdExceeded(String daysLeft) {
        int threshold = this.threshold.getValue();
        long remainingDays = Long.parseLong(daysLeft);
        return threshold >= remainingDays;
    }

    private ReminderThreshold getValidThreshold(String daysLeft) {
        if (isThresholdExceeded(daysLeft)) {
            return new ReminderThreshold(daysLeft);
        } else {
            return this.threshold;
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
        if (this.threshold.getValue() == 0) {
            return "the following Set Reminder command:\nThe Item " + this.item.getName()
                    + "'s reminder has been disabled";
        } else {
            return "the following Set Reminder command:\nThe Item " + this.item.getName() + "'s reminder "
                    + "has been set for " + this.threshold + " day(s)";
        }
    }
}
