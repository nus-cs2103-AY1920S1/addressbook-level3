package seedu.tarence.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.logic.commands.Command;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;


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
     * Returns the user prefs' application file path.
     */
    Path getApplicationFilePath();

    /**
     * Sets the user prefs' application file path.
     */
    void setApplicationFilePath(Path applicationFilePath);

    /**
     * Replaces application data with the data in {@code application}.
     */
    void setApplication(ReadOnlyApplication application);

    /** Returns the application */
    ReadOnlyApplication getApplication();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the application.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the application.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the application.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the application.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the application.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns true if a student with the same identity as {@code student} exists in the application.
     */
    boolean hasStudent(Student student);


    /**
     * Adds the given student.
     * {@code student} must not already exist in the application.
     */
    void addStudent(Student student);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the filtered module list */
    ObservableList<Module> getFilteredModuleList();

    /** Returns an unmodifiable view of the filtered tutorial list */
    ObservableList<Tutorial> getFilteredTutorialList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

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
     * Updates the filter of the filtered tutorial list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTutorialList(Predicate<Tutorial> predicate);

    /**
     * Returns true if a module with the same identity as {@code module} exists in the application.
     */
    boolean hasModule(Module module);

    /**
     * Checks if a module of the given code exists.
     */
    boolean hasModuleOfCode(ModCode modCode);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the application.
     */
    void addModule(Module module);

    /**
     * Deletes the given module.
     * Assumes the module exists in the application.
     */
    void deleteModule(Module module);

    void deleteTutorialsFromModule(Module module);

    boolean hasTutorial(Tutorial tutorial);

    void addTutorial(Tutorial tutorial);

    void deleteTutorial(Tutorial tutorial);

    void deleteStudentsFromTutorial(Tutorial tutorial);

    void addTutorialToModule(Tutorial tutorial);

    void addStudentToTutorial(Student student);

    /**
     * Checks if a tutorial of the given name exists in a module of the given code.
     */
    boolean hasTutorialInModule(ModCode modCode, TutName tutName);

    /**
     * Checks if there are multiple tutorials of the same name in the application.
     */
    int getNumberOfTutorialsOfName(TutName tutName);

    /**
     * Stores a command to be executed pending user confirmation.
     */
    void storePendingCommand(Command command);

    /**
     * Removes pending command and returns it for execution.
     */
    Command getPendingCommand();

    /**
     * Checks if a pending command exists in the application.
     */
    boolean hasPendingCommand();
}
