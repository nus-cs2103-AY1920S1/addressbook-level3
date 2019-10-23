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
 * Launches a link in the module in the system's default browser
 */
public class LaunchLinkCommand extends LinkCommand {
    private final Index targetIndex;
    private final String linkTitle;

    public LaunchLinkCommand(Index targetIndex, String linkTitle) {
        this.targetIndex = targetIndex;
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
        throw new CommandException("Link with matching title not found");
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<TrackedModule> lastShownList = model.getFilteredModuleList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }
        TrackedModule moduleToAccess = lastShownList.get(targetIndex.getZeroBased());
        Link target = find(moduleToAccess, linkTitle);
        target.launch();
        return new CommandResult((MESSAGE_LAUNCH_SUCCESS));
    }

}
