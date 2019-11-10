package seedu.module.logic.commands.linkcommands;

import static java.util.Objects.requireNonNull;

import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.Link;
import seedu.module.model.module.TrackedModule;

/**
 * Adds a given Link to the specified TrackedModule
 */
public class AddLinkCommand extends LinkCommand {
    private final Link webLink;

    public AddLinkCommand(Link webLink) {
        this.webLink = webLink;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        TrackedModule moduleToAddLink = this.currentDisplayed(model);
        if (moduleToAddLink.hasLinkTitle(webLink)) {
            throw new CommandException(MESSAGE_DUPLICATE_TITLE);
        }
        moduleToAddLink.addLink(webLink);
        return new CommandResult(MESSAGE_LINK_SUCCESS, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit
                || (other instanceof AddLinkCommand)
                && webLink.equals(((AddLinkCommand) other).webLink);
    }
}
