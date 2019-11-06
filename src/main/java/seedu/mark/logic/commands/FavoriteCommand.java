package seedu.mark.logic.commands;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.tag.Tag;
import seedu.mark.storage.Storage;

/**
 * Favorites a bookmark identified using its displayed index from Mark.
 */
public class FavoriteCommand extends Command {
    public static final String COMMAND_WORD = "favorite";
    public static final String COMMAND_ALIAS = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a 'Favorite' tag to the bookmark identified by the index number used in the displayed "
            + "bookmark list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAVORITE_BOOKMARK_SUCCESS = "Bookmark added to Favorites: %1$s";
    public static final String MESSAGE_FAVORITE_BOOKMARK_DUPLICATE = "This bookmark already exists in Favorites";

    private final Index targetIndex;

    public FavoriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        List<Bookmark> lastShownList = model.getFilteredBookmarkList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
        }

        Bookmark bookmarkToFavorite = lastShownList.get(targetIndex.getZeroBased());

        if (bookmarkToFavorite.containsTag(Tag.FAVORITE)) {
            throw new CommandException(MESSAGE_FAVORITE_BOOKMARK_DUPLICATE);
        }

        model.favoriteBookmark(bookmarkToFavorite);
        model.saveMark(String.format(MESSAGE_FAVORITE_BOOKMARK_SUCCESS, bookmarkToFavorite));
        return new CommandResult(String.format(MESSAGE_FAVORITE_BOOKMARK_SUCCESS, bookmarkToFavorite));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavoriteCommand // instanceof handles nulls
                && targetIndex.equals(((FavoriteCommand) other).targetIndex)); // state check
    }
}
