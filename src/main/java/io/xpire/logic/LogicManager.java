package io.xpire.logic;

import static io.xpire.model.ListType.XPIRE;
import static io.xpire.model.state.StackManager.MAXIMUM_STATES;

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
import io.xpire.model.ListType;
import io.xpire.model.Model;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.history.UndoableHistoryManager;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import io.xpire.model.state.StackManager;
import io.xpire.model.state.StateManager;
import io.xpire.storage.Storage;
import javafx.collections.ObservableList;

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
    private final StateManager stateManager = new StackManager();
    private final UndoableHistoryManager<Command> commandHistory = new UndoableHistoryManager<>(MAXIMUM_STATES);
    private final UndoableHistoryManager<String> inputHistory = new UndoableHistoryManager<>(MAXIMUM_STATES);

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
            Command previousCommand = commandHistory.previous();
            String previousInput = inputHistory.previous();
            commandResult = new CommandResult(String.format(commandResult.getFeedbackToUser(),
                    previousCommand, previousInput));
        } else if (command instanceof RedoCommand) {
            Command nextCommand = commandHistory.next();
            String nextInput = inputHistory.next();
            commandResult = new CommandResult(String.format(commandResult.getFeedbackToUser(),
                    nextCommand, nextInput));
        } else if (command.isShowInHistory()) {
            commandHistory.save(command);
            inputHistory.save(commandText);
        }
        return commandResult;
    }

    //@@author liawsy
    @Override
    public ReadOnlyListView<? extends Item>[] getLists() {
        return this.model.getLists();
    }

    @Override
    public ObservableList<? extends Item> getCurrentFilteredItemList() {
        return this.model.getCurrentList();
    }

    @SuppressWarnings("unchecked")
    //@@author xiaoyu-nus
    @Override
    public ObservableList<XpireItem> getXpireItemList() {
        try {
            return (ObservableList<XpireItem>) this.model.getItemList(XPIRE);
        } catch (ClassCastException e) {
            this.logger.warning("Wrong item type for Xpire");
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    //@@author xiaoyu-nus
    @Override
    public ObservableList<Item> getReplenishItemList() {
        try {
            return (ObservableList<Item>) this.model.getItemList(ListType.REPLENISH);
        } catch (ClassCastException e) {
            this.logger.warning("Wrong item type for Replenish List");
            return null;
        }
    }

    //@@author
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
        return model.getCurrentView() == XPIRE;
    }
}
