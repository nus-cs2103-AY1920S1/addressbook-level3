package seedu.address.cashier.ui;

import seedu.address.inventory.model.Item;
import seedu.address.person.model.person.Person;

/**
 * Formats the Ui return responses for different commands to be shown to user.
 */
public class CashierMessages {

    public static final String AMOUNT_NOT_A_NUMBER = "Please input a number for the amount being paid.";

    public static final String CLEARED_SUCCESSFULLY = "Cleared successfully!";

    public static final String NO_CASHIER = "Sorry! Please enter the cashier's name before proceeding:\n "
            + " cashier NAME\n";

    public static final String NO_SUCH_COMMAND = "Sorry! There is no such command.";

    public static final String NO_SUCH_INDEX_CASHIER = "There is no item at the inputted index.";

    public static final String NO_SUCH_ITEM_CASHIER = "There is no such item available. Please input a valid item.";

    public static final String NO_SUCH_PERSON = "Sorry! There is no such person. Please enter a valid name.";

    public static final String MESSAGE_INVALID_ADDCOMMAND_FORMAT = "Sorry! Please type add with parameters:\n"
            + " d/DESCRIPTION\n q/QUANTITY";

    public static final String MESSAGE_INVALID_CASHIERCOMMAND_FORMAT = "Sorry! Please type " + " \"cashier NAME \" \n";

    public static final String MESSAGE_INVALID_EDITCOMMAND_FORMAT = "Sorry! Please type edit with parameters:\n"
            + " i/INDEX\n q/QUANTITY";

    public static final String INDEX_NOT_A_NUMBER = "Please input the row index of the item to be deleted "
            + "according to the table.";

    public static final String QUANTITY_NOT_A_NUMBER = "Please input an integer for the quantity of item to be sold.";

    public static final String addCashierSuccessful(Person cashier) {
        return "Added cashier successfully. Cashier-in-charge is " + cashier.getName() + ". \n";
    }

    public static final String checkoutSuccessful(String totalAmount, String change) {
        return "Total amount is " + totalAmount + ". \n" + "The change is " + change + ". \nCheckout successful.";
    }

    public static final String editSuccessfully(Item i) {
        return i.getDescription() + ": " + i.getQuantity() + "\nEdited successfully.";
    }

    /**
     * Returns a message that the amount input is insufficient.
     *
     * @param totalAmount the total amount of items in the Sales List
     * @return the message
     */
    public static final String insufficientAmount(double totalAmount) {
        return " The total price is " + totalAmount + " .The amount is insufficient. \n "
                + "Please input an amount of at least " + totalAmount + " .";
    }

    public static final String insufficientStock(String qty, String description) {
        return "There is insufficient stock. Only " + qty + " " + description + " left. Please input a valid quantity.";
    }

    public static String addedItem(Item item) {
        return "Added Item:\n" + item;
    }

    public static String deletedItem(Item item) {
        return "Deleted Item:\n" + item;
    }

}
