package seedu.address.model.help;

import seedu.address.logic.commands.AddClaimCommand;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddIncomeCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditClaimCommand;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.EditIncomeCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;

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

    public static String getDescription (SecondaryCommand secondaryCommand) {
        switch (secondaryCommand.toString()) {

        case "help":
            return HelpCommand.MESSAGE_USAGE;

        case "add_claim":
            return AddClaimCommand.MESSAGE_USAGE;

        case "add_contact":
            return AddContactCommand.MESSAGE_USAGE;

        case "add_income":
            return AddIncomeCommand.MESSAGE_USAGE;

        case "delete":
            return DeleteCommand.MESSAGE_USAGE;

        case "edit_claim":
            return EditClaimCommand.MESSAGE_USAGE;

        case "edit_contact":
            return EditContactCommand.MESSAGE_USAGE;

        case "edit_income":
            return EditIncomeCommand.MESSAGE_USAGE;

        case "find":
            return FindCommand.MESSAGE_USAGE;

        default: return "Command should be self-explanatory with no additional parameters.\n"
                + "Use 'type/guide' if you really want to know the exact details "
                + "or you can just try it out :)";
        }
    }
}
