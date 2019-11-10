package seedu.address.cashier.logic.commands;

import static seedu.address.cashier.ui.CashierMessages.MESSAGE_DELETED_ITEM;

import java.util.logging.Logger;

import seedu.address.cashier.logic.parser.exception.ParseException;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.model.Item;
import seedu.address.person.commons.core.LogsCenter;

/**
 * Deletes an item identified using its displayed index from the sales list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    private final int index;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates a DeleteCommand to delete an item
     * @param index of the item in the sales list to be deleted
     */
    public DeleteCommand(int index) {
        assert index > 0 : "Index must be a positive integer.";
        logger.info("Delete index: " + index);
        this.index = index;
    }

    @Override
    public CommandResult execute(seedu.address.cashier.model.Model modelManager,
                                 seedu.address.person.model.CheckAndGetPersonByNameModel personModel)
            throws NoSuchIndexException, ParseException {
        Item item;
        try {
            item = modelManager.findItemByIndex(index);
            modelManager.deleteItem(index);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchIndexException(CashierMessages.NO_SUCH_INDEX_CASHIER);
        }
        logger.info("Deleted Item: " + item.toString());
        return new CommandResult(String.format(MESSAGE_DELETED_ITEM, item.getDescription()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && index == (((DeleteCommand) other).index));
    }

}
