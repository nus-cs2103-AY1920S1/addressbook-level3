package seedu.mark.logic.commands;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import seedu.mark.commons.core.Messages;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.predicates.BookmarkContainsKeywordsPredicate;
import seedu.mark.storage.Storage;

/**
 * Finds and lists all bookmarks in Mark whose identifiers contain any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds bookmarks that contain any of the given "
            + "keywords in their name or URL, or that are under the given folder(s) or tagged with the given tag(s).\n"
            + "Parameters: [KEYWORD]... [t/TAG]... [f/FOLDER]...\n"
            + "Example: " + COMMAND_WORD + " stack-overflow github t/Favorite f/CS2103T";

    private final BookmarkContainsKeywordsPredicate predicate;

    public FindCommand(BookmarkContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) {
        requireAllNonNull(model, storage);

        model.updateFilteredBookmarkList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_BOOKMARKS_LISTED_OVERVIEW, model.getFilteredBookmarkList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
