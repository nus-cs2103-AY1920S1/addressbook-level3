package io.xpire.logic.commands;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;
import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;
import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import io.xpire.model.item.exceptions.ItemNotFoundException;
import io.xpire.model.state.ModifiedState;
import io.xpire.model.state.StateManager;
import io.xpire.model.tag.Tag;

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
    public static final String MESSAGE_SUCCESS = "%s is moved to the Replenish List";
    private static final Tag EXPIRED_TAG = new Tag("Expired");

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
            throw new CommandException(MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        XpireItem targetItem = (XpireItem) lastShownList.get(this.targetIndex.getZeroBased());
        Item remodelledItem = targetItem.remodel();
        Set<Tag> tags = new HashSet<>(remodelledItem.getTags());
        tags.remove(EXPIRED_TAG);
        remodelledItem.setTags(tags);
        if (model.hasItem(REPLENISH, remodelledItem)) {
            try {
                stateManager.saveState(new ModifiedState(model));
                model.deleteItem(XPIRE, targetItem);
            } catch (ItemNotFoundException e) {
                throw new CommandException(MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
            }
        } else {
            stateManager.saveState(new ModifiedState(model));
            model.addItem(REPLENISH, remodelledItem);
            model.deleteItem(XPIRE, targetItem);
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
