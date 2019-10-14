package seedu.address.inventory.commands;

import seedu.address.inventory.model.Model;
import seedu.address.inventory.ui.InventoryMessages;

/**
 * Command that sorts the Items by description.
 */
public class SortDescriptionCommand extends SortCommand {
    @Override
    public CommandResult execute(Model model) throws Exception {
        model.sortByDescription();
        return new CommandResult(InventoryMessages.MESSAGE_SORTED_BY_DESCRIPTION);
    }
}
