package seedu.address.person.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {
    public static final String MESSAGE_NO_COMMAND = "Please input a valid command. The commands include\n"
            + "add, delete, edit, find, list, go and exit.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Sorry! Please input the correct command format:\n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid. Please "
            + "make sure it is within the list.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
}
