package seedu.address.cashier.ui;

import java.util.logging.Logger;

import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.Item;
import seedu.address.person.commons.core.LogsCenter;

public class CashierUi {

    private static final Logger logger = LogsCenter.getLogger(CashierUi.class);

    public static final String NO_SUCH_COMMAND =  "Sorry! There is no such command.";

    public static final String MESSAGE_INVALID_ADDCOMMAND_FORMAT = "Sorry! Please type add with parameters:\n" +
            " d/DESCRIPTION\n q/QUANTITY";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Sorry! Please type edit with the index " +
            "and any parameters to be edited:\n" +
            " d/DESCRIPTION\n q/QUANTITY";

    public static final String NO_SUCH_INDEX_CASHIER = "There is no item at the inputted index.";

    public static final String NO_SUCH_ITEM_CASHIER = "There is no such item available.";

    public static final String NOT_A_NUMBER = "Please input the index of the item in the table.";

    public static String addedItem(Item item) {
        return "Added Item:\n" + item;
    }

    public static String deletedItem(Item item) {
        return "Deleted Item:\n" + item;
    }

    public String printAllItems(InventoryList inventoryList) {
        String msg = "";
        try {
            for (int i = 0; i < inventoryList.size(); i++) {
                msg = msg + inventoryList.getItem(i) + "\n";
            }
        } catch (Exception e) {
            logger.severe("problem here.");
        }
        return msg;
    }
}
