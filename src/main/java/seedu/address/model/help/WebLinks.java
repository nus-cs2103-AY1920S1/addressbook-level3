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
        switch (secondaryCommand.toString()) {

        case "help":
            return "https://ay1920s1-cs2103t-w12-1.github.io/main/UserGuide.html#viewing-help-code-help-code";

        case "exit":
            return "https://ay1920s1-cs2103t-w12-1.github.io/main/UserGuide.html#exiting-the-program-code-exit-code";

        default: return "https://ay1920s1-cs2103t-w12-1.github.io/main/UserGuide.html";
        }
    }
}
