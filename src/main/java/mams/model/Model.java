package mams.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import mams.commons.core.GuiSettings;
import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;
    /** {@code Predicate} that always evaluate to true */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;
    /** {@code Predicate} that always evaluate to true */
    Predicate<Appeal> PREDICATE_SHOW_ALL_APPEALS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getMamsFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setMamsFilePath(Path mamsFilePath);

    /**
     * Replaces address book data with the data in {@code mams}.
     */
    void setMams(ReadOnlyMams mams);

    /** Returns the Mams */
    ReadOnlyMams getMams();

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    boolean hasStudent(Student student);

    /**
     *
     * Returns true if a module with the same identity as {@code module} exists in MAMS.
     */
    boolean hasModule(Module module);

    /**
     *
     * Returns true if an appeal with the same identity as {@code appeal} exists in MAMS.
     */
    boolean hasAppeal(Appeal appeal);

    /**
     * Deletes the given student.
     * The student must exist in MAMS.
     */
    void deleteStudent(Student target);

    /**
     * Deletes the given module.
     * The module must exist in MAMS.
     */
    void deleteModule(Module target);

    /**
     * Deletes the given appeal.
     * The appeal must exist in MAMS.
     */
    void deleteAppeal(Appeal target);


    /**
     * Adds the given student.
     * {@code student} must not already exist in MAMS.
     */
    void addStudent(Student student);

    /**
     * Adds the given module.
     * {@code module} must not already exist in MAMS.
     */
    void addModule(Module module);

    /**
     * Adds the given appeal.
     * {@code appeal} must not already exist in MAMS.
     */
    void addAppeal(Appeal appeal);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in MAMS.
     * The student identity of {@code editedStudent} must not be
     * the same as another existing student in MAMS.
     */
    void setStudent(Student target, Student editedStudent);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in MAMS.
     * The module identity of {@code editedModule} must not be
     * the same as another existing module in MAMS.
     */
    void setModule(Module target, Module editedModule);

    /**
     * Replaces the given appeal {@code target} with {@code editedAppeal}.
     * {@code target} must exist in MAMS.
     * The appeal identity of {@code editedAppeal} must not be
     * the same as another existing appeal in MAMS.
     */
    void setAppeal(Appeal target, Appeal editedAppeal);

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the filtered module list */
    ObservableList<Module> getFilteredModuleList();

    /** Returns an unmodifiable view of the filtered appeal list */
    ObservableList<Appeal> getFilteredAppealList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Updates the filter of the filtered appeal list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppealList(Predicate<Appeal> predicate);
}
