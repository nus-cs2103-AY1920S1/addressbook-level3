package budgetbuddy.model;

import java.util.function.Predicate;

import budgetbuddy.commons.core.GuiSettings;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Transaction;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Returns the loans manager.
     */
    LoansManager getLoansManager();

    void deleteTransaction(Transaction target);

    /*
     * Returns the rule manager.
     */
    RuleManager getRuleManager();

    /**
     * Returns the script library.
     *
     * @return the script library
     */
    ScriptLibrary getScriptLibrary();

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
     * Updates the filter of the filtered transaction list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList(Predicate<Transaction> predicate);

    /**
     * Returns an unmodifiable view of the filtered transaction list
     */
    ObservableList<Transaction> getFilteredTransactions();

    /**
     * Returns the accounts manager.
     */
    AccountsManager getAccountsManager();
}
