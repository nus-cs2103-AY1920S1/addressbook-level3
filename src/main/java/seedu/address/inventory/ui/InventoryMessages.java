package seedu.address.inventory.ui;

/**
 * Formats the Ui return responses for different commands to be shown to user.
 */
public class InventoryMessages {
    public static final String MESSAGE_BEGINNING_FOR_INVALID_COMMAND = "Sorry! Please input the correct "
            + "command format:\n";

    public static final String MESSAGE_NO_SUCH_SORT_COMMAND =
            "The items can be sorted by category, quantity and description.\n"
                    + "Please input sort category/quantity/description";

    public static final String MESSAGE_RESET_TO_ORIGINAL_ORDER = "The items are now reset to original order of input "
            + "when treasurerPro was just open.";

    public static final String MESSAGE_SORTED_BY_DESCRIPTION = "The items are now sorted by description.";

    public static final String MESSAGE_SORTED_BY_CATEGORY = "The items are now sorted by category.";

    public static final String MESSAGE_SORTED_BY_QUANTITY = "The items are now sorted by quantity.";

    public static final String MESSAGE_INVALID_ADD_COMMAND_FORMAT = MESSAGE_BEGINNING_FOR_INVALID_COMMAND
            + "add: Adds an item to the table."
            + "Parameters:\nd/DESCRIPTION\nc/CATEGORY\nq/QUANTITY\nco/COST\n[p/PRICE]";

    public static final String MESSAGE_NOT_A_NUMBER = "Sorry! Please input a positive number less than 10,000.";

    public static final String NO_SUCH_ITEM_INVENTORY =
            "Sorry! There is no such item in our database. Do add the item in.";

    public static final String MESSAGE_INVALID_EDIT_COMMAND_FORMAT = MESSAGE_BEGINNING_FOR_INVALID_COMMAND
            + "edit: Edits an item in the table."
            + "Parameters:\nID\n[d/DESCRIPTION]\n[c/CATEGORY]\n[q/QUANTITY]\n[co/COST]\n[p/PRICE]";

    public static final String MESSAGE_NOT_EDITED = "The item was not edited. Please follow the correct"
            + " format and ensure that the values correspond to the input type (E.g. quantity must be a number)."
            + "\nThe format is:\nedit ID [d/DESCRIPTION] [c/CATEGORY] [q/QUANTITY] [co/COST] [p/PRICE].";

    public static final String NO_SUCH_INDEX_INVENTORY = "There is no item at the inputted index.\nPlease make sure "
            + "the index is within the table.";

    public static final String MESSAGE_EDITED_ITEM = "Edited item from:\n%s\nto:%s";

    public static final String MESSAGE_DUPLICATE = "The given input is the same as that of item specified.";

    public static final String MESSAGE_ADDED_ITEM = "Added item:\n%s";

    public static final String MESSAGE_DELETED_ITEM = "Deleted item:\n%s";

    public static final String MESSAGE_NO_COMMAND = "Please input a valid command. The commands include "
            + "add, delete, edit, sort, go and exit.";

    public static final String MESSAGE_ADDED_DUPLICATE_ITEM = "The given description is the same as that"
            + " of an existing item. We have added the quantity to the existing item.";

    public static final String MESSAGE_NEGATIVE_NUMBER = "The given input cannot be negative.";

    public static final String MESSAGE_ON_CASHIER_MODE = "Sorry! You cannot modify any existing items while on "
            + "cashier mode. \nTo turn off the cashier mode, please clear all items on Cashier Tab by "
            + "inputting \"clear\" on the Cashier Tab";

    public static final String MESSAGE_NUMBER_TOO_LARGE = "Sorry, please input a number less than 10,000.";

    public static final String MESSAGE_TOTAL_TOO_LARGE = "Sorry, the total cost/price is too large."
            + " It must be less than 10,000.";

    public static final String MESSAGE_QUANTITY_CANNOT_BE_ZERO = "Sorry! Quantity cannot be zero.";
}
