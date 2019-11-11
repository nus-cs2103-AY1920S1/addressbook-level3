package mams.model;

import javafx.collections.ObservableList;
import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.Student;

/**
 * Unmodifiable view of MAMS
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

    /**
     * Returns an unmodifiable view of the appeal list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Appeal> getAppealList();
}
