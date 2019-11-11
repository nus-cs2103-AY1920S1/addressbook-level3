package seedu.module.logic.commands.linkcommands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.Link;
import seedu.module.model.module.TrackedModule;

/**
 * Edits a Link with matching title from TrackedModule.
 */
public class EditLinkCommand extends LinkCommand {
    public static final String MESSAGE_NO_EDIT_INFO = "No edit information given.";
    private final String linkTitle;
    private final Optional<String> newTitle;
    private final Optional<String> newLink;

    public EditLinkCommand(String linkTitle, Optional<String> newTitle, Optional<String> newLink) {
        this.linkTitle = linkTitle;
        this.newTitle = newTitle;
        this.newLink = newLink;
    }

    /**
     * Returns index of Link object with matching title from the TrackedModule
     * @param targetModule
     * @param linkTitle
     * @return
     * @throws CommandException
     */
    private int findIndex(TrackedModule targetModule, String linkTitle) throws CommandException {
        int limit = targetModule.getLink().size();
        for (int i = 0; i < limit; i++) {
            if (targetModule.getLink().get(i).name.equals(linkTitle)) {
                return i;
            }
        }
        throw new CommandException(MESSAGE_LINK_NOT_FOUND);
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        TrackedModule moduleToAccess = this.currentDisplayed(model);
        if (!this.newLink.isPresent() && !this.newTitle.isPresent()) {
            throw new CommandException(MESSAGE_NO_EDIT_INFO);
        }

        int editIndex = this.findIndex(moduleToAccess, this.linkTitle);
        String title = this.newTitle.isPresent() ? this.newTitle.get() : moduleToAccess.getLink().get(editIndex).name;
        String link = this.newLink.isPresent() ? this.newLink.get() : moduleToAccess.getLink().get(editIndex).url;
        Link editedLink = new Link(title, link);
        if (this.newTitle.isPresent() && moduleToAccess.hasLinkTitle(editedLink)) {
            throw new CommandException(MESSAGE_DUPLICATE_TITLE);
        }
        moduleToAccess.getLink().set(editIndex, editedLink);
        return new CommandResult((MESSAGE_EDIT_SUCCESS), false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit
                || (other instanceof EditLinkCommand)
                && linkTitle.equals(((EditLinkCommand) other).linkTitle)
                && newTitle.equals(((EditLinkCommand) other).newTitle)
                && newLink.equals(((EditLinkCommand) other).newLink);
    }
}
