package io.xpire.logic.commands;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.XpireItem;
import io.xpire.model.state.ModifiedState;
import io.xpire.model.state.StateManager;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;

//@@author liawsy
/**
 * Shifts an {@Item} back to the main list.
 */
public class ShiftToMainCommand extends Command {
    public static final String COMMAND_WORD = "shift";
    public static final String COMMAND_SHORTHAND = "sh";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Moves the item identified by the index number to the main list.\n"
            + "Format: shift|<index>|<expiry date>[|<quantity>] (index must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "|1|11/2/1997|3" + "\n";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the main list";
    public static final String MESSAGE_SUCCESS = "%s is moved to the main list";

    private XpireItem xpireItem;
    private final Index targetIndex;
    private final ExpiryDate expiryDate;
    private final Quantity quantity;
    private String result = "";

    public ShiftToMainCommand(Index targetIndex, ExpiryDate expiryDate, Quantity quantity) {
        this.targetIndex = targetIndex;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }

    @Override
    public CommandResult execute(Model model, StateManager stateManager) throws CommandException {

        requireNonNull(model);
        stateManager.saveState(new ModifiedState(model));
        List<Item> lastShownList = model.getFilteredReplenishItemList();

        if (this.targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }
        Item targetItem = lastShownList.get(this.targetIndex.getZeroBased());
        XpireItem toShiftItem = adaptItemToXpire(targetItem, expiryDate, quantity);
        this.xpireItem = toShiftItem;
        if (model.hasItem(toShiftItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        } else {
            model.addItem(toShiftItem);
            model.deleteReplenishItem(targetItem);
        }
        this.result = String.format(MESSAGE_SUCCESS, toShiftItem.getName());
        setShowInHistory(true);
        return new CommandResult(this.result);
    }

    /**
     * Changes an Item to an XpireItem.
     * @param item to change into XpireItem.
     * @param expiryDate of the item.
     * @param quantity of the item.
     * @return an XpireItem to be added into the main list.
     */
    private XpireItem adaptItemToXpire(Item item, ExpiryDate expiryDate, Quantity quantity) {
        Name itemName = item.getName();
        Set<Tag> originalTags = item.getTags();
        Set<Tag> newTags = new TreeSet<>(new TagComparator());
        for (Tag tag: originalTags) {
            if (!newTags.contains(tag)) {
                newTags.add(tag);
            }
        }
        return new XpireItem(itemName, expiryDate, quantity, newTags);
    }

    @Override
    public String toString() {
        return "the following Shift command:\n" + this.result;
    }
}
