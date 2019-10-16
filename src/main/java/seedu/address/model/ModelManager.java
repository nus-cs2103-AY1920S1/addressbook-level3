package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
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

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<FlashCard> filteredFlashCards;
    private final FilteredList<Deadline> filteredDeadlines;
    private final FilteredList<Category> categoryList;
    private FlashCardTestModel flashCardTestModel;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFlashCards = new FilteredList<>(this.addressBook.getFlashcardList());
        filteredDeadlines = new FilteredList<>(this.addressBook.getDeadlineList());
        categoryList = new FilteredList<>(this.addressBook.getCategoryList());
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
    public boolean hasFlashcard(FlashCard flashCard) {
        requireNonNull(flashCard);
        return addressBook.hasFlashcard(flashCard);
    }

    @Override
    public void deleteFlashCard(FlashCard target) {
        addressBook.removeFlashCard(target);
    }

    @Override
    public void addFlashCard(FlashCard flashCard) {
        addressBook.addFlashcard(flashCard);
        updateFilteredFlashCardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public void setFlashCard(FlashCard target, FlashCard editedFlashCard) {
        requireAllNonNull(target, editedFlashCard);

        addressBook.setFlashcard(target, editedFlashCard);
    }

    @Override
    public void addDeadline(Deadline deadline) {
        addressBook.addDeadline(deadline);
        updateFilteredDeadlineList(PREDICATE_SHOW_ALL_DEADLINES);
    }

    @Override
    public boolean hasDeadline(Deadline deadline) {
        requireNonNull(deadline);
        return addressBook.hasDeadline(deadline);
    }

    @Override
    public void editStats(int type) {
        if (type == 0) {
            addressBook.addGood();
        }
        if (type == 1) {
            addressBook.addHard();
        }
        if (type == 2) {
            addressBook.addEasy();
        }
    }
    //@@author shutingy
    @Override
    public void addFlashCard(FlashCard flashCard) {
        addressBook.addFlashcard(flashCard);
        updateFilteredFlashCardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        updateFilteredCategoryList(PREDICATE_SHOW_ALL_CATEGORIES);
    }
  
    public int[] getStats() {
        return addressBook.getStats();
    }


    @Override
    public void deleteDeadline(Deadline target) {
        addressBook.removeDeadline(target);
    }

    @Override
    public void setDeadline(Deadline target, Deadline editedDeadline) {
        requireAllNonNull(target, editedDeadline);
        addressBook.setDeadline(target, editedDeadline);
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
        return addressBook.getFlashcardList();
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
    public String getTestQuestion() {
        return flashCardTestModel.getQuestion();
    }

    @Override
    public String getTestAnswer() {
        return flashCardTestModel.getAnswer();

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
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredFlashCards.equals(other.filteredFlashCards)
                && categoryList.equals(other.categoryList)
                && filteredDeadlines.equals(other.filteredDeadlines);
    }

}
