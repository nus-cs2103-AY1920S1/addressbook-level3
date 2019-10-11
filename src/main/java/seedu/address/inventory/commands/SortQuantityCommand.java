package seedu.address.inventory.commands;

import seedu.address.inventory.model.Model;
import seedu.address.inventory.ui.InventoryMessages;

public class SortQuantityCommand extends SortCommand {
    @Override
    public CommandResult execute(Model model) throws Exception {
        model.sortByQuantity();
        return new CommandResult(InventoryMessages.MESSAGE_SORTED_BY_QUANTITY);
    }
}
