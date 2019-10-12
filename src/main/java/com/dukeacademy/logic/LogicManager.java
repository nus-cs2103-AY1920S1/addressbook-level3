package com.dukeacademy.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.GuiSettings;
import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.parser.AddressBookParser;
import com.dukeacademy.logic.parser.exceptions.ParseException;
import com.dukeacademy.model.Model;
import com.dukeacademy.model.ReadOnlyQuestionBank;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.storage.Storage;

import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveQuestionBank(model.getQuestionBank());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyQuestionBank getQuestionBank() {
        return model.getQuestionBank();
    }

    @Override
    public ObservableList<Question> getFilteredPersonList() {
        return model.getFilteredQuestionList();
    }

    @Override
    public Path getQuestionBankFilePath() {
        return model.getQuestionBankFilePath();
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
