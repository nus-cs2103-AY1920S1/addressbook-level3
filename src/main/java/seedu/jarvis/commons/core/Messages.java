package seedu.jarvis.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Jarvis does not recognise this command!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    public static final String MESSAGE_INVALID_PURCHASE_DISPLAYED_INDEX = "Jarvis does not have a purchase with this "
            + "index! Please input another valid index from the list on the left under the Finance tab.";
    public static final String MESSAGE_INVALID_INSTALLMENT_DISPLAYED_INDEX = "Jarvis does not have an installment "
            + "with this index! Please input another valid index from the list on the right under the Finance tab.";
    public static final String MESSAGE_PURCHASES_LISTED_OVERVIEW = "Here you go, Jarvis has found %1$d purchases with "
            + "that keyword! \nIf you wish to return your list of purchases, type 'list-finances' in the command box.";
    public static final String MESSAGE_NO_PURCHASES_FOUND = "Sorry, Jarvis did not find any purchases with that "
            + "keyword. \nIf you wish to return your list of purchases, type 'list-finances' in the command box.";

    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index is not in the range of "
                                                                       + "task items in the list";

    public static final String MESSAGE_INVALID_CCA_DISPLAYED_INDEX = "The cca index provided is invalid";
    public static final String MESSAGE_CCAS_LISTED_OVERVIEW = "%1$d Ccas listed!";

    public static final String MESSAGE_INVALID_COURSE_DISPLAYED_INDEX = "The course index provided is invalid";
}
