package io.xpire.logic.commands;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.item.XpireItem;
import io.xpire.model.item.exceptions.ItemNotFoundException;
import io.xpire.model.state.ModifiedState;
import io.xpire.model.state.StateManager;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;

//@@author liawsy
/**
 * Shifts a {@code XpireItem} to the Replenish List.
 */
public class ShiftToReplenishCommand extends Command {

    public static final String COMMAND_WORD = "shift";
    public static final String COMMAND_SHORTHAND = "sh";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Moves the item identified by the index number to the replenish list.\n"
            + "Format: shift|<index> (index must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "|1" + "\n";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the Replenish List";
    public static final String MESSAGE_SUCCESS = "%s is moved to the Replenish List";
    private static final Tag EXPIRED_TAG = new Tag("Expired");

    private Item replenishItem;
    private final Index targetIndex;
    private String result = "";

    public ShiftToReplenishCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, StateManager stateManager) throws CommandException {

        requireNonNull(model);
        stateManager.saveState(new ModifiedState(model));
        List<XpireItem> lastShownList = model.getFilteredXpireItemList();

        if (this.targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        XpireItem targetItem = lastShownList.get(this.targetIndex.getZeroBased());
        Item replenishItem = adaptItemToReplenish(targetItem);
        this.replenishItem = replenishItem;
        if (model.hasReplenishItem(replenishItem)) {
            try {
                model.deleteItem(targetItem);
            } catch (ItemNotFoundException e) {
                throw new CommandException(MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
            }
        } else {
            model.addReplenishItem(this.replenishItem);
            model.deleteItem(targetItem);
        }
        this.result = String.format(MESSAGE_SUCCESS, replenishItem.getName());
        setShowInHistory(true);
        return new CommandResult(this.result);
    }

    /**
     * Changes an XpireItem to an Item.
     * @param xpireItem to change into Item.
     * @return The new Item.
     */
    private Item adaptItemToReplenish(XpireItem xpireItem) {
        Name itemName = xpireItem.getName();
        Set<Tag> originalTags = xpireItem.getTags();
        Set<Tag> newTags = new TreeSet<>(new TagComparator());
        for (Tag tag: originalTags) {
            if (!newTags.contains(tag) && !tag.equals(EXPIRED_TAG)) {
                newTags.add(tag);
            }
        }
        return new Item(itemName, newTags);
    }

    @Override
    public String toString() {
        return "the following Shift command:\n" + this.result;
    }
}
