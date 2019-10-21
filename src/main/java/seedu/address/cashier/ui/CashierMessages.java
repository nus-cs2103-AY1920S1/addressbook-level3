package seedu.address.cashier.ui;

import java.util.ArrayList;

/**
 * Formats the Ui return responses for different commands to be shown to user.
 */
public class CashierMessages {

    public static final String AMOUNT_NOT_A_NUMBER = "Please input a number for the amount being paid.";

    public static final String CLEARED_SUCCESSFULLY = "Cleared successfully!";

    public static final String NO_CASHIER = "Sorry! Please enter the cashier's name before proceeding:\n "
            + " cashier NAME\n";

    public static final String NO_SUCH_COMMAND = "Sorry! There is no such command.";

    public static final String NO_SUCH_DESCRIPTION_CASHIER = "There is no such item with " +
            "the specified description in the sales list.";

    public static final String NO_SUCH_INDEX_CASHIER = "There is no item at the inputted index.";

    public static final String NO_SUCH_ITEM_FOR_SALE_CASHIER = "Sorry! This item is not available for sale!";

    public static final String NO_SUCH_ITEM_CASHIER = "There is no such item available. Please input a valid item.";

    public static final String NO_SUCH_PERSON = "Sorry! There is no such person. Please enter a valid name.";

    public static final String MESSAGE_INVALID_ADDCOMMAND_FORMAT = "Sorry! Please type \"add "
            + "d/DESCRIPTION\n q/QUANTITY\"";

    public static final String MESSAGE_INVALID_CASHIERCOMMAND_FORMAT = "Sorry! Please type \"cashier NAME\" \n";

    public static final String MESSAGE_INVALID_EDITCOMMAND_FORMAT = "Sorry! Please type \"edit"
            + " i/INDEX q/QUANTITY\" \n OR \n \"edit d/DESCRIPTION\n q/QUANTITY\" \n ";

    public static final String INDEX_NOT_A_NUMBER = "Please input the row index of the item to be deleted "
            + "according to the table.";

    public static final String QUANTITY_NOT_A_NUMBER = "Please input an integer for the quantity of item to be sold.";

    public static final String QUANTITY_NOT_POSITIVE = "Please input an positive integer for the quantity of item to be sold.";

    public static final String MESSAGE_ADD_CASHIER = "Added cashier successfully. Cashier-in-charge is: %s.\n";

    public static final String MESSAGE_CHECKOUT_SUCCESS = "Total amount is %s.\nThe change is %s.\n"
            + "Checkout successful.";

    public static final String MESSAGE_EDIT_SUCCESS = "%s: %s\nEdited successfully.";

    public static final String MESSAGE_INSUFFICIENT_AMOUNT = "The total price is %s. The amount is insufficient.\n"
            + "Please input an amount of at least %s.";

    public static final String MESSAGE_INSUFFICIENT_STOCK = "There is insufficient stock. Only %s %s left."
            + "Please input a valid quantity.";

    public static final String MESSAGE_ADDED_ITEM = "Added item:\n%s";

    public static final String MESSAGE_DELETED_ITEM = "Deleted item:\n%s";

    /**
     * Returns a message containing all the items description according to the category.
     *
     * @param list of the description of items
     * @return a string containing all the items description according to the category
     */
    public static String itemsByCategory(ArrayList<String> list) {
        String output = "The following items are up for sales: \n";
        for (int i = 0; i < list.size(); i++) {
            output += (i + 1) + ". " + list.get(i) + "\n";
        }
        output += "\nTo add to the cart, please type \"add "
                           + "c/CATEGORY d/DESCRIPTION\n q/QUANTITY\"";
        return output;
    }

    /**
     * Returns a message containing a list of recommendations when the input item is invalid.
     * @param list of recommendations
     * @return a list of recommendations
     */
    public static String noSuchItemRecommendation(ArrayList<String> list) {
        String output = NO_SUCH_ITEM_CASHIER + "\nDo you mean: \n";
        for (int i = 0; i < list.size(); i++) {
            output += (i + 1) + ". " + list.get(i) + "\n";
        }
        output += "\nTo add to the cart, please type \"add "
                + "d/DESCRIPTION\n q/QUANTITY\"";
        return output;
    }

}
