package seedu.address.model.help;

/**
 * Contains links to our User Guide for all the commands in Contact
 */

public class WebLinks {

    /**
     * Matches the command input of the user to the correct web link.
     *
     * @param secondaryCommand A valid command in Contact.
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

        case "add_person":
            return base + "#adding-a-contact-code-add_person-code";

        case "edit_person":
            return base + "#editing-a-contact-code-edit_person-code";

        case "add_claim":
            return base + "#adding-a-claim-code-add_claim-code";

        case "edit_claim":
            return base + "#editing-a-claim-code-edit_claim-code";

        case "add_income":
            return base + "#adding-an-income";

        case "edit_income":
            return base + "#editing-an-income";

        case "delete":
            return base + "#deleting-a-contact";

        case "find":
            return base + "#filter";

        case "sort":
            return base + "#sorting-claims-list-based-on-dates";

        default: return base;
        }
    }
}
