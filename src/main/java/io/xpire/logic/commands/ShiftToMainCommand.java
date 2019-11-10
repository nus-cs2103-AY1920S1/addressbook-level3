package io.xpire.logic.commands;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;
import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;
import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;

import java.util.List;

import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.XpireItem;
import io.xpire.model.state.ModifiedState;
import io.xpire.model.state.StateManager;

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

    private final Index targetIndex;
    private final ExpiryDate expiryDate;
    private final Quantity quantity;
    private String result;

    public ShiftToMainCommand(Index targetIndex, ExpiryDate expiryDate, Quantity quantity) {
        this.targetIndex = targetIndex;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.result = "";
    }

    @Override
    public CommandResult execute(Model model, StateManager stateManager) throws CommandException {
        requireAllNonNull(model, stateManager);
        this.requireNonEmptyCurrentList(model);

        List<? extends Item> lastShownList = model.getCurrentList();
        if (this.targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item targetItem = lastShownList.get(this.targetIndex.getZeroBased());
        XpireItem remodelledItem = targetItem.remodel(this.expiryDate, this.quantity);

        if (model.hasItem(XPIRE, remodelledItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        } else {
            stateManager.saveState(new ModifiedState(model));
            model.addItem(XPIRE, remodelledItem);
            model.deleteItem(REPLENISH, targetItem);
        }

        this.result = String.format(MESSAGE_SUCCESS, remodelledItem.getName());
        setShowInHistory(true);
        return new CommandResult(this.result);
    }

    @Override
    public String toString() {
        return "Shift command";
    }
}
