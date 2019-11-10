package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.MainParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserState;
import seedu.address.model.person.Person;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private final Storage storage;
    private final MainParser mainParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        mainParser = new MainParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = mainParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveUserState(model.getUserState());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyUserState getUserState() {
        return model.getUserState();
    }

    @Override
    public Path getUserStateFilePath() {
        return model.getUserStateFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<BankAccountOperation> getFilteredTransactionList() {
        return model.getFilteredTransactionList();
    }

    @Override
    public List<BankAccountOperation> getTransactionList() {
        return model.getTransactionList();
    }

    @Override
    public ObservableList<Budget> getBudgetList() {
        return model.getFilteredBudgetList();
    }

    @Override
    public ObservableList<LedgerOperation> getLedgerOperationsList() {
        return model.getFilteredLedgerOperationsList();
    }

    @Override
    public ObservableList<Projection> getProjectionList() {
        return model.getFilteredProjectionsList();
    }

    @Override
    public ObservableList<Person> getPeopleInLedger() {
        return model.getPeopleInLedger();
    }

}
