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
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;
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
            saveAllData();
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyResourceBook<Exercise> getExerciseBook() {
        return model.getExerciseBookData();
    }

    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return model.getFilteredExerciseList();
    }

    @Override
    public ReadOnlyResourceBook<Regime> getRegimeBook() {
        return model.getAllRegimeData();
    }

    @Override
    public ObservableList<Regime> getFilteredRegimeList() {
        return model.getFilteredRegimeList();
    }

    public ObservableList<Schedule> getFilteredScheduleList() {
        return model.getFilteredScheduleList();
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
    public ObservableList<Exercise> getSuggestedExerciseList() {
        return model.getSuggestedExerciseList();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    /**
     * Saves all book data from ExerHealth to disk
     *
     * @throws IOException if saving fails
     */
    private void saveAllData() throws IOException {
        storage.saveExerciseBook(model.getExerciseBookData());
        storage.saveScheduleBook(model.getAllScheduleData());
        storage.saveRegimeBook(model.getAllRegimeData());
        storage.savePropertyBook(model.getPropertyBook());
    }
}
