package budgetbuddy.logic;

import budgetbuddy.commons.core.GuiSettings;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.logic.script.ScriptEnvironmentInitialiser;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.loan.Debtor;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.script.Script;
import budgetbuddy.model.transaction.Transaction;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Adds to the script environment.
     */
    void addToScriptEnvironment(ScriptEnvironmentInitialiser sei);

    /**
     * Returns an unmodifiable view of the list of accounts.
     */
    ObservableList<Account> getAccountList();

    /**
     * Returns an unmodifiable view of the list of transactions.
     */
    ObservableList<Transaction> getTransactionList();

    /**
     * Returns an unmodifiable view of the list of loans.
     */
    ObservableList<Loan> getFilteredLoanList();

    /**
     * Returns an unmodifiable view of the list of debtors.
     */
    SortedList<Debtor> getSortedDebtorList();

    /** Returns an unmodifiable view of the list of rules */
    ObservableList<Rule> getRuleList();

    /**
     * Returns an unmodifiable view of the list of scripts.
     */
    ObservableList<Script> getScriptList();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

}
