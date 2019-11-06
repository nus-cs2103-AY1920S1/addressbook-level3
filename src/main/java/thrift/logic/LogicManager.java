package thrift.logic;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import thrift.commons.core.GuiSettings;
import thrift.commons.core.LogsCenter;
import thrift.logic.commands.AddExpenseCommand;
import thrift.logic.commands.AddIncomeCommand;
import thrift.logic.commands.BudgetCommand;
import thrift.logic.commands.CloneCommand;
import thrift.logic.commands.Command;
import thrift.logic.commands.CommandResult;
import thrift.logic.commands.DeleteCommand;
import thrift.logic.commands.FindCommand;
import thrift.logic.commands.ListCommand;
import thrift.logic.commands.NonScrollingCommand;
import thrift.logic.commands.RedoCommand;
import thrift.logic.commands.ScrollingCommand;
import thrift.logic.commands.UndoCommand;
import thrift.logic.commands.Undoable;
import thrift.logic.commands.UpdateCommand;
import thrift.logic.commands.exceptions.CommandException;
import thrift.logic.parser.ThriftParser;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.Model;
import thrift.model.ReadOnlyThrift;
import thrift.model.transaction.Transaction;
import thrift.storage.Storage;
import thrift.ui.BalanceBar;
import thrift.ui.FilteredBar;
import thrift.ui.TransactionListPanel;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ThriftParser thriftParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        thriftParser = new ThriftParser();
    }

    @Override
    public CommandResult execute(String commandText, TransactionListPanel transactionListPanel, BalanceBar balanceBar,
            FilteredBar filteredBar) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = thriftParser.parseCommand(commandText);
        CommandResult commandResult = processParsedCommand(command, commandText, transactionListPanel, balanceBar,
                filteredBar);
        try {
            storage.saveThrift(model.getThrift());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyThrift getThrift() {
        return model.getThrift();
    }

    @Override
    public CommandResult processParsedCommand(Command command, String commandText,
                                              TransactionListPanel transactionListPanel,
                                              BalanceBar balanceBar, FilteredBar filteredBar) throws CommandException {
        requireAllNonNull(command, commandText);
        CommandResult commandResult = parseScrollable(command, transactionListPanel);
        assert commandResult != null;
        parseRefreshable(command, balanceBar);
        parseFilterable(commandText, command, filteredBar);
        parseUndoable(command, commandText);
        return commandResult;
    }

    @Override
    public CommandResult parseScrollable(Command command, TransactionListPanel transactionListPanel)
            throws CommandException {
        requireNonNull(command);
        CommandResult commandResult;
        if (command instanceof ScrollingCommand) {
            commandResult = ((ScrollingCommand) command).execute(model, transactionListPanel);
        } else {
            commandResult = ((NonScrollingCommand) command).execute(model);
        }
        return commandResult;
    }

    @Override
    public void parseRefreshable(Command command, BalanceBar balanceBar) {
        requireNonNull(command);
        if (isRefreshingFilteredList(command)) {
            model.updateBalanceForCurrentMonth();
            model.updateExpenseForCurrentMonth();
            model.updateIncomeForCurrentMonth();
            updateBalanceBar(balanceBar);
        } else {
            assert isRefreshingFilteredList(command) == false;
            logger.info("[ " + command.getClass().getSimpleName()
                    + " IS NOT UPDATING MONTHLY BALANCE OR BUDGET]");
        }
    }

    @Override
    public void parseFilterable(String input, Command command, FilteredBar filteredBar) {
        requireAllNonNull(input, command);
        try {
            String arguments = thriftParser.getArguments(input).trim();
            if (command instanceof ListCommand) {
                if (arguments.equals("")) {
                    filteredBar.setFiltered("All");
                } else {
                    String monthYear = model.getCurrentMonthYear();
                    filteredBar.setFiltered(monthYear);
                }
            } else if (command instanceof FindCommand) {
                String filteredString = formatFindCommandArguments(arguments);
                filteredBar.setFiltered(filteredString);
            }
        } catch (ParseException e) {
            logger.severe("Unable to parse the user input at LogicManager#parseFilterable");
        }
    }

    /**
     * Formats arguments for find command into human-readable format. <br>
     * Note: arguments should not be empty.
     *
     * @param arguments is the arguments to find command.
     * @return a human-readable string for find command.
     */
    private String formatFindCommandArguments(String arguments) {
        assert !arguments.equals("");
        String[] keywords = arguments.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keywords.length; i++) {
            if (i == keywords.length - 2) {
                sb.append("\"").append(keywords[i]).append("\"").append(" or ");
            } else if (i == keywords.length - 1) {
                sb.append("\"").append(keywords[i]).append("\"");
            } else {
                sb.append("\"").append(keywords[i]).append("\"").append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public void parseUndoable(Command command, String commandText) {
        requireAllNonNull(command, commandText);
        if (command instanceof Undoable) {
            model.keepTrackCommands((Undoable) command);
            logger.info("[UNDOABLE COMMAND][" + commandText + "] is tracked");
        }
    }

    @Override
    public void updateBalanceBar(BalanceBar balanceBar) {
        // Return on null for testing as JavaFX components cannot be initialized during testing
        if (balanceBar == null) {
            return;
        }
        balanceBar.setMonthYear(getCurrentMonthYear());
        balanceBar.setMonthBudget(getCurrentMonthBudget());
        balanceBar.setMonthBalance(getCurrentMonthBalance());
        balanceBar.setMonthExpense(getCurrentMonthExpense());
        balanceBar.setMonthIncome(getCurrentMonthIncome());
    }

    @Override
    public boolean isRefreshingFilteredList(Command command) {
        requireNonNull(command);
        if (command instanceof AddIncomeCommand
                || command instanceof AddExpenseCommand
                || command instanceof BudgetCommand
                || command instanceof CloneCommand
                || command instanceof DeleteCommand
                || command instanceof ListCommand
                || command instanceof RedoCommand
                || command instanceof UpdateCommand
                || command instanceof UndoCommand) {
            logger.info("[PREPARING TO UPDATE MONTHLY BALANCE OR BUDGET]");
            return true;
        }
        return false;
    }

    @Override
    public double getCurrentMonthBudget() {
        return model.getCurrentMonthBudget();
    }

    @Override
    public String getCurrentMonthYear() {
        return model.getCurrentMonthYear();
    }

    @Override
    public double getCurrentMonthBalance() {
        return model.getBalance();
    }

    @Override
    public double getCurrentMonthExpense() {
        return model.getExpense();
    }

    @Override
    public void computeInitialMonthExpense() {
        model.updateExpenseForCurrentMonth();
    }

    @Override
    public double getCurrentMonthIncome() {
        return model.getIncome();
    }

    @Override
    public void computeInitialMonthIncome() {
        model.updateIncomeForCurrentMonth();
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return model.getFilteredTransactionList();
    }

    @Override
    public void setFilteredTransactionListToCurrentMonth() {
        model.updateFilteredTransactionListToCurrentMonth();
    }

    @Override
    public Path getThriftFilePath() {
        return model.getThriftFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
