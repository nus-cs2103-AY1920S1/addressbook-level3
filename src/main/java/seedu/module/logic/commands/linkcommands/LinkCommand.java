package seedu.module.logic.commands.linkcommands;

import seedu.module.logic.commands.Command;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.TrackedModule;

/**
 * Adds or launches a Link in a Module depending on input
 */
public abstract class LinkCommand extends Command {
    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds, deletes or launches a Link to a Module.\n"
            + "Parameters: " + "Action " + "n/Link title [l/Link]\n"
            + "Example: " + COMMAND_WORD + " add "
            + "n/example " + "l/ " + "http://google.com\n"
            + COMMAND_WORD + " go " + "t/" + "example\n";

    public static final String MESSAGE_LINK_SUCCESS = "New link added to module.";
    public static final String MESSAGE_LAUNCH_SUCCESS = "Website opened in default browser.";
    public static final String MESSAGE_DUPLICATE_TITLE = "Link name already exists, choose another one.";
    public static final String MESSAGE_NO_DISPLAYED_MODULE_ERROR = "No displayed module to execute command. "
            + "View a module first.";
    public static final String MESSAGE_MODULE_NOT_TRACKED = "Links can only be added to tracked modules. "
            + "Current displayed module has not been tracked.";
    public static final String MESSAGE_DELETE_SUCCESS = "Link deleted successfully.";
    public static final String MESSAGE_EDIT_SUCCESS = "Link edited successfully.";
    public static final String MESSAGE_MARK_SUCCESS = "Link marked successfully.";
    public static final String MESSAGE_LINK_NOT_FOUND = "Link with matching title not found";

    /**
     * Returns the current TrackedModule that is being viewed, if any.
     * @param model
     * @return
     * @throws CommandException
     */
    public TrackedModule currentDisplayed(Model model) throws CommandException {
        if (!model.getDisplayedModule().isPresent()) {
            throw new CommandException(MESSAGE_NO_DISPLAYED_MODULE_ERROR);
        }
        return (TrackedModule) model.getDisplayedModule()
                .filter(m -> m instanceof TrackedModule)
                .orElseThrow(() -> new CommandException(MESSAGE_MODULE_NOT_TRACKED));
    }
}
