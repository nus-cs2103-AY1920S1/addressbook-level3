package com.typee.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import com.typee.commons.core.GuiSettings;
import com.typee.commons.core.LogsCenter;
import com.typee.commons.exceptions.DataConversionException;
import com.typee.logic.commands.CommandResult;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.InteractiveParser;
import com.typee.logic.interactive.parser.Parser;
import com.typee.logic.interactive.parser.exceptions.ParseException;
import com.typee.model.Model;
import com.typee.model.ReadOnlyEngagementList;
import com.typee.model.engagement.Engagement;
import com.typee.storage.Storage;
import com.typee.ui.Tab;

import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final InteractiveParser interactiveParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        interactiveParser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        interactiveParser.parseInput(commandText);
        commandResult = interactiveParser.fetchResult();
        if (interactiveParser.hasParsedCommand()) {
            commandResult = interactiveParser.makeCommand().execute(model);
        }

        try {
            storage.saveEngagementList(model.getEngagementList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyEngagementList getEngagementList() {
        return model.getEngagementList();
    }

    @Override
    public ObservableList<Engagement> getFilteredEngagementList() {
        return model.getFilteredEngagementList();
    }

    @Override
    public ObservableList<Engagement> getSortedEngagementList() {
        return model.getSortedEngagementList();
    }

    @Override
    public ObservableList<Tab> getTabList() throws DataConversionException {
        return storage.getTabList();
    }

    @Override
    public Path getEngagementListFilePath() {
        return model.getEngagementListFilePath();
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
