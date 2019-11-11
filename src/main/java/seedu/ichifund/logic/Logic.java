package seedu.ichifund.logic;

import java.nio.file.Path;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.ichifund.commons.core.GuiSettings;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.analytics.Data;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.context.TransactionContext;
import seedu.ichifund.model.loan.Loan;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.transaction.Transaction;

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
     * Executes all the tasks.
     */
    void executeAllTasks();

    /**
     * Returns an unmodifiable version of the index of the current parser manager.
     */
    ObservableValue<Integer> getCurrentFeatureParserIndex();

    /**
     * Sets the current parser manager according to the given index.
     */
    void setFeatureParser(int index);

    /**
     * Returns the FundBook.
     *
     * @see seedu.ichifund.model.Model#getFundBook()
     */
    ReadOnlyFundBook getFundBook();

    /** Returns an unmodifiable view of the filtered list of transactions */
    ObservableList<Transaction> getFilteredTransactionList();

    /** Returns an unmodifiable view of the filtered list of repeaters */
    ObservableList<Repeater> getFilteredRepeaterList();

    /** Returns an unmodifiable view of the filtered list of repeaters */
    ObservableList<Loan> getFilteredLoanList();

    /** Returns an unmodifiable view of the filtered list of budgets */
    ObservableList<Budget> getFilteredBudgetList();

    /** Returns the current analytics view */
    ObservableList<Data> getDataList();

    /**
     * Returns the user prefs' fund book file path.
     */
    Path getFundBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    ObservableValue<TransactionContext> getTransactionContextProperty();
}
