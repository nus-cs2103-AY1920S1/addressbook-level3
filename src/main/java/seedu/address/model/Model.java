package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;
import seedu.address.model.question.Question;

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

    //=========== AddressBook ================================================================================

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    //=========== Students ================================================================================
    /**
     * Adds the given student.
     * {@code student} must not exist in the student list.
     */
    void addStudent(Student student);

    /**
     * Returns the student that has been deleted based on the index.
     */
    void deleteStudent(Student student);

    /**
     * Returns the student based on its Index.
     */
    Student getStudent(Index index);

    /**
     * Replaces the student at the specified index.
     */
    void setStudent(Index index, Student student);

    /**
     * Returns the list of students.
     *
     * @return list of students
     */
    String getStudentList();


    //=========== Questions ================================================================================
    /**
     * Adds the given question.
     * {@code question} must not exist in the question list.
     */
    void addQuestion(Question question);

    /**
     * Returns the question that has been deleted based on the index.
     */
    Question deleteQuestion(Index index);

    /**
     * Returns the question based on its Index.
     */
    Question getQuestion(Index index);

    /**
     * Replaces the question at the specified index.
     */
    void setQuestion(Index index, Question question);

    /**
     * Returns the questions summary.
     *
     * @return Summary of questions list.
     */
    String getQuestionsSummary();

    //=========== Questions ================================================================================

    /**
     * Adds the given note.
     * {@code note} must not exist in the note list.
     */
    void addNote(Note note);

    /**
     * Returns the note that has been deleted based on the index.
     */
    Note deleteNote(Index index);

    /**
     * Returns the note based on its Index.
     */
    Note getNote(Index index);

    /**
     * Replaces the note at the specified index.
     */
    void setNote(Index index, Note question);

    /**
     * Returns the notes summary.
     *
     * @return Summary of notes list.
     */
    String getNoteList();

    //=========== Person ================================================================================

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
}
