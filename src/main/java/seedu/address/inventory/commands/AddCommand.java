package seedu.address.inventory.commands;

import static seedu.address.inventory.ui.InventoryMessages.MESSAGE_ADDED_ITEM;

import java.util.logging.Logger;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.ModelManager;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.person.commons.core.LogsCenter;

/**
 * Adds a transaction to the transaction list.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    private Item item;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public AddCommand(Item item) {
        this.item = item;
    }

    @Override
    public CommandResult execute(ModelManager model) {
        InventoryMessages inventoryMessages = new InventoryMessages();
        model.addItem(item);
        logger.info(item.toString());
        return new CommandResult(String.format(MESSAGE_ADDED_ITEM, item));
    }
}
