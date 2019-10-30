package seedu.moneygowhere.model;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.moneygowhere.commons.core.GuiSettings;
import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.logic.sorting.ReminderComparator;
import seedu.moneygowhere.logic.sorting.SpendingComparator;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.currency.Currency;
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
    private final SortedList<Spending> sortedSpendings;
    private final SortedList<Reminder> sortedReminders;

    private Predicate<Spending> statsPredicate;

    /**
     * Initializes a ModelManager with the given spendingBook and userPrefs.
     */
    public ModelManager(ReadOnlySpendingBook spendingBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(spendingBook, userPrefs);

        logger.fine("Initializing with MoneyGoWhere: " + spendingBook + " and user prefs " + userPrefs);

        this.spendingBook = new SpendingBook(spendingBook);
        this.userPrefs = new UserPrefs(userPrefs);

        sortedSpendings = new SortedList<>(this.spendingBook.getSpendingList());
        sortedSpendings.setComparator(new SpendingComparator());

        filteredSpendings = new FilteredList<>(sortedSpendings);

        sortedReminders = new SortedList<>(this.spendingBook.getReminderList());
        sortedReminders.setComparator(new ReminderComparator());
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

    //=========== Budget related functions =====================================================================

    @Override
    public void setBudget(Budget budget) {
        spendingBook.setBudget(budget);
    }

    @Override
    public Budget getBudget() {
        return spendingBook.getBudget();
    }

    @Override
    public void clearBudgetSum() {
        spendingBook.clearBudgetSum();
    }

    //=========== Currency functions =====================================================================

    @Override
    public ObservableList<Currency> getCurrencies() {
        return spendingBook.getCurrencies();
    }

    @Override
    public Currency getCurrencyInUse() {
        return spendingBook.getCurrencyInUse();
    }

    @Override
    public void setCurrencyInUse(Currency currency) {
        spendingBook.setCurrencyInUse(currency);
    }

    //=========== Reminder related functions =====================================================================

    @Override
    public void deleteReminder(Reminder target) {
        spendingBook.removeReminder(target);
    }

    @Override
    public void addReminder(Reminder reminder) {
        spendingBook.addReminder(reminder);
    }

    @Override
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return spendingBook.hasReminder(reminder);
    }

    //=========== Sorted Reminder List Accessors =============================================================

    /**
     * Returns an sorted view of the list of {@code Reminder}
     */
    @Override
    public ObservableList<Reminder> getSortedReminderList() {
        return sortedReminders;
    }

    //=========== Statistics related functions =====================================================================
    /**
     * Returns an unmodifiable view of spending, filtered by {@code statsPredicate} and sorted by date.
     *
     * @return {@code ObservableList<Spending>} of spending which fulfill the date range provided
     */
    @Override
    public ObservableList<Spending> getStatsList() {
        FilteredList<Spending> filteredList = new FilteredList<>(getFilteredSpendingList());
        filteredList.setPredicate(statsPredicate);

        SortedList<Spending> sortedList = new SortedList<>(filteredList);
        Comparator<Spending> byDate = (Spending a, Spending b) -> (a.getDate().compareTo(b.getDate()));
        sortedList.setComparator(byDate);

        return FXCollections.unmodifiableObservableList(sortedList);
    }

    @Override
    public void updateStatsPredicate(Predicate<Spending> predicate) {
        statsPredicate = predicate;
    }

    //=========== Filtered Spending List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Spending} backed by the internal list of
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

    //=========== Sorted Spending List Accessors =============================================================

    @Override
    public void updateSortedSpendingList(Comparator<Spending> comparator) {
        requireNonNull(comparator);
        sortedSpendings.setComparator(comparator);
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
