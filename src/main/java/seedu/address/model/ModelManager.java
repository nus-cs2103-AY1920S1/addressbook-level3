package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;

import seedu.address.logic.FunctionMode;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Flashcard> filteredFlashcards;
    private final FilteredList<Note> filteredNotes;
    private final FilteredList<CheatSheet> filteredCheatSheets;
    private final FilteredList<Tag> filteredTags;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredFlashcards = new FilteredList<>(this.addressBook.getFlashcardList());
        filteredNotes = new FilteredList<>(this.addressBook.getNoteList());
        filteredCheatSheets = new FilteredList<>(this.addressBook.getCheatSheetList());
        filteredTags = new FilteredList<>(this.addressBook.getTagList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }


    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return addressBook.hasFlashcard(flashcard);
    }

    @Override
    public void addFlashcard(Flashcard flashcard) {
        addressBook.addFlashcard(flashcard);
        updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);

        addressBook.setFlashcard(target, editedFlashcard);
    }

    @Override
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return addressBook.hasNote(note);
    }

    @Override
    public void deleteNote(Note target) {
        addressBook.removeNote(target);
    }

    @Override
    public void addNote(Note note) {
        addressBook.addNote(note);
        updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
    }

    @Override
    public void setNote(Note target, Note editedNote) {
        requireAllNonNull(target, editedNote);

        addressBook.setNote(target, editedNote);
    }

    //=========== Filtered Tag List Accessors =============================================================

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return filteredTags;
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredTags.setPredicate(predicate);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
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

    //=========== Filtered Flashcard List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Flashcard} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return filteredFlashcards;
    }

    @Override
    public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
        requireNonNull(predicate);
        filteredFlashcards.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Note} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Note> getFilteredNoteList() {
        return filteredNotes;
    }

    @Override
    public void updateFilteredNoteList(Predicate<Note> predicate) {
        requireNonNull(predicate);
        filteredNotes.setPredicate(predicate);
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
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredNotes.equals(other.filteredNotes);
    }

    @Override
    public void deleteFlashcard(Flashcard target) {
        addressBook.removeFlashcard(target);
    }

    /**
     * Formats string for output
     * @return String formatted flashcard display
     */
    public String formatOutputListString(FunctionMode mode) {
        String msg = "";

        switch (mode) {
        case CHEATSHEET:
            msg = formatList(filteredCheatSheets);
            break;

        case FLASHCARD:
            msg = formatList(filteredFlashcards);
            break;

        case NOTE:
            msg = formatList(filteredNotes);
            break;

        default:
            // error?
        }

        return msg;
    }

    /**
     * Formats string for output.
     * @param object the filteredlist to read
     * @param <T> the different features: cheatsheet, flashcard, notes
     * @return list of all the objects
     */
    public <T> String formatList(FilteredList<T> object) {
        int size = object.size();

        if (size == 0) {
            return "[Empty list]";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= size; i++) {
            T feature = object.get(i - 1);
            sb.append(i)
                    .append(". ")
                    .append(feature.toString());

            if (i != size) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    //===================CheatSheetBook============================================================

    @Override
    public void addCheatSheet(CheatSheet cheatSheet) {
        addressBook.addCheatSheet(cheatSheet);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public boolean hasCheatSheet(CheatSheet cheatSheet) {
        requireNonNull(cheatSheet);
        return addressBook.hasCheatSheet(cheatSheet);
    }

    @Override
    public void setCheatSheet(CheatSheet target, CheatSheet editedCheatSheet) {
        requireAllNonNull(target, editedCheatSheet);

        addressBook.setCheatSheet(target, editedCheatSheet);
    }

    @Override
    public void deleteCheatSheet(CheatSheet cheatSheet) {
        addressBook.deleteCheatSheet(cheatSheet);
    }

    //=========== Filtered CheatSheet List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<CheatSheet> getFilteredCheatSheetList() {
        return filteredCheatSheets;
    }

    @Override
    public void updateFilteredCheatSheetList(Predicate<CheatSheet> predicate) {
        requireNonNull(predicate);
        filteredCheatSheets.setPredicate(predicate);
    }

    //========================GLOBAL COLLECT TAGGED ITEMS======================================
    @Override
    public ArrayList<StudyBuddyItem> collectTaggedItems(Predicate<StudyBuddyItem> predicate) {
        ArrayList<StudyBuddyItem> taggedItems = new ArrayList<>();
        for (Flashcard fc : addressBook.getFlashcardList()) {
            if (predicate.test(fc)) {
                taggedItems.add(fc);
            }
        }
        for (CheatSheet cs : addressBook.getCheatSheetList()) {
            if (predicate.test(cs)) {
                taggedItems.add(cs);
            }
        }
        for (Note n : addressBook.getNoteList()) {
            if (predicate.test(n)) {
                taggedItems.add(n);
            }
        }
        return taggedItems;
    }
}
