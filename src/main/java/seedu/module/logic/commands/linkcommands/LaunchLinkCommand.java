package seedu.module.logic.commands.linkcommands;

import static java.util.Objects.requireNonNull;

import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.Link;
import seedu.module.model.module.TrackedModule;

/**
 * Launches a link in the module in the system's default browser
 */
public class LaunchLinkCommand extends LinkCommand {
    private final String linkTitle;

    public LaunchLinkCommand(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    /**
     * Returns Link object with matching title from the TrackedModule
     * @param targetModule
     * @param linkTitle
     * @return
     * @throws CommandException
     */
    private Link find(TrackedModule targetModule, String linkTitle) throws CommandException {
        for (Link link: targetModule.getLink()) {
            if (link.name.equals(linkTitle)) {
                return link;
            }
        }
        throw new CommandException(MESSAGE_LINK_NOT_FOUND);
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        TrackedModule moduleToAccess = this.currentDisplayed(model);
        Link target = find(moduleToAccess, linkTitle);
        target.launch();
        return new CommandResult((MESSAGE_LAUNCH_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit
                || (other instanceof LaunchLinkCommand)
                && linkTitle.equals(((LaunchLinkCommand) other).linkTitle);
    }

}
