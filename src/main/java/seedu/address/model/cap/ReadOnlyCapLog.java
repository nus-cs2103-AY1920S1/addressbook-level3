package seedu.address.model.cap;

import java.util.*;

import javafx.collections.ObservableList;
import seedu.address.model.cap.person.Semester;
import seedu.address.storage.cap.*;
import seedu.address.model.common.Module;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyCapLog {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Semester> getSemesterList();

    ObservableList<Module> getModuleList();

}
