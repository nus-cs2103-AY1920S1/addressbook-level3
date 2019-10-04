package mams.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import mams.commons.core.GuiSettings;
import mams.commons.core.LogsCenter;
import mams.logic.commands.Command;
import mams.logic.commands.CommandResult;
import mams.logic.commands.exceptions.CommandException;
import mams.logic.parser.MamsParser;
import mams.logic.parser.exceptions.ParseException;
import mams.model.Model;
import mams.model.ReadOnlyMams;
import mams.model.person.Person;
import mams.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final MamsParser mamsParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        mamsParser = new MamsParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = mamsParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveMams(model.getMams());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyMams getMams() {
        return model.getMams();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getMamsFilePath() {
        return model.getMamsFilePath();
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
