package seedu.mark.model;

import javafx.collections.ObservableList;
import seedu.mark.model.bookmark.Bookmark;

/**
 * Unmodifiable view of a bookmark manager
 */
public interface ReadOnlyBookmarkManager {

    /**
     * Returns an unmodifiable view of the bookmarks list.
     * This list will not contain any duplicate bookmarks.
     */
    ObservableList<Bookmark> getBookmarkList();

}
