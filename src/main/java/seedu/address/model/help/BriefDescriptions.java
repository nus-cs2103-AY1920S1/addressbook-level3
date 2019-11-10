package seedu.address.model.help;

import seedu.address.logic.commands.AddClaimCommand;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddIncomeCommand;
import seedu.address.logic.commands.ApproveClaimCommand;
import seedu.address.logic.commands.BudgetCommand;
import seedu.address.logic.commands.CheckCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.commands.DeleteIncomeCommand;
import seedu.address.logic.commands.DeleteShortcutCommand;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.EditIncomeCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GotoCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RejectClaimCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortReverseCommand;

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

        case "delete_contact":
            return DeleteContactCommand.MESSAGE_USAGE;

        //case "delete_claim":
            //return DeleteClaimCommand.MESSAGE_USAGE;

        case "delete_income":
            return DeleteIncomeCommand.MESSAGE_USAGE;

        case "edit_contact":
            return EditContactCommand.MESSAGE_USAGE;

        case "edit_income":
            return EditIncomeCommand.MESSAGE_USAGE;

        case "find":
            return FindCommand.MESSAGE_USAGE;

        case "goto":
            return GotoCommand.MESSAGE_USAGE;

        case "check":
            return CheckCommand.MESSAGE_USAGE;

        case "approve":
            return ApproveClaimCommand.MESSAGE_USAGE;

        case "budget":
            return BudgetCommand.MESSAGE_USAGE;

        case "clear":
            return ClearCommand.MESSAGE_USAGE;

        case "reject":
            return RejectClaimCommand.MESSAGE_USAGE;

        case "exit":
            return ExitCommand.MESSAGE_USAGE;

        case "sort":
            return SortCommand.MESSAGE_USAGE;

        case "reverse":
            return SortReverseCommand.MESSAGE_USAGE;

        case "delete_shortcut":
            return DeleteShortcutCommand.MESSAGE_USAGE;

        default: return "Command should be self-explanatory with no additional parameters.\n"
                + "Use 'type/guide' if you really want to know the exact details "
                + "or you can just try it out :)";
        }
    }
}
