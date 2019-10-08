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

        String base = "docs/javadocs/seedu/address/logic/commands/";

        switch (secondaryCommand.toString()) {

        case "help":
            return base + "HelpCommand.html";

        case "add":
            return base + "AddCommand.html";

        default: return base + "Command.html";
        }
    }

}
