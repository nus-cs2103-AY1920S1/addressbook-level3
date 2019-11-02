package seedu.elisa.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.elisa.commons.core.Messages;
import seedu.elisa.commons.core.index.Index;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.AutoRescheduleManager;
import seedu.elisa.model.ItemIndexWrapper;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.RescheduleTask;
import seedu.elisa.model.item.VisualizeList;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Finally! Deleted Item: %1$s";

    private final Index targetIndex;
    private ItemIndexWrapper deleted;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        requireNonNull(model);
        VisualizeList lastShownList = model.getVisualList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        deleted = model.getIndices(targetIndex.getZeroBased());
        Item itemDeleted = model.deleteItem(targetIndex.getZeroBased());

        if (itemDeleted.hasAutoReschedule()) { // also ensures that itemDeleted has an Event.
            RescheduleTask.removeFromAllTasks(itemDeleted.getEvent().get());
        }

        if (!isExecuted()) {
            model.getElisaCommandHistory().clearRedo();
            setExecuted(true);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, itemDeleted));
    }

    @Override
    public void reverse(ItemModel model) throws CommandException {
        model.addItem(deleted);

        // if deleted item is autoReschedulable, add item back to thread with an updated DateTime.
        if (deleted.getItem().hasAutoReschedule()) {
            Item item = deleted.getItem();
            AutoRescheduleManager.updateEvent(item, model);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
