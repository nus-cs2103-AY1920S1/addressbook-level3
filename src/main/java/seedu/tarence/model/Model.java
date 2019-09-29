package seedu.tarence.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.tarence.commons.core.GuiSettings;
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

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

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

    boolean hasTutorial(Tutorial tutorial);

    void addTutorial(Tutorial tutorial);

    void addTutorialToModule(Tutorial tutorial);

    void addStudentToTutorial(Student student);

    /**
     * Checks if a tutorial of the given name exists in a module of the given code.
     */
    boolean hasTutorialInModule(ModCode modCode, TutName tutName);
}
