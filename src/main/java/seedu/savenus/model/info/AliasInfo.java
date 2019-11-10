package seedu.savenus.model.info;

import seedu.savenus.logic.commands.AliasCommand;

//@@author robytanama
/**
 * Contains information on Alias command.
 */
public class AliasInfo {
    public static final String COMMAND_WORD = AliasCommand.COMMAND_WORD;

    public static final String INFORMATION = "Alias command allows you to change the command word to your liking.\n\n"
            + "The changed command will depend on the following factors:\n"
            + "The new command word\n"
            + "The command word to be changed\n\n";

    public static final String USAGE =
            "alias sort s";

    public static final String OUTPUT = "You can now call Sort command just by simply typing s";
}
