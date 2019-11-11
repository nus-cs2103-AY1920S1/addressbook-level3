package seedu.ezwatchlist.commons.core.messages;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_SHOW_DISPLAYED_INDEX = "The show index provided is invalid";
    public static final String MESSAGE_SYNC_INVALID_INDEX = "The INDEX only refers to the results found in"
            + " search result page. Please"
            + " ensure the index is valid.";
    public static final String MESSAGE_INVALID_COMMAND = "Can't execute that command in this tab. Please switch over "
            + "to the watchlist or watched tab and enter the command";
}
