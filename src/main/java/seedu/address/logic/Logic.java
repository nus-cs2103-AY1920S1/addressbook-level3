package seedu.address.logic;

import java.nio.file.Path;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserState;
import seedu.address.model.person.Person;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the BankAccount.
     *
     * @see Model#getBankAccount()
     */
    ReadOnlyUserState getUserState();

    /**
     * Returns the user prefs' bank account file path.
     */
    Path getUserStateFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns an unmodifiable view of the filtered list of transactions
     */
    ObservableList<BankAccountOperation> getFilteredTransactionList();

    /**
     * Returns an ObservableList of Transactions
     */
    List<BankAccountOperation> getTransactionList();

    ObservableList<Budget> getBudgetList();

    ObservableList<LedgerOperation> getLedgerOperationsList();

    ObservableList<Projection> getProjectionList();

    ObservableList<Person> getPeopleInLedger();
}
