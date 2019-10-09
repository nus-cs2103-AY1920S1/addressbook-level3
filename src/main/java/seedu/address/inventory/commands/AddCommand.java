package seedu.address.inventory.commands;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.inventory.model.Model;

import java.util.logging.Logger;

/**
 * Adds a transaction to the transaction list.
 */
public class AddCommand extends Command {
    private Item item;
    public static final String COMMAND_WORD = "add";
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public AddCommand(Item item) {
        this.item = item;
    }

    @Override
    public CommandResult execute(Model model) {
        InventoryMessages inventoryMessages = new InventoryMessages();
        model.addItem(item);
        logger.info(item.toString());
        return new CommandResult(inventoryMessages.addedItem(item));
    }
}
