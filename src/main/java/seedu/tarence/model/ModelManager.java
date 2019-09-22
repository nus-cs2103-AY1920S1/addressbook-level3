package seedu.tarence.model;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.model.person.Person;

/**
 * Represents the in-memory model of the student book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final StudentBook studentBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given student and userPrefs.
     */
    public ModelManager(ReadOnlyStudentBook studentBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(studentBook, userPrefs);

        logger.fine("Initializing with student book: " + studentBook + " and user prefs " + userPrefs);

        this.studentBook = new StudentBook(studentBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.studentBook.getPersonList());
    }

    public ModelManager() {
        this(new StudentBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getStudentBookFilePath() {
        return userPrefs.getStudentBookFilePath();
    }

    @Override
    public void setStudentBookFilePath(Path studentBookFilePath) {
        requireNonNull(studentBookFilePath);
        userPrefs.setStudentBookFilePath(studentBookFilePath);
    }

    //=========== T.A.rence ================================================================================

    @Override
    public void setStudentBook(ReadOnlyStudentBook studentBook) {
        this.studentBook.resetData(studentBook);
    }

    @Override
    public ReadOnlyStudentBook getStudentBook() {
        return studentBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return studentBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        studentBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        studentBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        studentBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedStudentBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return studentBook.equals(other.studentBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
