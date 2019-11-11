package seedu.address.model.inventory.exceptions;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Exception for when the Inventory is unable to be removed.
 */
public class InventoryNotRemovableException extends CommandException {

    public static final String MESSAGE = "Failed to delete your inventory item, "
            + "the expenditure you are trying to remove is associated with an event, please go to"
            + " the corresponding event to delete the item";

    public InventoryNotRemovableException() {
        super(MESSAGE);
    }
}
