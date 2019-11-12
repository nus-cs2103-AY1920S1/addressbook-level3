package io.xpire.logic.commands;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;
import static io.xpire.model.ListType.XPIRE;

import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.logic.commands.util.CommandUtil;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.Model;
import io.xpire.model.item.ExpiryDate;
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
    public static final String MESSAGE_SUCCESS_ITEM_UPDATED = "Quantity for item %s is increased to %s";
    public static final String MESSAGE_SUCCESS_ITEM_ADDED = "New item added to tracking list: %s";

    private XpireItem toAdd;
    private final Name name;
    private final Quantity quantity;
    private final ExpiryDate expiryDate;

    /**
     * Creates an AddCommand to add the specified {@code XpireItem}.
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
     * @param stateManager {@code StackManager} which manages the state of each command.
     * @return success message from {@code CommandResult} if xpireItem is successfully added.
     * @throws ParseException if xpireItem added is a duplicate.
     */
    @Override
    public CommandResult execute(Model model, StateManager stateManager) throws CommandException {
        requireAllNonNull(model, stateManager);
        if (model.hasItem(XPIRE, this.toAdd)) {
            XpireItem itemWithUpdatedQuantity = CommandUtil.updateItemQuantity(stateManager, model, this.toAdd);
            setShowInHistory(true);
            return new CommandResult(String.format(MESSAGE_SUCCESS_ITEM_UPDATED,
                    itemWithUpdatedQuantity.getName(), itemWithUpdatedQuantity.getQuantity()));
        } else {
            stateManager.saveState(new ModifiedState(model));
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

}
