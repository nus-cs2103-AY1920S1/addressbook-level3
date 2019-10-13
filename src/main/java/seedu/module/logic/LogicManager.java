package seedu.module.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.module.commons.core.GuiSettings;
import seedu.module.commons.core.LogsCenter;
import seedu.module.logic.commands.Command;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.logic.parser.ModuleBookParser;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.Model;
import seedu.module.model.module.Module;
import seedu.module.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ModuleBookParser moduleBookParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        moduleBookParser = new ModuleBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = moduleBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveModuleBook(model.getModuleBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ObservableList<Module> getDisplayedList() {
        return model.getDisplayedList();
    }

    @Override
    public Path getModuleBookFilePath() {
        return model.getModuleBookFilePath();
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
