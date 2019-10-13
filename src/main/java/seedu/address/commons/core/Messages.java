package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX = "The entity index provided is invalid";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_NO_FLAG =
            "Please flag the command with '-b' or '-w' to indicate bodies or workers";
    public static final String MESSAGE_INVALID_FLAG = "Invalid flag used! Use -b / -w / -f instead";
    public static final String MESSAGE_BODIES_LISTED_OVERVIEW = "%1$d bodies listed!";
    public static final String MESSAGE_WORKERS_LISTED_OVERVIEW = "%1$d workers listed!";
    public static final String MESSAGE_INVALID_ENTITY_DISPLAYED_ID = "The identification number provided is invalid "
            + "for the selected type of entity.";
}
