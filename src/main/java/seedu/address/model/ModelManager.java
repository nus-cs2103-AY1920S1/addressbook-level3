package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Expense;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final ModelHistory modelHistory;
    private final FilteredList<Expense> filteredExpenses;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
        filteredExpenses = new FilteredList<>(this.addressBook.getExpenseList());
        this.modelHistory = new ModelHistory();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    /**
     * Copy constructor for ModelManager.
     */
    public ModelManager(Model model) {
        this(model.getAddressBook(), model.getUserPrefs());
        setModelHistory(model.getModelHistory());
    }

    @Override
    public void fillModelData(Model model) {
        requireNonNull(model);
        setAddressBook(model.getAddressBook());
        setUserPrefs(model.getUserPrefs());

        if (model.getFilteredEventPredicate() != null) {
            updateFilteredEventList(model.getFilteredEventPredicate());
        } else {
            updateFilteredEventList(model.PREDICATE_SHOW_ALL_EVENTS);
        }

        if (model.getFilteredExpensePredicate() != null) {
            updateFilteredExpenseList(model.getFilteredExpensePredicate());
        } else {
            updateFilteredExpenseList(model.PREDICATE_SHOW_ALL_EXPENSES);
        }
    }

    //=========== ModelHistory ==================================================================================

    @Override
    public ReadOnlyModelHistory getModelHistory() {
        return modelHistory;
    }

    @Override
    public void setModelHistory(ReadOnlyModelHistory modelHistory) {
        requireNonNull(modelHistory);
        this.modelHistory.resetData(modelHistory);
    }

    @Override
    public Optional<Model> rollbackModel() {
        Optional<Model> prevModel = modelHistory.getPrevModel();
        if (prevModel.isPresent()) {
            modelHistory.addToFutureModels(new ModelManager(this));
        }
        return prevModel;
    }

    @Override
    public Optional<Model> migrateModel() {
        Optional<Model> nextModel = modelHistory.getNextModel();
        if (nextModel.isPresent()) {
            modelHistory.addToPastModels(new ModelManager(this));
        }
        return nextModel;
    }

    @Override
    public void addToHistory() {
        modelHistory.addToPastModels(new ModelManager(this));
        modelHistory.clearFutureModels();
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
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

    //=========== GuiSettings ==================================================================================

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    //=========== Expense ================================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        requireNonNull(addressBook);
        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return addressBook.hasExpense(expense);
    }

    @Override
    public void deleteExpense(Expense target) {
        addressBook.removeExpense(target);
    }

    @Override
    public void addExpense(Expense expense) {
        addressBook.addExpense(expense);
        updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
    }

    @Override
    public void setExpense(Expense target, Expense editedExpense) {
        requireAllNonNull(target, editedExpense);

        addressBook.setExpense(target, editedExpense);
    }

    //=========== Budget ================================================================================

    @Override
    public boolean hasBudget(Budget budget) {
        requireNonNull(budget);
        return addressBook.hasBudget(budget);
    }

    @Override
    public void addBudget(Budget budget) {
        addressBook.addBudget(budget);
    }

    @Override
    public void setPrimary(Budget budget) {
        addressBook.setPrimary(budget);
    }

    //=========== Event ================================================================================

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return addressBook.hasEvent(event);
    }

    //    @Override
    //    public void deleteEvent(Event target) {
    //        addressBook.removeEvent(target);
    //    }

    @Override
    public void addEvent(Event event) {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    //    @Override
    //    public void setEvent(Event target, Event editedEvent) {
    //        requireAllNonNull(target, editedEvent);
    //
    //        addressBook.setEvent(target, editedEvent);
    //    }

    //=========== Filtered Expense List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Expense} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        return filteredExpenses;
    }

    @Override
    public Predicate<? super Expense> getFilteredExpensePredicate() {
        return filteredExpenses.getPredicate();
    }

    @Override
    public void updateFilteredExpenseList(Predicate<? super Expense> predicate) {
        requireNonNull(predicate);
        filteredExpenses.setPredicate(predicate);
    }

    //=========== Filtered Event List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Expense} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public Predicate<? super Event> getFilteredEventPredicate() {
        return filteredEvents.getPredicate();
    }

    @Override
    public void updateFilteredEventList(Predicate<? super Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
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
                && filteredExpenses.equals(other.filteredExpenses)
                && filteredEvents.equals(other.filteredEvents);
    }

    @Override
    public String toString() {
        return "Model with " + addressBook;
    }
}
