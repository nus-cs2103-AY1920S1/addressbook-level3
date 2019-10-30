package budgetbuddy.logic;

import budgetbuddy.commons.core.GuiSettings;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.loan.Debtor;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.rule.Rule;

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
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
