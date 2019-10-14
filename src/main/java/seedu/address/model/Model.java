package seedu.address.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.flashcard.FlashCard;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<FlashCard> PREDICATE_SHOW_ALL_FLASHCARDS = unused -> true;

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

    /**
     * Returns true if a flashCard with the same identity as {@code flashCard} exists in the address book.
     */
    boolean hasFlashcard(FlashCard flashCard);

    /**
     * Deletes the given flashCard.
     * The flashCard must exist in the address book.
     */
    void deleteFlashCard(FlashCard target);

    /**
     * Adds the given deadline.
     * {@code deadline} must not already exist in the address book.
     */
    void addDeadline(Deadline deadline);

    /**
     * Returns true if a deadline with the same identity as {@code deadline} exists in the address book.
     */
    boolean hasDeadline(Deadline deadline);

    /**
     * Deletes the given deadline.
     * The deadline must exist in the address book.
     */
    void deleteDeadline(Deadline deadline);

    /**
     * Adds the given flashCard.
     * {@code flashCard} must not already exist in the address book.
     */
    void addFlashCard(FlashCard flashCard);



    /**
     * Replaces the given flashCard {@code target} with {@code editedFlashCard}.
     * {@code target} must exist in the address book.
     * The flashCard identity of {@code editedFlashCard}
     * must not be the same as another existing flashCard in the address book.
     */
    void setFlashCard(FlashCard target, FlashCard editedFlashCard);


    /** Returns an unmodifiable view of the filtered flashCard list */
    ObservableList<FlashCard> getFilteredFlashCardList();

    /**
     * Updates the filter of the filtered flashCard list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFlashCardList(Predicate<FlashCard> predicate);

    //@@author keiteo
    // TODO: replace arraylist with actual decks
    void initializeTestModel(ArrayList<FlashCard> testList);

    boolean hasTestFlashCard();

    String getTestQuestion();

    String getTestAnswer();
}
