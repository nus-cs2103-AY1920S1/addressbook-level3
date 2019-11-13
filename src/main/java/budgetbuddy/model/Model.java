package budgetbuddy.model;

import java.util.function.Predicate;

import budgetbuddy.commons.core.GuiSettings;
import budgetbuddy.model.transaction.Transaction;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Returns the loans manager.
     */
    LoansManager getLoansManager();

    /**
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
     * Resets the list of filtered transactions
     */
    void resetFilteredTransactionList();

    /**
     * Returns the accounts manager.
     */
    AccountsManager getAccountsManager();

}
