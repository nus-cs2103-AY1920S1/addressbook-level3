package seedu.address.model.help;

/**
 * Contains links to our User Guide for all the commands in FinSec
 */

public class WebLinks {

    /**
     * Matches the command input of the user to the correct web link.
     *
     * @param secondaryCommand A valid command in FinSec.
     * @return a web link to the {@code HelpCommand}.
     */

    public static String getLink (SecondaryCommand secondaryCommand) {

        String base = "https://ay1920s1-cs2103t-w12-1.github.io/main/UserGuide.html";

        switch (secondaryCommand.toString()) {

        case "help":
            return base + "#viewing-help-code-help-code";

        case "exit":
            return base + "#exiting-the-program-code-exit-code";

        case "goto":
            return base + "#changing-views-code-goto-code";

        case "add_contact":
            return base + "#adding-a-person-code-add_contact-code";

        case "edit_contact":
            return base + "#editing-a-contact-code-edit_contact-code";

        case "add_claim":
            return base + "#adding-a-claim-code-add_claim-code";

        case "approve":
            return base + "#approving-a-claim-code-approve-code";

        case "reject":
            return base + "#rejecting-a-claim-code-reject-code";

        case "add_income":
            return base + "#adding-an-income-code-add_income-code";

        case "edit_income":
            return base + "#editing-an-income-code-edit_income-code";

        case "delete_contact":
            return base + "#deleting-a-contact-code-delete_contact-code";

        case "delete_income":
            return base + "#deleting-an-income-code-delete_contact-code";

        case "sort":
            return base + "#sorting-the-contacts-claims-incomes-list-by-contact-s-name";

        case "reverse":
        return base + "#sorting-the-contacts-claims-incomes-list-in-reverse-order";

        case "check":
            return base + "#checking-a-person-or-claim-code-check-code";

        case "budget":
            return base + "#viewing-budget-code-budget-code";

        case "clear":
            return base + "#clearing-all-data-code-clear-code";

        case "delete_shortcut":
            return base + "#deleting-a-shortcut-code-delete_shortcut-code";

        default: return base;
        }
    }
}
