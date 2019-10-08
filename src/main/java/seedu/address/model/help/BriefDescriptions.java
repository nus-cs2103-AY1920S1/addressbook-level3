package seedu.address.model.help;

import seedu.address.logic.commands.*;

/**
 * Contains a brief descriptions of all the commands in FinSec
 */

public class BriefDescriptions {

    /**
     * Matches the command input of the user to the correct description.
     *
     * @param secondaryCommand A valid command in FinSec.
     * @return a brief description to the {@code HelpCommand}.
     */

    public static String getDescription (SecondaryCommand secondaryCommand){
        switch (secondaryCommand.toString()){

        case "help":
            return HelpCommand.MESSAGE_USAGE;

        case "add":
            return AddCommand.MESSAGE_USAGE;

        case "delete":
            return DeleteCommand.MESSAGE_USAGE;

        case "edit":
            return EditCommand.MESSAGE_USAGE;

        case "find":
            return FindCommand.MESSAGE_USAGE;

        default: return "Unknown Command";
        }
    }
}
