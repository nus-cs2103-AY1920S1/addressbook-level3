package io.xpire.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import io.xpire.commons.core.GuiSettings;
import io.xpire.commons.core.LogsCenter;
import io.xpire.logic.commands.Command;
import io.xpire.logic.commands.CommandResult;
import io.xpire.logic.commands.RedoCommand;
import io.xpire.logic.commands.UndoCommand;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.logic.parser.Parser;
import io.xpire.logic.parser.ReplenishParser;
import io.xpire.logic.parser.XpireParser;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.Model;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.history.CommandHistory;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import io.xpire.model.state.StateManager;
import io.xpire.storage.Storage;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private Parser parser;
    private final XpireParser xpireParser = new XpireParser();
    private final ReplenishParser replenishParser = new ReplenishParser();
    private final StateManager stateManager = new StateManager();
    private final CommandHistory commandHistory = new CommandHistory();

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.parser = xpireParser;
    }

    //@@author febee99
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        if (isXpireListView()) {
            this.parser = xpireParser;
        } else {
            this.parser = replenishParser;
        }
        Command command = this.parser.parse(commandText);
        commandResult = command.execute(this.model, this.stateManager);

        try {
            this.storage.saveList(this.model.getLists());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        if (command instanceof UndoCommand) {
            Command previousCommand = commandHistory.retrievePreviousCommand();
            commandResult = new CommandResult(String.format(commandResult.getFeedbackToUser(), previousCommand));
        } else if (command instanceof RedoCommand) {
            Command nextCommand = commandHistory.retrieveNextCommand();
            commandResult = new CommandResult(String.format(commandResult.getFeedbackToUser(), nextCommand));
        } else if (command.isShowInHistory()) {
            commandHistory.addCommand(command);
        }
        return commandResult;
    }

    //@@author liawsy
    @Override
    public ReadOnlyListView<? extends Item>[] getLists() {
        return this.model.getLists();
    }

    @Override
    public FilteredList<? extends Item> getCurrentFilteredItemList() {
        return this.model.getCurrentFilteredItemList();
    }

    @Override
    public ObservableList<XpireItem> getXpireItemList() {
        return this.model.getFilteredXpireItemList();
    }

    @Override
    public ObservableList<Item> getReplenishItemList() {
        return this.model.getFilteredReplenishItemList();
    }

    @Override
    public Path getListFilePath() {
        return this.model.getListFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return this.model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        this.model.setGuiSettings(guiSettings);
    }

    private boolean isXpireListView() {
        return this.model.getCurrentFilteredItemList().equals(this.model.getFilteredXpireItemList());
    }
}
