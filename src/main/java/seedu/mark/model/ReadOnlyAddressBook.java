package seedu.mark.model;

import javafx.collections.ObservableList;
import seedu.mark.model.bookmark.Bookmark;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Bookmark> getPersonList();

}
