package seedu.algobase.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.logic.parser.AlgoBaseParser;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.Model;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AlgoBaseParser algoBaseParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        algoBaseParser = new AlgoBaseParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = algoBaseParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAlgoBase(model.getAlgoBase());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAlgoBase getAlgoBase() {
        return model.getAlgoBase();
    }

    @Override
    public ObservableList<Problem> getFilteredProblemList() {
        return model.getFilteredProblemList();
    }

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return model.getFilteredTagList();
    }

    @Override
    public Path getAlgoBaseFilePath() {
        return model.getAlgoBaseFilePath();
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
