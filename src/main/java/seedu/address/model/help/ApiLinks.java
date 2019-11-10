package seedu.address.model.help;

/**
 * Contains links to our JavaDocs for all the commands in FinSec
 */

public class ApiLinks {

    /**
     * Matches the command input of the user to the correct API link.
     *
     * @param secondaryCommand A valid command in FinSec.
     * @return an API link to the {@code HelpCommand}.
     */

    public static String getLink (SecondaryCommand secondaryCommand) {

        String base = "/javadocs/seedu/address/logic/commands/";

        switch (secondaryCommand.toString()) {

        case "help":
            return base + "HelpCommand.html";

        case "add_claim":
            return base + "AddClaimCommand.html";

        case "add_contact":
            return base + "AddContactCommand.html";

        case "add_income":
            return base + "AddIncomeCommand.html";

        case "delete_contact":
            return base + "DeleteContactCommand.html";

        //case "delete_claim":
        //return base + "DeleteClaimCommand.html";

        case "delete_income":
            return base + "DeleteIncomeCommand.html";

        case "edit_claim":
            return base + "EditClaimCommand.html";

        case "edit_contact":
            return base + "EditContactCommand.html";

        case "edit_income":
            return base + "EditIncomeCommand.html";

        case "find":
            return base + "FindCommand.html";

        case "goto":
            return base + "GotoCommand.html";

        case "check":
            return base + "CheckCommand.html";

        case "approve":
            return base + "ApproveClaimCommand.html";

        case "budget":
            return base + "BudgetCommand.html";

        case "clear":
            return base + "ClearCommand.html";

        case "reject":
            return base + "RejectClaimCommand.html";

        case "exit":
            return base + "ExitCommand.html";

        case "sort":
            return base + "SortCommand.html";

        case "reverse":
            return base + "SortReverseCommand.html";

        case "delete_shortcut":
            return base + "DeleteShortcutCommand.html";

        default: return base + "Command.html";
        }
    }

}
