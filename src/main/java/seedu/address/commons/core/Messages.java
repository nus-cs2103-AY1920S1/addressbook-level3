package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";
    public static final String MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX = "The FlashCard index provided is invalid";
    public static final String MESSAGE_INVALID_DEADLINE_DISPLAYED_INDEX = "The deadline index provided is invalid";
    public static final String MESSAGE_INVALID_THEME =
            "The theme is not available. Existing themes: dark, light, pink, blue, hacker, nus";
    public static final String MESSAGE_FLASHCARD_LISTED_OVERVIEW = "%1$d  FlashCard(s) listed!";
    public static final String MESSAGE_UNKNOWN_TEST_COMMAND = "Unknown test command!\n%s";
    public static final String MESSAGE_EXPORT_IO_EXCEPTION = "There was an error in writing to the file.\n"
            + "Some directories may be protected - please try a different directory.";
}
