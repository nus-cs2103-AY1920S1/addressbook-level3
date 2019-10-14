package seedu.address.inventory.commands;

import seedu.address.inventory.model.Model;
import seedu.address.inventory.ui.InventoryMessages;

/**
 * Command that sorts the Items by category.
 */
public class SortCategoryCommand extends SortCommand {
    @Override
    public CommandResult execute(Model model) throws Exception {
        model.sortByCategory();
        return new CommandResult(InventoryMessages.MESSAGE_SORTED_BY_CATEGORY);
    }
}
