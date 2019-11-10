package seedu.ichifund.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.ichifund.commons.core.GuiSettings;
import seedu.ichifund.commons.core.LogsCenter;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.logic.parser.IchiFundParser;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.logic.tasks.TaskManager;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.analytics.Data;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.context.TransactionContext;
import seedu.ichifund.model.loan.Loan;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final IchiFundParser ichiFundParser;
    private final TaskManager taskManager;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        ichiFundParser = new IchiFundParser();
        taskManager = new TaskManager();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = ichiFundParser.parseCommand(commandText);
        commandResult = command.execute(model);
        taskManager.executeAll(model);

        try {
            storage.saveFundBook(model.getFundBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }


    public void executeAllTasks() {
        taskManager.executeAll(model);
    }

    @Override
    public ObservableValue<Integer> getCurrentFeatureParserIndex() {
        return ichiFundParser.getCurrentFeatureParserIndex();
    }

    @Override
    public void setFeatureParser(int index) {
        ichiFundParser.setFeatureParser(index);
    }

    @Override
    public ObservableValue<TransactionContext> getTransactionContextProperty() {
        return model.getTransactionContextProperty();
    }

    @Override
    public ReadOnlyFundBook getFundBook() {
        return model.getFundBook();
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return model.getFilteredTransactionList();
    }

    @Override
    public ObservableList<Repeater> getFilteredRepeaterList() {
        return model.getFilteredRepeaterList();
    }

    @Override
    public ObservableList<Loan> getFilteredLoanList() {
        return model.getFilteredLoanList();
    }

    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        return model.getFilteredBudgetList();
    }

    @Override
    public ObservableList<Data> getDataList() {
        return model.getDataList();
    }

    @Override
    public Path getFundBookFilePath() {
        return model.getFundBookFilePath();
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
