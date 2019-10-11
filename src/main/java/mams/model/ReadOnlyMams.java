package mams.model;

import javafx.collections.ObservableList;
import mams.model.student.Student;
import mams.model.module.Module;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyMams {

    /**
     * Returns an unmodifiable view of the students list.
     * This list will not contain any duplicate students.
     */
    ObservableList<Student> getStudentList();

    /**
     * Returns an unmodifiable view of the module list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

}
