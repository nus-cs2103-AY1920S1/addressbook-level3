package seedu.address.model.help;

import seedu.address.logic.commands.*;

public class BriefDescriptions {

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
