package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * Interface for ReadOnlyCheatSheetBook
 * //to fill up
 */

public interface ReadOnlyCheatSheetBook {

    /**
     * Returns an unmodifiable view of the cheatSheets list.
     * This list will not contain any duplicate cheatSheets.
     * @return a cheatsheet list
     */

    ObservableList<Person> getCheatSheetList();

}
