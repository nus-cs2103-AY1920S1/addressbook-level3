package seedu.address.inventory.commands;

import static seedu.address.inventory.ui.InventoryMessages.MESSAGE_DELETED_ITEM;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.ModelManager;
import seedu.address.inventory.model.exception.NoSuchIndexException;

/**
 * Deletes a transaction to the transaction list.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    private int index;

    /**
     * Creates an DeleteIndexCommand to delete the specified {@code Transaction}
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(ModelManager model)
            throws NoSuchIndexException {
        Item item = model.findItemByIndex(index);
        model.deleteItem(index);
        return new CommandResult(String.format(MESSAGE_DELETED_ITEM, item));
    }
}
