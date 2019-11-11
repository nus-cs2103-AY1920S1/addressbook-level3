package seedu.address.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.category.Category;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.ui.TestFlashCardPanel;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<FlashCard> PREDICATE_SHOW_ALL_FLASHCARDS = unused -> true;
    Predicate<Category> PREDICATE_SHOW_ALL_CATEGORIES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Deadline> PREDICATE_SHOW_ALL_DEADLINES = unused -> true;

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

    void setStyleSheet(String string);

    String getStyleSheet();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getKeyboardFlashCardsFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setKeyboardFlashCardsFilePath(Path keyboardFlashCardsFilePath);

    /**
     * Replaces address book data with the data in {@code keyboardFlashCards}.
     */
    void setKeyboardFlashCards(ReadOnlyKeyboardFlashCards keyboardFlashCards);

    /** Returns the KeyboardFlashCards */
    ReadOnlyKeyboardFlashCards getKeyboardFlashCards();

    /**
     * Returns true if a flashCard with the same identity as {@code flashCard} exists in the address book.
     *///@@author shutingy
    boolean hasFlashcard(FlashCard flashCard);

    /**
     * Deletes the given flashCard.
     * The flashCard must exist in the address book.
     *///@@author shutingy -reused
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

    //@@author LeonardTay748
    /**
     * Updates the Statistics.
     * type 0,1 & 2 to edit number of Good, Hard & Easy qns completed during the test.
     */
    void editStats(int type);

    /**
     * returns the Statistics.
     * type 0,1 & 2 to edit number of Good, Hard & Easy qns completed during the test.
     */
    int[] getTestStats();

    /**
     * returns an int[] of performance values.
     */
    ArrayList<Float> getPerformance();

    /**
     * updates the int[] that holds all performance values from test mode.
     * adds a new performance value to the existing int[] that holds all performance values from test mode.
     */
    void updatePerformance(Model model);

    /**
     * clears the int[] that holds all performance values from test mode.
     */
    void resetPerformance(Model model);

    /**
     * Adds the given flashCard.
     * {@code flashCard} must not already exist in the address book.
     */
    //@@author shutingy -reused
    void addFlashCard(FlashCard flashCard);

    /**
     * Replaces the given flashCard {@code target} with {@code editedFlashCard}.
     * {@code target} must exist in the address book.
     * The flashCard identity of {@code editedFlashCard}
     * must not be the same as another existing flashCard in the address book.
     */
    //@@author shutingy -reused
    void setFlashCard(FlashCard target, FlashCard editedFlashCard);


    /** Returns an unmodifiable view of the filtered flashCard list */
    ObservableList<FlashCard> getFilteredFlashCardList();

    /**
     * Updates the filter of the filtered flashCard list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFlashCardList(Predicate<FlashCard> predicate);

    //@@author shutingy -reused
    ObservableList<Category> getCategoryList();

    //@@author shutingy -reused
    void updateFilteredCategoryList(Predicate<Category> predicate);

    //@@author keiteo

    /**
     * Returns an entire list of FlashCards in the system.
     * @return An ObservableList of FlashCards
     */
    ObservableList<FlashCard> getFlashCardList();

    /**
     * Initializes the test mode model with a list of FlashCards.
     *
     * @param testList List of FlashCards to be tested.
     */
    void initializeTestModel(List<FlashCard> testList);

    /**
     * Checks if the list of FlashCards in the test model is fully expended.
     *
     * @return A boolean to indicate if the test model still has FlashCards.
     */
    boolean hasTestFlashCard();

    /**
     * Sets the current FlashCard in the TestFlashCardPanel GUI.
     */
    void setTestFlashCard();

    /**
     * Gets the test FlashCard question.
     */
    String getTestQuestion();

    /**
     * Gets the test FlashCard answer.
     */
    String getTestAnswer();

    /**
     * Gets the TestFlashCardPanel GUI class.
     */
    TestFlashCardPanel getTestFlashCardPanel();

    /**
     * Gets the current FlashCard in test mode.
     */
    FlashCard getCurrentTestFlashCard();

    //@@author shutingy
    void showAnswer();

    /**
     * Replaces the given flashCard {@code target} with {@code editedFlashCard}.
     * {@code target} must exist in the address book.
     * The flashCard identity of {@code editedFlashCard}
     * must not be the same as another existing flashCard in the address book.
     */
    void setDeadline(Deadline target, Deadline editedDeadline);

    /** Returns an unmodifiable view of the filtered deadline list */
    //@@author dalsontws
    ObservableList<Deadline> getFilteredDeadlineList();

    /**
     * Updates the filter of the filtered flashCard list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    //@@author dalsontws
    void updateFilteredDeadlineList(Predicate<Deadline> predicate);
    /**
     * Simulates updating the filter of the filtered flashCard list to filter by the given {@code predicate}.
     * Does not actually result in any change to the flashCard list stored within this model.
     * Returns the simulated list.
     * @throws NullPointerException if {@code predicate} is null.
     */
    //@@author LeowWB
    ObservableList<FlashCard> getFilteredFlashCardListNoCommit(Predicate<FlashCard> predicate);
}
