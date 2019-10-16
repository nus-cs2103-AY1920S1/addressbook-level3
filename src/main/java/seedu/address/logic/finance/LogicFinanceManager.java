package seedu.address.logic.finance;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.finance.commands.Command;
import seedu.address.logic.finance.commands.CommandResult;
import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.logic.finance.parser.FinanceLogParser;
import seedu.address.logic.finance.parser.exceptions.ParseException;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.ReadOnlyFinanceLog;
import seedu.address.model.finance.logEntry.LogEntry;
import seedu.address.storage.finance.Storage;


/**
 * The LogicManager for finance feature of the app.
 */
public class LogicFinanceManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicFinanceManager.class);

    private final Model model;
    private final Storage storage;
    private final FinanceLogParser financeLogParser;

    public LogicFinanceManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        financeLogParser = new FinanceLogParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        //Logging, safe to ignore
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        //Parse user input from String to a Command
        Command command = financeLogParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            //We can deduce that the previous line of code modifies model in some way
            // since it's being stored here.
            storage.saveFinanceLog(model.getFinanceLog());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyFinanceLog getAddressBook() {
        return model.getFinanceLog();
    }

    @Override
    public ObservableList<LogEntry> getFilteredPersonList() {
        return model.getFilteredLogEntryList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getFinanceLogFilePath();
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
