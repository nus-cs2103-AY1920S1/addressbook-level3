package seedu.exercise.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.logic.commands.Command;
import seedu.exercise.logic.commands.CommandResult;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.parser.ExerciseBookParser;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.Model;
import seedu.exercise.model.ReadOnlyExerciseBook;
import seedu.exercise.model.ReadOnlyRegimeBook;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.regime.Regime;
import seedu.exercise.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ExerciseBookParser exerciseBookParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        exerciseBookParser = new ExerciseBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = exerciseBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveExerciseBook(model.getAllExerciseData());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        try {
            storage.saveRegimeBook(model.getAllRegimeData());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyExerciseBook getExerciseBook() {
        return model.getAllExerciseData();
    }

    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return model.getFilteredExerciseList();
    }

    @Override
    public ReadOnlyRegimeBook getRegimeBook() {
        return model.getAllRegimeData();
    }

    @Override
    public ObservableList<Regime> getFilteredRegimeList() {
        return model.getFilteredRegimeList();
    }

    @Override
    public Path getExerciseBookFilePath() {
        return model.getExerciseBookFilePath();
    }

    @Override
    public Path getRegimeBookFilePath() {
        return model.getRegimeBookFilePath();
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
