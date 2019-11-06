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
public class MarkLinkCommand extends LinkCommand {
    private final String linkTitle;
    private final boolean mark;

    public MarkLinkCommand(String linkTitle, boolean mark) {
        this.linkTitle = linkTitle;
        this.mark = mark;
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

    /**
     * Marks/unmarks link object. Marked object are pushed to front, unmarked are pushed to the back of list.
     * @param target
     * @param moduleToAccess
     */
    private void markLink(Link target, TrackedModule moduleToAccess) {
        moduleToAccess.getLink().removeIf(module -> module.equals(target));
        if (this.mark) {
            target.setMarked();
            moduleToAccess.getLink().add(0, target);
        } else {
            target.setUnmarked();
            moduleToAccess.getLink().add(target);
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        TrackedModule moduleToAccess = this.currentDisplayed(model);
        Link target = find(moduleToAccess, linkTitle);
        if (target.isMarked() && this.mark) {
            return new CommandResult("Link is already marked");
        } else if (!target.isMarked() && !this.mark) {
            return new CommandResult("Link is already unmarked.");
        }
        markLink(target, moduleToAccess);
        return new CommandResult((MESSAGE_MARK_SUCCESS), false, true, false);
    }

}
