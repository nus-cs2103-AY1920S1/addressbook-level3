package io.xpire.logic.commands;

import static io.xpire.model.ListType.XPIRE;
import static java.util.Objects.requireNonNull;

import java.util.List;

import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.Model;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.XpireItem;
import io.xpire.model.state.ModifiedState;
import io.xpire.model.state.StateManager;

/**
 * Adds an xpireItem to the list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_SHORTHAND = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to the tracking list.\n"
            + "Format: add|<item name>|<expiry date>[|<quantity>]\n"
            + "Example: " + COMMAND_WORD + "|Strawberry|11/12/1999|2";

    public static final String MESSAGE_SUCCESS = "New item added to tracking list: %s";
    public static final String MESSAGE_SUCCESS_ITEM_UPDATED = "Quantity for item is increased to %s";
    public static final String MESSAGE_SUCCESS_ITEM_ADDED = "New item added to tracking list: %s";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists";

    private String result = "";
    private XpireItem toAdd;
    private final Name name;
    private final Quantity quantity;
    private final ExpiryDate expiryDate;

    /**
     * Creates an AddCommand to add the specified {@code XpireItem}
     */
    public AddCommand(Name name, ExpiryDate expiryDate, Quantity quantity) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.toAdd = new XpireItem(this.name, this.expiryDate, this.quantity);
    }

    /**
     * Executes {@code AddCommand}.
     *
     * @param model {@code Model} which the command should operate on.
     * @param stateManager
     * @return success message from {@code CommandResult} if xpireItem is successfully added.
     * @throws CommandException if xpireItem added is a duplicate.
     */
    @Override
    public CommandResult execute(Model model, StateManager stateManager) throws ParseException {
        requireNonNull(model);
        requireNonNull(stateManager);
        stateManager.saveState(new ModifiedState(model));
        if (model.hasItem(XPIRE, this.toAdd)) {
            XpireItem itemToReplace = retrieveXpireItem(this.toAdd, model.getItemList(XPIRE));
            XpireItem itemWithUpdatedQuantity = increaseItemQuantity(new XpireItem(itemToReplace), this.quantity);
            model.setItem(XPIRE, itemToReplace, itemWithUpdatedQuantity);
            setShowInHistory(true);
            return new CommandResult(String.format(MESSAGE_SUCCESS_ITEM_UPDATED,
                    itemWithUpdatedQuantity.getQuantity()));
        } else {
            model.addItem(XPIRE, toAdd);
            setShowInHistory(true);
            return new CommandResult(String.format(MESSAGE_SUCCESS_ITEM_ADDED, toAdd));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof AddCommand)) {
            return false;
        } else {
            AddCommand other = (AddCommand) obj;
            return this.toAdd.equals(other.toAdd);
        }
    }

    @Override
    public int hashCode() {
        return this.toAdd.hashCode();
    }

    @Override
    public String toString() {
        return "Add command";
    }

    /**
     * Retrieves item that is the same as item inputted by user.
     * @param item existing in the tracking list.
     * @param list where item is retrieved from.
     * @return exact item which is the same as input item.
     **/
    private XpireItem retrieveXpireItem(XpireItem item, List<? extends Item> list) {
        requireNonNull(item);
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSameItem(item)) {
                index = i;
            }
        }
        return (XpireItem) list.get(index);
    }

    /**
     * Increases the item quantity for any duplicate items.
     * @param targetItem
     * @param quantity
     * @return
     * @throws ParseException
     */
    private XpireItem increaseItemQuantity(XpireItem targetItem, Quantity quantity) throws ParseException {
        Quantity prevQuantity = targetItem.getQuantity();
        Quantity updatedQuantity = prevQuantity.increaseQuantity(quantity);
        targetItem.setQuantity(updatedQuantity);
        return targetItem;
    }

}
