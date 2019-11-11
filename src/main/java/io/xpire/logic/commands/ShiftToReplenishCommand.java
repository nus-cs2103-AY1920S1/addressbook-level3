package io.xpire.logic.commands;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;
import static io.xpire.logic.commands.util.CommandUtil.MESSAGE_DUPLICATE_ITEM_REPLENISH;
import static io.xpire.logic.commands.util.CommandUtil.MESSAGE_REPLENISH_SHIFT_SUCCESS;
import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;

import java.util.List;

import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import io.xpire.model.state.ModifiedState;
import io.xpire.model.state.StateManager;

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

    private final Index targetIndex;
    private String result;

    public ShiftToReplenishCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.result = "";
    }

    @Override
    public CommandResult execute(Model model, StateManager stateManager) throws CommandException {
        requireAllNonNull(model, stateManager);
        this.requireNonEmptyCurrentList(model);
        List<? extends Item> lastShownList = model.getCurrentList();
        if (this.targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }
        XpireItem targetItem = (XpireItem) lastShownList.get(this.targetIndex.getZeroBased());
        shiftItemToReplenishList(stateManager, model, targetItem);
        this.result = String.format(MESSAGE_REPLENISH_SHIFT_SUCCESS, targetItem.getName());
        setShowInHistory(true);
        return new CommandResult(this.result);
    }

    /**
     * Shifts an item to the replenish list.
     */
    private void shiftItemToReplenishList(StateManager stateManager, Model model, XpireItem itemToShift)
            throws CommandException {
        Item remodelledItem = itemToShift.remodel();
        if (model.hasItem(REPLENISH, remodelledItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM_REPLENISH);
        }
        stateManager.saveState(new ModifiedState(model));
        model.addItem(REPLENISH, remodelledItem);
        model.deleteItem(XPIRE, itemToShift);
    }

    @Override
    public String toString() {
        return "Shift command";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof ShiftToReplenishCommand)) {
            return false;
        } else {
            ShiftToReplenishCommand other = (ShiftToReplenishCommand) obj;
            return this.targetIndex.equals(other.targetIndex);
        }
    }

    @Override
    public int hashCode() {
        return this.targetIndex.hashCode();
    }

}
