package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.category.Category;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.RatingContainsKeywordPredicate;
import seedu.address.ui.TestFlashCardPanel;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final KeyboardFlashCards keyboardFlashCards;
    private final UserPrefs userPrefs;
    private final FilteredList<FlashCard> filteredFlashCards;
    private final FilteredList<Deadline> filteredDeadlines;
    private final FilteredList<Category> categoryList;
    private FlashCardTestModel flashCardTestModel;
    private ArrayList<Float> performance;


    /**
     * Initializes a ModelManager with the given keyboardFlashCards and userPrefs.
     */
    public ModelManager(ReadOnlyKeyboardFlashCards addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.keyboardFlashCards = new KeyboardFlashCards(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFlashCards = new FilteredList<>(this.keyboardFlashCards.getFlashcardList());
        filteredDeadlines = new FilteredList<>(this.keyboardFlashCards.getDeadlineList());
        categoryList = new FilteredList<>(this.keyboardFlashCards.getCategoryList());
        flashCardTestModel = new FlashCardTestModel(new LinkedList<>());
        this.performance = new ArrayList<Float>();
    }

    public ModelManager() {
        this(new KeyboardFlashCards(), new UserPrefs());
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

    public void setStyleSheet(String styleSheet) {
        userPrefs.setStyleSheet(styleSheet);
    }

    public String getStyleSheet() {
        return userPrefs.getStyleSheet();
    }

    @Override
    public Path getKeyboardFlashCardsFilePath() {
        return userPrefs.getKeyboardFlashCardsFilePath();
    }

    @Override
    public void setKeyboardFlashCardsFilePath(Path keyboardFlashCardsFilePath) {
        requireNonNull(keyboardFlashCardsFilePath);
        userPrefs.setKeyboardFlashCardsFilePath(keyboardFlashCardsFilePath);
    }

    //=========== KeyboardFlashCards ================================================================================

    @Override
    public void setKeyboardFlashCards(ReadOnlyKeyboardFlashCards keyboardFlashCards) {
        this.keyboardFlashCards.resetData(keyboardFlashCards);
    }

    @Override
    public ReadOnlyKeyboardFlashCards getKeyboardFlashCards() {
        return keyboardFlashCards;
    }

    @Override
    public boolean hasFlashcard(FlashCard flashCard) {
        requireNonNull(flashCard);
        return keyboardFlashCards.hasFlashcard(flashCard);
    }

    @Override
    public void deleteFlashCard(FlashCard target) {
        keyboardFlashCards.removeFlashCard(target);
    }

    //@@author shutingy
    @Override
    public void addFlashCard(FlashCard flashCard) {
        keyboardFlashCards.addFlashcard(flashCard);
        updateFilteredFlashCardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        updateFilteredCategoryList(PREDICATE_SHOW_ALL_CATEGORIES);
    }

    @Override
    public void setFlashCard(FlashCard target, FlashCard editedFlashCard) {
        requireAllNonNull(target, editedFlashCard);

        keyboardFlashCards.setFlashcard(target, editedFlashCard);
    }

    @Override
    public void addDeadline(Deadline deadline) {
        keyboardFlashCards.addDeadline(deadline);
        updateFilteredDeadlineList(PREDICATE_SHOW_ALL_DEADLINES);
    }

    @Override
    public boolean hasDeadline(Deadline deadline) {
        requireNonNull(deadline);
        return keyboardFlashCards.hasDeadline(deadline);
    }

    //@@author LeonardTay748
    @Override
    public void editStats(int type) {
        if (type == 0) {
            keyboardFlashCards.addGood();
        }
        if (type == 1) {
            keyboardFlashCards.addHard();
        }
        if (type == 2) {
            keyboardFlashCards.addEasy();
        }
    }

    public int[] getTestStats() {
        return keyboardFlashCards.getStats();
    }

    @Override
    public ArrayList<Float> getPerformance() {
        return performance;
    }

    @Override
    public void updatePerformance(Model model) {
        requireNonNull(model);
        float numGood = model.getFilteredFlashCardListNoCommit(new RatingContainsKeywordPredicate("good")).size();
        float numHard = model.getFilteredFlashCardListNoCommit(new RatingContainsKeywordPredicate("hard")).size();
        float numEasy = model.getFilteredFlashCardListNoCommit(new RatingContainsKeywordPredicate("easy")).size();
        float value;
        if ((numEasy + numGood + numHard) == 0) {
            value = 0;
        } else {
            value = ((numEasy + numGood) * 100) / (numEasy + numGood + numHard);
        }
        performance.add(value);
    }

    @Override
    public void resetPerformance(Model model) {
        requireNonNull(model);
        performance.clear();
    }

    @Override
    public void deleteDeadline(Deadline target) {
        keyboardFlashCards.removeDeadline(target);
    }

    @Override
    public void setDeadline(Deadline target, Deadline editedDeadline) {
        requireAllNonNull(target, editedDeadline);
        keyboardFlashCards.setDeadline(target, editedDeadline);
    }


    //=========== Filtered FlashCard List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code FlashCard} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<FlashCard> getFilteredFlashCardList() {
        return filteredFlashCards;
    }

    @Override
    public void updateFilteredFlashCardList(Predicate<FlashCard> predicate) {
        requireNonNull(predicate);
        filteredFlashCards.setPredicate(predicate);
    }

    //=========== Filtered Deadline List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Deadline} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Deadline> getFilteredDeadlineList() {
        return filteredDeadlines;
    }

    @Override
    public void updateFilteredDeadlineList(Predicate<Deadline> predicate) {
        requireNonNull(predicate);
        filteredDeadlines.setPredicate(predicate);
    }

    //@@author keiteo
    @Override
    public ObservableList<FlashCard> getFlashCardList() {
        return keyboardFlashCards.getFlashcardList();
    }

    //=========== FlashCardTestModel ================================================================================
    @Override
    public void initializeTestModel(List<FlashCard> testList) {
        flashCardTestModel = new FlashCardTestModel(testList);
    }

    @Override
    public boolean hasTestFlashCard() {
        return !flashCardTestModel.isEmpty();
    }

    @Override
    public void setTestFlashCard() {
        flashCardTestModel.setFlashcard();
    }

    @Override
    public String getTestQuestion() {
        return flashCardTestModel.getQuestion();
    }

    @Override
    public String getTestAnswer() {
        return flashCardTestModel.getAnswer();
    }

    @Override
    public FlashCard getCurrentTestFlashCard() {
        return flashCardTestModel.getCurrentFlashCard();
    }

    //@@author shutingy
    @Override
    public TestFlashCardPanel getTestFlashCardPanel() {
        return flashCardTestModel.getTestFlashCardPanel();
    }

    @Override
    public void showAnswer() {
        flashCardTestModel.showAnswer();
    }

    //@@author LeowWB
    @Override
    public ObservableList<FlashCard> getFilteredFlashCardListNoCommit(Predicate<FlashCard> predicate) {
        requireNonNull(predicate);
        FilteredList<FlashCard> simulatedList = new FilteredList<FlashCard>(filteredFlashCards);
        simulatedList.setPredicate(predicate);
        return simulatedList;
    }

    //@@author shutingy
    @Override
    public ObservableList<Category> getCategoryList() {
        return categoryList;
    }

    //@@author shutingy
    @Override
    public void updateFilteredCategoryList(Predicate<Category> predicate) {
        requireNonNull(predicate);
        categoryList.setPredicate(predicate);
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
        return keyboardFlashCards.equals(other.keyboardFlashCards)
                && userPrefs.equals(other.userPrefs)
                && filteredFlashCards.equals(other.filteredFlashCards)
                && categoryList.equals(other.categoryList)
                && filteredDeadlines.equals(other.filteredDeadlines)
                && flashCardTestModel.equals(other.flashCardTestModel);
    }

}
