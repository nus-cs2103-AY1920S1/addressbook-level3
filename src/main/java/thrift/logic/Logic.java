package thrift.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import thrift.commons.core.GuiSettings;
import thrift.logic.commands.CommandResult;
import thrift.logic.commands.exceptions.CommandException;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.Model;
import thrift.model.ReadOnlyThrift;
import thrift.model.transaction.Transaction;
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
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText, TransactionListPanel transactionListPanel)
            throws CommandException, ParseException;

    /**
     * Returns the Thrift.
     *
     * @see Model#getThrift()
     */
    ReadOnlyThrift getThrift();

    /** Returns an unmodifiable view of the filtered list of transactions*/
    ObservableList<Transaction> getFilteredTransactionList();

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
