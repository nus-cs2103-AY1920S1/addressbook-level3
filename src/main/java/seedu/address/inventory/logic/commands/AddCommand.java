package seedu.address.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.inventory.ui.InventoryMessages.MESSAGE_ADDED_ITEM;

import java.util.logging.Logger;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.Model;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.util.CommandResult;

/**
 * Adds a transaction to the transaction list.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    private Item item;
    private boolean isDuplicateItem;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public AddCommand(Item item, boolean isDuplicateItem) {
        requireNonNull(item);
        this.item = item;
        this.isDuplicateItem = isDuplicateItem;
    }

    @Override
    public CommandResult execute(Model model) {
        if (isDuplicateItem) {
            return new CommandResult(String.format(InventoryMessages.MESSAGE_ADDED_DUPLICATE_ITEM, item));
        } else {
            model.addItem(item);
            logger.info(item.toString());
            return new CommandResult(String.format(MESSAGE_ADDED_ITEM, item));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && item.equals(((AddCommand) other).item));
    }
}
