package seedu.moneygowhere.model;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.moneygowhere.commons.core.GuiSettings;
import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.spending.Spending;

/**
 * Represents the in-memory model of the MoneyGoWhere list data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final SpendingBook spendingBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Spending> filteredSpendings;

    /**
     * Initializes a ModelManager with the given spendingBook and userPrefs.
     */
    public ModelManager(ReadOnlySpendingBook spendingBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(spendingBook, userPrefs);

        logger.fine("Initializing with MoneyGoWhere: " + spendingBook + " and user prefs " + userPrefs);

        this.spendingBook = new SpendingBook(spendingBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredSpendings = new FilteredList<>(this.spendingBook.getSpendingList());
    }

    public ModelManager() {
        this(new SpendingBook(), new UserPrefs());
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
    public Path getSpendingBookFilePath() {
        return userPrefs.getSpendingBookFilePath();
    }

    @Override
    public void setSpendingBookFilePath(Path spendingBookFilePath) {
        requireNonNull(spendingBookFilePath);
        userPrefs.setSpendingBookFilePath(spendingBookFilePath);
    }

    //=========== SpendingBook ================================================================================

    @Override
    public void setSpendingBook(ReadOnlySpendingBook spendingBook) {
        this.spendingBook.resetData(spendingBook);
    }

    @Override
    public ReadOnlySpendingBook getSpendingBook() {
        return spendingBook;
    }

    @Override
    public boolean hasSpending(Spending spending) {
        requireNonNull(spending);
        return spendingBook.hasSpending(spending);
    }

    @Override
    public void deleteSpending(Spending target) {
        spendingBook.removeSpending(target);
    }

    @Override
    public void addSpending(Spending spending) {
        spendingBook.addSpending(spending);
        updateFilteredSpendingList(PREDICATE_SHOW_ALL_SPENDINGS);
    }

    @Override
    public void setSpending(Spending target, Spending editedSpending) {
        requireAllNonNull(target, editedSpending);

        spendingBook.setSpending(target, editedSpending);
    }

    //=========== Budget related things =====================================================================

    @Override
    public void setBudget(Budget budget) {
        spendingBook.setBudget(budget);
    }

    @Override
    public Budget getBudget() {
        return spendingBook.getBudget();
    }

    //=========== Reminder related functions =====================================================================

    @Override
    public void addReminder(Reminder reminder) {
        spendingBook.addReminder(reminder);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedSpendingBook}
     */
    @Override
    public ObservableList<Spending> getFilteredSpendingList() {
        return filteredSpendings;
    }

    @Override
    public void updateFilteredSpendingList(Predicate<Spending> predicate) {
        requireNonNull(predicate);
        filteredSpendings.setPredicate(predicate);
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
        return spendingBook.equals(other.spendingBook)
                && userPrefs.equals(other.userPrefs)
                && filteredSpendings.equals(other.filteredSpendings);

    }

}
