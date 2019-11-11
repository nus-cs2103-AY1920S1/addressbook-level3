package seedu.moolah.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.moolah.commons.core.GuiSettings;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.model.alias.AliasMappings;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ReadOnlyModelHistory;
import seedu.moolah.model.statistics.Statistics;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Expense> PREDICATE_SHOW_ALL_EXPENSES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    Predicate<Budget> PREDICATE_SHOW_ALL_BUDGETS = unused -> true;

    /**
     * Resets the model according to the given model.
     * @param model the {@code Model} whose values are going to replace the current ones
     */
    void resetData(Model model);

    /**
     * Creates a copy of the current model.
     * @return a copy of the current model.
     */
    Model copy();

    /**
     * Modifies the model according to the given changes.
     * @param changes changes that are going to be applied to the model
     */
    void applyChanges(ModelChanges changes);

    // ======== MODEL HISTORY ===============

    /**
     * Returns the model history.
     * @return a read-only view of the model history
     */
    ReadOnlyModelHistory getModelHistory();

    /**
     * Replaces model history with the data in {@code history}.
     * @param history the {@code ModelHistory} object whose data are going to replace the current ones
     */
    void setModelHistory(ReadOnlyModelHistory history);

    /**
     * Adds an entry to the past changes.
     * @param changes the {@code ModelChanges} to be added
     */
    void addToPastChanges(ModelChanges changes);

    /**
     * Adds an entry to the future changes.
     * @param changes the {@code ModelChanges} to be added
     */
    void addToFutureChanges(ModelChanges changes);

    /**
     * Commits the current model to the history with respect to the previous model.
     * @param changeMessage the change message to be recorded
     * @param prevModel the previous state of the model for reference
     */
    void commit(String changeMessage, Model prevModel);

    /**
     * Checks whether model can be rolled-back.
     * @return true if model can be rolled-back, false otherwise
     */
    boolean canRollback();

    /**
     * Rolls back model to the immediate previous state and returns the
     * description of the applied change.
     * @return an {@code Optional} that contains the description of the applied change if there are changes,
     * false otherwise.
     */
    Optional<String> rollback();

    /**
     * Checks whether model can be migrated.
     * @return true if model can be migrated, false otherwise
     */
    boolean canMigrate();

    /**
     * Migrates model to the immediate next state and returns the
     * description of the applied change.
     * @return an {@code Optional} that contains the description of the applied change if there are changes,
     * false otherwise.
     */
    Optional<String> migrate();

    // ======== USER PREFS ===============
    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    // ======== GUI SETTINGS ===============

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

    /**
     * Removes an alias with the given name if it exists, and returns true, otherwise return false.
     */
    boolean removeAliasWithName(String aliasName);

    boolean aliasWithNameExists(String aliasName);

    // ======== MOOLAH SETTINGS ===============
    /**
     * Returns the user prefs' MooLah file path.
     */
    Path getMooLahFilePath();

    /**
     * Sets the user prefs' MooLah file path.
     */
    void setMooLahFilePath(Path mooLahFilePath);

    /** Returns the MooLah */
    ReadOnlyMooLah getMooLah();

    /**
     * Replaces MooLah data with the data in {@code mooLah}.
     */
    void setMooLah(ReadOnlyMooLah mooLah);

    // ======== MOOLAH ACTIONS ===============
    // ================================ EXPENSE =======================================
    /**
     * Returns true if a expense with the same identity as {@code expense}
     * exists in the MooLah.
     */
    boolean hasExpense(Expense expense);

    /**
     * Deletes the given expense.
     * The expense must exist in the MooLah.
     */
    void deleteExpense(Expense target);

    /**
     * Adds the given expense.
     * {@code expense} must not already exist in the MooLah.
     */
    void addExpense(Expense expense);

    /**
     * Replaces the given expense {@code target} with {@code editedExpense}.
     * {@code target} must exist in the MooLah.
     * The expense identity of {@code editedExpense} must not be the same as another
     * existing expense in the MooLah.
     */
    void setExpense(Expense target, Expense editedExpense);

    /** Returns an unmodifiable view of the filtered expense list */
    ObservableList<Expense> getFilteredExpenseList();

    /** Returns the predicate of the filtered expense list. **/
    Predicate<? super Expense> getFilteredExpensePredicate();

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenseList(Predicate<? super Expense> predicate);

    // ================================ BUDGET =======================================

    boolean hasBudget(Budget budget);

    boolean hasBudgetWithName(Description targetDescription);

    Budget getPrimaryBudget();

    boolean hasPrimaryBudget();

    void addBudget(Budget budget);

    void switchBudgetTo(Description description);

    void deleteBudget(Budget target);

    void setBudget(Budget target, Budget editedBudget);

    void changePrimaryBudgetWindow(Timestamp pastDate);

    /** Returns an unmodifiable view of the filtered expense list */
    ObservableList<Budget> getFilteredBudgetList();

    void updateFilteredBudgetList(Predicate<? super Budget> budget);

    Predicate<? super Budget> getFilteredBudgetPredicate();

    void clearBudgets();

    void deleteBudgetWithName(Description description);

    // ================================ EVENT =======================================
    /** Returns an unmodifiable view of the filtered expense list */
    ObservableList<Event> getFilteredEventList();

    /** Returns the predicate of the filtered expense list */
    Predicate<? super Event> getFilteredEventPredicate();

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<? super Event> predicate);

    void notifyAboutTranspiredEvents(List<Event> events);

    boolean hasEvent(Event event);

    void addEvent(Event event);

    void deleteEvent(Event target);

    void setEvent(Event eventToEdit, Event editedEvent);

    // ================================ STATS =======================================

    Statistics getStatistics();

    void setStatistics(Statistics statistics);

    void handleAlreadyTranspiredEvents();
}
