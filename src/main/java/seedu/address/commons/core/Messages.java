package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Oops! Sorry, SugarMummy doesn't understand what this "
            + "command means :(";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Oops! The command you've entered appears to be in "
            + "an invalid format. \n%1$s";
    public static final String MESSAGE_INVALID_SUBARGUMENT_INDEX = "Oops! The index / indices provided in the "
            + "sub-arguments is/are invalid. Index / indices must be integers greater than or equal to 1.";
    public static final String MESSAGE_SUBARGUMENT_INDEX_OUT_OF_BOUNDS = "Oops! The index / indices provided in the "
            + "sub-arguments is/are out of bounds.";
    public static final String MESSAGE_INCONSISTENT_SUBARGUMENT_INDEX = "Oops! the use of index / indices provided in "
            + "the sub-arguments is/are inconsistent. (i.e. some prefixes of the same type have no indices while "
            + "others do)";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_PARAMETER = "Please enter correct input for %2$s! \n%1$s";

}
