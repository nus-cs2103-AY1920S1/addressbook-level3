package io.xpire.logic.commands.util;

import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;
import static java.util.Objects.requireNonNull;

import java.util.List;

import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.item.Item;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.XpireItem;
import io.xpire.model.item.exceptions.ItemNotFoundException;
import io.xpire.model.state.ModifiedState;
import io.xpire.model.state.StateManager;

/**
 * Helper functions for commands.
 */
public class CommandUtil {

    public static final String MESSAGE_INVALID_REDUCE_QUANTITY = "Invalid quantity specified. \n"
            + "Quantity must be positive and less than or equals to item's quantity.";

    public static final String MESSAGE_INVALID_INCREASE_QUANTITY =
            "Quantity specified would cause item quantity to exceed maximum limit. \n";

    public static final String MESSAGE_DUPLICATE_ITEM_REPLENISH =
            "A similar item has already been added to the replenish list.\n"
                    + "To add this item, please delete the existing item on the replenish list.\n";

    public static final String MESSAGE_REPLENISH_SHIFT_SUCCESS = "%s is shifted into the replenish list.";

    /**
     * Updates item quantity of an existing item on the list.
     *
     * @param model {@code Model} which the command should operate on.
     * @param existingItem item that exists on the list.
     * @return item with updated quantity.
     * @throws CommandException if the resulting quantity exceeds the maximum limit.
     */
    public static XpireItem updateItemQuantity(StateManager stateManager, Model model, XpireItem existingItem)
            throws CommandException {
        XpireItem itemToReplace = retrieveXpireItemFromList(existingItem, model.getItemList(XPIRE));
        XpireItem itemWithUpdatedQuantity = increaseItemQuantity(itemToReplace, existingItem.getQuantity());
        stateManager.saveState(new ModifiedState(model));
        model.setItem(XPIRE, itemToReplace, itemWithUpdatedQuantity);
        return itemWithUpdatedQuantity;
    }


    /**
     * Reduces xpireItem's quantity by amount specified.
     *
     * @param targetXpireItem XpireItem whose amount will be reduced.
     * @param reduceByQuantity Quantity to be reduced.
     * @return The new XpireItem with its quantity reduced.
     * @throws CommandException if new item quantity is invalid.
     */
    public static XpireItem reduceItemQuantity(XpireItem targetXpireItem, Quantity reduceByQuantity)
            throws CommandException {
        XpireItem targetItemCopy = new XpireItem(targetXpireItem);
        Quantity originalQuantity = targetItemCopy.getQuantity();
        if (originalQuantity.isLessThan(reduceByQuantity)) {
            throw new CommandException(MESSAGE_INVALID_REDUCE_QUANTITY);
        }
        Quantity updatedQuantity = originalQuantity.deductQuantity(reduceByQuantity);
        targetItemCopy.setQuantity(updatedQuantity);
        return targetItemCopy;
    }

    /**
     * Increases the item quantity for any duplicate items.
     *
     * @param targetItem the target item to increase the quantity of.
     * @param quantity to increase by.
     * @return The new item with revised quantity.
     * @throws CommandException if the input quantity causes the new quantity to exceed the maximum limit.
     */
    public static XpireItem increaseItemQuantity(XpireItem targetItem, Quantity quantity) throws CommandException {
        XpireItem targetItemCopy = new XpireItem(targetItem);
        Quantity prevQuantity = targetItemCopy.getQuantity();
        if (prevQuantity.sumExceedsMaximumLimit(quantity)) {
            throw new CommandException(MESSAGE_INVALID_INCREASE_QUANTITY);
        }
        Quantity updatedQuantity = prevQuantity.increaseQuantity(quantity);
        targetItemCopy.setQuantity(updatedQuantity);
        return targetItemCopy;
    }

    /**
     * Retrieves item that is the same as item inputted by user.
     * Used only for Xpire list view.
     *
     * @param item existing in the tracking list.
     * @param list where item is retrieved from.
     * @return exact item which is the same as input item.
     **/
    public static XpireItem retrieveXpireItemFromList(XpireItem item, List<? extends Item> list) {
        requireNonNull(item);
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSameItem(item)) {
                index = i;
            }
        }
        if (index == -1) {
            throw new ItemNotFoundException();
        }
        return (XpireItem) list.get(index);
    }

    /**
     * Shifts an {@code XpireItem} to the Replenish List.
     *
     * @param model {@code Model} which the command should operate on.
     * @param itemToShift to be shifted to the{@code ReplenishList}.
     * @throws CommandException when a similar item already exists on the Replenish List.
     */
    public static void shiftItemToReplenishList(StateManager stateManager, Model model, XpireItem itemToShift)
            throws CommandException {
        Item remodelledItem = itemToShift.remodel();
        if (model.hasItem(REPLENISH, remodelledItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM_REPLENISH);
        }
        stateManager.saveState(new ModifiedState(model));
        model.addItem(REPLENISH, remodelledItem);
        model.deleteItem(XPIRE, itemToShift);
    }
}
