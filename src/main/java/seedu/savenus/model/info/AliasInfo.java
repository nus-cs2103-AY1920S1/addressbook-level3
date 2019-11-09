package seedu.savenus.model.info;

import seedu.savenus.logic.commands.AliasCommand;

/**
 * Contains information on Alias command.
 */
public class AliasInfo {
    public static final String COMMAND_WORD = AliasCommand.COMMAND_WORD;

    public static final String INFORMATION =
            "Users are able to given an alias word for any given command in $aveNUS.\n"
            + "If no alias word is given, the alias word for the command word will be cleared.";

    public static final String USAGE =
            "alias sort s";

    public static final String OUTPUT =
            "You have successfully set the alias of sort to be s.";
}
