package thrift.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import thrift.commons.core.GuiSettings;
import thrift.commons.core.LogsCenter;
import thrift.logic.commands.Command;
import thrift.logic.commands.CommandResult;
import thrift.logic.commands.Undoable;
import thrift.logic.commands.UpdateCommand;
import thrift.logic.commands.exceptions.CommandException;
import thrift.logic.parser.ThriftParser;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.Model;
import thrift.model.ReadOnlyThrift;
import thrift.model.transaction.Transaction;
import thrift.storage.Storage;
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
    public CommandResult execute(String commandText, TransactionListPanel transactionListPanel)
            throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = thriftParser.parseCommand(commandText);
        if (command instanceof UpdateCommand) {
            commandResult = ((UpdateCommand) command).execute(model, transactionListPanel);
        } else {
            commandResult = command.execute(model);
        }
        if (command instanceof Undoable) {
            model.keepTrackCommands((Undoable) command);
            logger.info("[UNDOABLE COMMAND][" + commandText + "] is tracked");
        }
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
    public ObservableList<Transaction> getFilteredTransactionList() {
        return model.getFilteredTransactionList();
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
