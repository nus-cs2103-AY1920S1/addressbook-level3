package thrift.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import thrift.commons.core.GuiSettings;
import thrift.logic.commands.Command;
import thrift.logic.commands.CommandResult;
import thrift.logic.commands.exceptions.CommandException;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.Model;
import thrift.model.ReadOnlyThrift;
import thrift.model.transaction.Transaction;
import thrift.ui.BalanceBar;
import thrift.ui.FilteredBar;
import thrift.ui.TransactionListPanel;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @param transactionListPanel The TransactionListPanel to be manipulated by execution of certain commands.
     * @param balanceBar The BalanceBar that displays the current month, budget and balance.
     * @param filteredBar The FilteredBar that displays the meaning of the list.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText, TransactionListPanel transactionListPanel, BalanceBar balanceBar,
            FilteredBar filteredBar) throws CommandException, ParseException;

    /**
     * Returns the Thrift.
     *
     * @see Model#getThrift()
     */
    ReadOnlyThrift getThrift();

    /**
     * Processes the parsed command, checking if it requires scrolling the view, updating the balancebar, or record
     * keeping for possible undo/redo.
     *
     * @param command processed command that is ready to execute.
     * @param commandText raw user input for the command.
     * @param transactionListPanel transaction list pane that houses the transactions displayed to the user.
     * @param balanceBar GUI object that displays the current balance, budget and month to the user.
     * @param filteredBar GUI object that displays the meaning of the list.
     * @return {@code CommandResult} object that is created as a result from executing the command.
     * @throws CommandException if the command encounters any runtime errors.
     */
    CommandResult processParsedCommand(Command command, String commandText, TransactionListPanel transactionListPanel,
                                       BalanceBar balanceBar, FilteredBar filteredBar) throws CommandException;

    /**
     * Takes the given command and executes it, checking if the command requires scrolling the
     * {@code transactionListPanel} into view.
     *
     * @param command processed command that is ready to execute.
     * @param transactionListPanel transaction list pane that houses the transactions displayed to the user.
     * @return {@code CommandResult} object that is created as a result from executing the command.
     * @throws CommandException if the command encounters any runtime errors.
     */
    CommandResult parseScrollable(Command command, TransactionListPanel transactionListPanel)
            throws CommandException;

    /**
     * Checks if the given command requires the {@code BalanceBar} to be refreshed.
     *
     * @param command processed command that is ready to execute.
     * @param balanceBar GUI object that displays the current balance, budget and month to the user.
     */
    void parseRefreshable(Command command, BalanceBar balanceBar);

    /**
     * Checks if the given command requires to update {@code filteredBar}.
     *
     * @param input user input to the command box.
     * @param command processed command that is ready to execute.
     * @param filteredBar GUI object that displays the meaning of the list.
     */
    void parseFilterable(String input, Command command, FilteredBar filteredBar);

    /**
     * Checks if the given command requires to be record-kept for possible undo/redo in the future.
     *
     * @param command processed command that is ready to execute.
     * @param commandText raw user input for the command.
     */
    void parseUndoable(Command command, String commandText);

    /** Updates the various components of the {@code BalnaceBar}. */
    void updateBalanceBar(BalanceBar balanceBar);

    /** Returns if the given command requires a refresh of the {@code filteredList}. */
    boolean isRefreshingFilteredList(Command command);

    /** Returns the current month and year in MMM yyyy format. */
    String getCurrentMonthYear();

    /** Returns the current month's budget. */
    double getCurrentMonthBudget();

    /** Returns the current month's balance. */
    double getCurrentMonthBalance();

    /** Returns the current month's expense. */
    double getCurrentMonthExpense();

    /** Computes the current month's initial expense. */
    void computeInitialMonthExpense();

    /** Returns the current month's income. */
    double getCurrentMonthIncome();

    /** Computes the current month's initial income. */
    void computeInitialMonthIncome();

    /** Returns an unmodifiable view of the filtered list of transactions. */
    ObservableList<Transaction> getFilteredTransactionList();

    /** Filters the view of the transaction list to only show transactions that occur in the current month. */
    void setFilteredTransactionListToCurrentMonth();

    /**
     * Returns the user prefs' thrift file path.
     */
    Path getThriftFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
