package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ReminderThreshold;

/**
 * Changes the reminder threshold for a particular item in the list.
 */

public class SetReminderCommand extends Command {

    public static final String COMMAND_WORD = "set reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the reminder threshold of the item "
            + "identified by the index number used in the last item listing. "
            + "Existing threshold will be overwritten by the input.\n"
            + "Parameters: <index>(must be a positive integer) <threshold>(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "|1|7";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "set reminder command not implemented yet";

    public static final String MESSAGE_ARGUMENTS = "Index: %d, Threshold: %d";

    public static final String MESSAGE_SUCCESS = "Set reminder for item %d in %s day(s)";

    private final Index index;
    private final String threshold;

    /**
     * @param index Index of the item in the list.
     * @param threshold New threshold.
     */
    public SetReminderCommand(Index index, String threshold) {
        requireAllNonNull(index, threshold);

        this.index = index;
        this.threshold = threshold;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToSetReminder = lastShownList.get(index.getZeroBased());
        Item editedItem = itemToSetReminder;
        editedItem.setReminderThreshold(new ReminderThreshold(threshold));

        model.setItem(itemToSetReminder, editedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, index.getOneBased(), threshold));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SetReminderCommand)) {
            return false;
        } else {
            SetReminderCommand other = (SetReminderCommand) obj;
            return this.index.equals(other.index) && this.threshold == other.threshold;
        }
    }
}
