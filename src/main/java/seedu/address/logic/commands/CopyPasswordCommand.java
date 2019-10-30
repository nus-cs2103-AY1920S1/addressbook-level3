package seedu.address.logic.commands;

/**
 * Copies password, website, username from password identified.
 */
public abstract class CopyPasswordCommand extends Command {
    public static final String COMMAND_WORD = "copy";
    public static final String COMMAND_WORD1 = "cp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "copy supposed to blah";
}
