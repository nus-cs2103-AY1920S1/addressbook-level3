package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.mark.model.BookmarkManager;
import seedu.mark.model.Model;

/**
 * Clears the bookmark manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Bookmark manager has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setBookmarkManager(new BookmarkManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
