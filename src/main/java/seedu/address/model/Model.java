package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Alias;
import seedu.address.commons.core.AliasMappings;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Expense;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Expense> PREDICATE_SHOW_ALL_EXPENSES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /**
     * Fills model data to the given model.
     */
    void fillModelData(Model model);

    /**
     * Returns the model history.
     */
    ReadOnlyModelHistory getModelHistory();

    /**
     * Replaces model history with the data in {@code history}.
     */
    void setModelHistory(ReadOnlyModelHistory history);

    /**
     * Returns the rolled-back version of the model to the immediate previous state.
     */
    Optional<Model> rollbackModel();

    /**
     * Returns the migrated version of the model to the immediate next state.
     */
    Optional<Model> migrateModel();

    /**
     * Adds the current model to the history.
     */
    void addToHistory();

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    // ======== GUI SETTINGS ===============
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    // ======== ALIAS SETTINGS ===========
    /**
     * Return's the user prefs' alias mappings.
     */
    AliasMappings getAliasMappings();

    /**
     * Sets the user prefs' alias mappings.
     */
    void setAliasMappings(AliasMappings aliasMappings);

    /**
     * Add a user defined alias to the user prefs' alias mappings.
     */
    void addUserAlias(Alias alias);


    // ======== ADDRESS BOOK SETTINGS ===============
    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    // ======== ADDRESS BOOK ACTIONS ===============
    /**
     * Returns true if a expense with the same identity as {@code expense}
     * exists in the address book.
     */
    boolean hasExpense(Expense expense);

    /**
     * Deletes the given expense.
     * The expense must exist in the address book.
     */
    void deleteExpense(Expense target);

    /**
     * Adds the given expense.
     * {@code expense} must not already exist in the address book.
     */
    void addExpense(Expense expense);

    /**
     * Replaces the given expense {@code target} with {@code editedExpense}.
     * {@code target} must exist in the address book.
     * The expense identity of {@code editedExpense} must not be the same as another
     * existing expense in the address book.
     */
    void setExpense(Expense target, Expense editedExpense);

    boolean hasBudget(Budget budget);

    void addBudget(Budget budget);

    void setPrimary(Budget budget);

    /** Returns an unmodifiable view of the filtered expense list */
    ObservableList<Expense> getFilteredExpenseList();

    /** Returns the predicate of the filltred expense list. **/
    Predicate<? super Expense> getFilteredExpensePredicate();

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenseList(Predicate<? super Expense> predicate);

    /** Returns an unmodifiable view of the filtered expense list */
    ObservableList<Event> getFilteredEventList();

    /** Returns the predicate of the filtered expense list */
    Predicate<? super Event> getFilteredEventPredicate();

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<? super Event> predicate);

    boolean hasEvent(Event event);

    void addEvent(Event event);
}
