package seedu.address.inventory.logic.commands;

import static seedu.address.inventory.ui.InventoryMessages.MESSAGE_DELETED_ITEM;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.Model;
import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.util.CommandResult;

/**
 * Deletes a transaction to the transaction list.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    private boolean isByIndex;
    private int index;
    private String description;

    /**
     * Creates an DeleteIndexCommand to delete the specified {@code Item} by index.
     */
    public DeleteCommand(int index) {
        this.isByIndex = true;
        this.index = index;
    }

    /**
     * Creates an DeleteIndexCommand to delete the specified {@code Item} by description.
     */
    public DeleteCommand(String description) {
        this.isByIndex = false;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws NoSuchIndexException, NoSuchItemException {
        if (isByIndex) {
            Item item = model.findItemByIndex(index);
            model.deleteItem(index);
            return new CommandResult(String.format(MESSAGE_DELETED_ITEM, item));
        } else {
            int idx = model.findIndexByDescription(description);
            Item item = model.findItemByIndex(idx);
            model.deleteItem(idx);
            return new CommandResult(String.format(MESSAGE_DELETED_ITEM, item));
        }
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && index == ((DeleteCommand) other).index);
    }
}
