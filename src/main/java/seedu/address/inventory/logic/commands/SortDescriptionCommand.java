package seedu.address.inventory.logic.commands;

import seedu.address.inventory.model.Model;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.util.CommandResult;

/**
 * Command that sorts the Items by description.
 */
public class SortDescriptionCommand extends SortCommand {
    @Override
    public CommandResult execute(Model model) {
        model.sortByDescription();
        return new CommandResult(InventoryMessages.MESSAGE_SORTED_BY_DESCRIPTION);
    }
}
