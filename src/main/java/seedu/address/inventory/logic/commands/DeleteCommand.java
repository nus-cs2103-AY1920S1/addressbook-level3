package seedu.address.inventory.logic.commands;

import static seedu.address.inventory.ui.InventoryMessages.MESSAGE_DELETED_ITEM;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.Model;
import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.util.CommandResult;

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
    public CommandResult execute(Model model) throws NoSuchIndexException {
        Item item = model.findItemByIndex(index);
        model.deleteItem(index);
        return new CommandResult(String.format(MESSAGE_DELETED_ITEM, item));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && index == ((DeleteCommand) other).index);
    }
}
