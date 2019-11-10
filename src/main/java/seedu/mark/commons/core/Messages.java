package seedu.mark.commons.core;

import seedu.mark.model.bookmark.Folder;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX = "The bookmark index provided is invalid";
    public static final String MESSAGE_BOOKMARKS_LISTED_OVERVIEW = "%1$d bookmarks listed!";
    public static final String MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX = "The reminder index provided is invalid";
    public static final String MESSAGE_FOLDER_NOT_FOUND = "Folder %1$s does not exist";
    public static final String MESSAGE_READDING_ROOT_FOLDER = "The " + Folder.DEFAULT_FOLDER_NAME
            + " folder is the root folder for all folders. Please choose another name";
}
