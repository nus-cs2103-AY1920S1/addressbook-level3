package seedu.address.inventory.logic.commands;

import seedu.address.inventory.model.Model;
import seedu.address.inventory.ui.InventoryMessages;

/**
 * Command that sorts the Items by the order they were in when the app was opened.
 */
public class SortResetCommand extends SortCommand {
    @Override
    public CommandResult execute(Model model) {
        model.sortReset();
        return new CommandResult(InventoryMessages.MESSAGE_RESET_TO_ORIGINAL_ORDER);
    }
}
