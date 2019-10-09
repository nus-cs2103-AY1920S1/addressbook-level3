package seedu.address.inventory.ui;

import seedu.address.inventory.model.Item;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.ui.Inventory;

import java.util.logging.Logger;

public class InventoryMessages {
    public static final String NO_SUCH_SORT_COMMAND = "The items can be sorted by category, quantity and name.\n" +
            "Please input sort by category/quantity/name";

    public static final String RESET_TO_ORIGINAL_ORDER = "The items are now reset to original order of input " +
            "when treasurerPro was just open.";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Sorry! Please type find with your keywords\n " +
            "(Eg. find poster printing)";

    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "Here are the results of items that match" +
            " any of the keywords.";

    private static final Logger logger = LogsCenter.getLogger(InventoryMessages.class);

    public static final String SORTED_BY_NAME = "The items are now sorted by name!";

    public static final String SORTED_BY_QUANTITY = "The transactions are now sorted by quantity!";

    public static final String SORTED_BY_CATEGORY = "The transactions are now sorted by the category!";

    public static final String NO_SUCH_ITEM_INVENTORY = "Sorry! There is no such item in our database. Do add the item in.";

    public static final String NO_SUCH_COMMAND = "Sorry! There is no such command.";

    public static final String MESSAGE_INVALID_ADD_COMMAND_FORMAT = "Sorry! Please type add with parameters:\n" +
            " dt/DATE (eg.21-Sep-2019, 24-Aug-2019 etc)\n d/DESCRIPTION\n c/CATEGORY\n a/AMOUNT\n p/PERSON";

    public static final String MESSAGE_INVALID_EDIT_COMMAND_FORMAT = "Sorry! Please type edit with the index " +
            "and any parameters to be edited:\n" +
            " dt/DATE\n d/DESCRIPTION\n c/CATEGORY\n a/AMOUNT\n p/PERSON";

    public static final String MESSAGE_NOT_EDITED = "Sorry! Did not manage to edit item!";

    public static final String NO_SUCH_INDEX_INVENTORY = "There is no item at the inputted index.";

    public static final String NOT_A_NUMBER = "Please input the numerical index of the item.";

    public static String editedItem(Item itemBefore, Item itemAfter) {
        return "Edited Item from:\n" + itemBefore + "\nto :" + itemAfter;
    }

    public String addedItem(Item item) {
        return "Added Item:\n" + item;
    }

    public String deletedItem(Item item) {
        return "Deleted Item:\n" + item;
    }
}
