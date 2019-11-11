package seedu.address.inventory.logic.commands;

import seedu.address.inventory.model.Model;
import seedu.address.inventory.ui.InventoryMessages;

/**
 * Command that sorts the Items by quantity.
 */
public class SortQuantityCommand extends SortCommand {
    @Override
    public CommandResult execute(Model model) {
        model.sortByQuantity();
        return new CommandResult(InventoryMessages.MESSAGE_SORTED_BY_QUANTITY);
    }
}
