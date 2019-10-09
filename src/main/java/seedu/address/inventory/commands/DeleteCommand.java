package seedu.address.inventory.commands;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.Model;
import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.ui.InventoryMessages;

/**
 * Deletes a transaction to the transaction list.
 */
public class DeleteCommand extends Command {
    private int index;
    public static final String COMMAND_WORD = "delete";

    /**
     * Creates an DeleteCommand to delete the specified {@code Transaction}
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model)
            throws NoSuchIndexException {
        InventoryMessages inventoryMessages = new InventoryMessages();
        Item item = model.findItemByIndex(index);
        model.deleteItem(index);
        return new CommandResult(inventoryMessages.deletedItem(item));
    }
}
