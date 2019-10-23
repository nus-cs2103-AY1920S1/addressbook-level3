package seedu.module.logic.commands.linkcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.module.commons.core.Messages;
import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.Link;
import seedu.module.model.module.TrackedModule;

/**
 * Adds a given Link to the specified TrackedModule
 */
public class AddLinkCommand extends LinkCommand {
    private final Index targetIndex;
    private final Link webLink;

    public AddLinkCommand(Index targetIndex, Link webLink) {
        this.targetIndex = targetIndex;
        this.webLink = webLink;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<TrackedModule> lastShownList = model.getFilteredModuleList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }
        TrackedModule moduleToAddLink = lastShownList.get(targetIndex.getZeroBased());
        if (moduleToAddLink.hasLinkTitle(webLink)) {
            throw new CommandException(MESSAGE_DUPLICATE_TITLE);
        }
        moduleToAddLink.addLink(webLink);
        return new CommandResult(MESSAGE_LINK_SUCCESS);
    }
}
