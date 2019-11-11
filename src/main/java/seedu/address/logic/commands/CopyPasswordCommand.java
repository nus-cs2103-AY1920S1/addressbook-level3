package seedu.address.logic.commands;

/**
 * Copies password, website, username from password identified.
 */
public abstract class CopyPasswordCommand extends Command {
    public static final String COMMAND_WORD = "copy";
    public static final String COMMAND_WORD1 = "cp";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD1
            + " [" + CopyPasswordValueCommand.COMMAND_WORD + "/" + CopyPasswordValueCommand.COMMAND_WORD1
            + "] / [" + CopyWebsiteCommand.COMMAND_WORD + "/" + CopyWebsiteCommand.COMMAND_WORD1
            + "] / [" + CopyUsernameCommand.COMMAND_WORD + "/" + CopyUsernameCommand.COMMAND_WORD1
            + "]" + " : Opens and copies password/website/username identified by "
            + "the index number used in the display list. \n"
            + "Parameters: INDEX (must be positive integer)\n"
            + "Example: " + CopyPasswordCommand.COMMAND_WORD1 + " "
            + CopyPasswordValueCommand.COMMAND_WORD1 + " 1";
}
