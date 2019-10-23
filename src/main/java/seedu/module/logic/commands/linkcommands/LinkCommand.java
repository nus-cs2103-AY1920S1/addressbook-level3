package seedu.module.logic.commands.linkcommands;

import seedu.module.logic.commands.Command;

/**
 * Adds or launches a Link in a Module depending on input
 */
public abstract class LinkCommand extends Command {
    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds or launches a Link to a Module. "
            + "Parameters: " + "Index"
            + "Action" + "t/Link title [l/Link]\n"
            + "Example: " + COMMAND_WORD + "1 " + "a/add "
            + "n/example " + "l/ " + "http://google.com\n"
            + COMMAND_WORD + "1 " + "a/go " + "t/" + "example\n";

    public static final String MESSAGE_LINK_SUCCESS = "New link added to module.";
    public static final String MESSAGE_LAUNCH_SUCCESS = "Website opened in default browser.";
    public static final String MESSAGE_DUPLICATE_TITLE = "Link name already exists, choose another one.";


}
