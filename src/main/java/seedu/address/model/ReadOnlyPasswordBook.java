package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.password.Password;

/**
 * Unmodifiable view of an password book
 */
public interface ReadOnlyPasswordBook {
    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Password> getPasswordList();
}
