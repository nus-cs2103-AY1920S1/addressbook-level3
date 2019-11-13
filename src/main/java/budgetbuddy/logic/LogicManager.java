package budgetbuddy.logic;

import java.io.IOException;
import java.util.logging.Logger;

import budgetbuddy.commons.core.GuiSettings;
import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.parser.CommandLineParser;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.logic.script.ScriptEngine;
import budgetbuddy.logic.script.ScriptEnvironmentInitialiser;
import budgetbuddy.logic.script.ScriptModelBinding;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.loan.Debtor;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.script.Script;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.storage.Storage;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandLineParser commandLineParser;
    private final ScriptEngine scriptEngine;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        commandLineParser = new CommandLineParser();
        scriptEngine = new ScriptEngine(new ScriptModelBinding(model));
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = commandLineParser.parseCommand(commandText);
        commandResult = command.execute(model, scriptEngine);

        try {
            storage.save(model);
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public void addToScriptEnvironment(ScriptEnvironmentInitialiser sei) {
        scriptEngine.addToEnvironment(sei);
    }

    @Override
    public ObservableList<Account> getAccountList() {
        return model.getAccountsManager().getFilteredAccountList();
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return model.getFilteredTransactions();
    }

    @Override
    public ObservableList<Loan> getFilteredLoanList() {
        return model.getLoansManager().getFilteredLoans();
    }

    @Override
    public SortedList<Debtor> getSortedDebtorList() {
        return model.getLoansManager().getDebtors();
    }

    @Override
    public ObservableList<Rule> getRuleList() {
        return model.getRuleManager().getRules();
    }

    @Override
    public ObservableList<Script> getScriptList() {
        return model.getScriptLibrary().getScriptList();
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
