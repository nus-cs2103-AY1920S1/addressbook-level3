package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data" , "addressbook.json");
    private Path studentRecordFilePath = Paths.get("data" , "students.json");
    private Path savedQuestionsFilePath = Paths.get("data" , "questions.json");
    private Path notesRecordFilePath = Paths.get("data", "notes.json");


    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
        setStudentRecordFilePath(newUserPrefs.getStudentRecordFilePath());
        setNotesRecordFilePath(newUserPrefs.getNotesRecordFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    //region AddressBook
    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }
    //endregion

    //region StudentRecord
    public Path getStudentRecordFilePath() {
        return studentRecordFilePath;
    }

    public void setStudentRecordFilePath(Path studentsFilePath) {
        requireNonNull(studentsFilePath);
        this.studentRecordFilePath = studentsFilePath;
    }
    //endregion

    //region SavedQuestions
    public Path getSavedQuestionsFilePath() {
        return savedQuestionsFilePath;
    }

    public void setSavedQuestionsFilePath(Path savedQuestionsFilePath) {
        requireNonNull(savedQuestionsFilePath);
        this.savedQuestionsFilePath = savedQuestionsFilePath;
    }
    //endregion

    //region NotesRecord
    public Path getNotesRecordFilePath() {
        return notesRecordFilePath;
    }

    public void setNotesRecordFilePath(Path notesRecordFilePath) {
        requireNonNull(notesRecordFilePath);
        this.notesRecordFilePath = notesRecordFilePath;
    }
    //endregion

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && addressBookFilePath.equals(o.addressBookFilePath)
                && studentRecordFilePath.equals(o.studentRecordFilePath)
                && notesRecordFilePath.equals(o.notesRecordFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, studentRecordFilePath,
                savedQuestionsFilePath, notesRecordFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal address book data file location : " + addressBookFilePath);
        sb.append("\nLocal student data file location : " + studentRecordFilePath);
        sb.append("\nLocal questions data file location : " + savedQuestionsFilePath);
        sb.append("\nLocal notes data file location : " + notesRecordFilePath);
        return sb.toString();
    }

}
