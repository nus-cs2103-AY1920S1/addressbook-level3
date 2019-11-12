package seedu.address.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;

import seedu.address.logic.FunctionMode;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Note> PREDICATE_SHOW_ALL_NOTES = unused -> true;

    //To delete after deleting unused person commands
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Flashcard> PREDICATE_SHOW_ALL_FLASHCARDS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<CheatSheet> PREDICATE_SHOW_ALL_CHEATSHEETS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Tag> PREDICATE_SHOW_ALL_TAGS = unused -> true;

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
    Path getStudyBuddyProFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setStudyBuddyProFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setStudyBuddyPro(ReadOnlyStudyBuddyPro studyBuddyPro);

    /** Returns the AddressBook */
    ReadOnlyStudyBuddyPro getStudyBuddyPro();

    /** Returns an unmodifiable view of the filtered note list */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Updates the filter of the filtered note list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTagList(Predicate<Tag> predicate);

    /**
     * Returns true if a note with the same identity as {@code note} exists in the address book.
     */
    boolean hasNote(Note note);

    /**
     * Deletes the given note.
     * The note must exist in the address book.
     */
    void deleteNote(Note target);

    /**
     * Adds the given note.
     * {@code note} must not already exist in the address book.
     */
    void addNote(Note note);

    /**
     * Replaces the given note {@code target} with {@code editedNote}.
     * {@code target} must exist in the address book.
     * The note identity of {@code editedNote} must not be the same as another existing note in FiveNotes.
     */
    void setNote(Note target, Note editedNote);

    /** Returns an unmodifiable view of the filtered note list */
    ObservableList<Note> getFilteredNoteList();

    /**
     * Updates the filter of the filtered note list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredNoteList(Predicate<Note> predicate);

    /**
     * Returns true if the same flashcard as {@code flashcard} exists in the application.
     */
    boolean hasFlashcard(Flashcard flashcard);

    /**
     * Adds the given flashcard.
     * {@code flashcard} must not already exist in the application.
     */
    void addFlashcard(Flashcard toAdd);

    /**
     * Replaces the given flashcard {@code target} with {@code editedFlashcard}.
     * {@code target} must exist in the address book.
     * The flashcard identity of {@code editedFlashcard} must not be the same as another existing flashcard in
     * address book.
     */
    void setFlashcard(Flashcard target, Flashcard editedFlashcard);

    /** Returns an unmodifiable view of the filtered flashcard list */
    ObservableList<Flashcard> getFilteredFlashcardList();

    /**
     * Deletes the given flashcard.
     * The flashcard must exist in the flashcard bank.
     */
    void deleteFlashcard(Flashcard target);

    /** Updates the filter of the filtered flashcard list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFlashcardList(Predicate<Flashcard> predicate);

    /**
     * Formats string for output
     * @return String formatted flashcard display
     */
    String formatOutputListString(FunctionMode mode);

    /**
     * Formats string for output.
     * @param object the filteredlist to read
     * @param <T> the different features: cheatsheet, flashcard, notes
     * @return list of all the objects
     */
    <T> String formatList(FilteredList<T> object);

    /**
     * Adds the given cheatSheet.
     * {@code cheatSheet} must not already exist in the cheatSheet book.
     */
    void addCheatSheet(CheatSheet cheatSheet);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasCheatSheet(CheatSheet cheatSheet);

    /**
     * Replaces the given cheatSheet {@code target} with {@code editedCheatSheet}.
     * {@code target} must exist in the cheatSheet book.
     * The cheatSheet identity of {@code editedCheatSheet} must not be the same as
     * another existing editedCheatSheet in Cheatsheet.
     */
    void setCheatSheet(CheatSheet target, CheatSheet editedCheatSheet);

    public ObservableList<CheatSheet> getFilteredCheatSheetList();

    public void updateFilteredCheatSheetList(Predicate<CheatSheet> predicate);

    /**
     * Deletes the given cheatSheet.
     * {@code cheatSheet} must exist in the cheatSheet book.
     */
    void deleteCheatSheet(CheatSheet cheatSheet);

    /**
     * Collect tagged items
     * @param predicate to test on the object
     * @return arraylist of all the studybuddyitems that contains the specified tag
     */
    public ArrayList<String> collectTaggedItems(Predicate<StudyBuddyItem> predicate);

    public ArrayList<String> collectTaggedCheatSheets(Predicate<CheatSheet> predicate);

    public ArrayList<String> collectTaggedFlashcards(Predicate<Flashcard> predicate);

    public ArrayList<String> collectTaggedNotes(Predicate<Note> predicate);

    /**
     * Collect tagged flashcards
     * @param predicate to test on the object
     * @return arraylist of tagged flashcards that contains the specified tag
     */
    public ArrayList<Flashcard> getTaggedFlashcards(Predicate<Flashcard> predicate);

    public ArrayList<String> getListOfTags();

    public ArrayList<StudyBuddyCounter> getStatistics(ArrayList<Tag> tagList);

}
