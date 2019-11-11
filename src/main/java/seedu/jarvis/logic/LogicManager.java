package seedu.jarvis.logic;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.beans.value.ObservableStringValue;
import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.parser.JarvisParser;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final JarvisParser jarvisParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        jarvisParser = new JarvisParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = jarvisParser.parseCommand(commandText);
        commandResult = command.execute(model);
        // updates model after a successful command execution.
        updateModel(command);
        // saves the model to local storage.
        saveModel();

        return commandResult;
    }

    /**
     * Adds {@code Command} to {@code Model} if it is invertible, so that model can remember it for undo and redo
     * operations.
     *
     * @param command {@code Command} to be checked for having an inverse before it being added to {@code Model} if
     *                               necessary.
     */
    private void updateModel(Command command) {
        if (!command.hasInverseExecution()) {
            return;
        }
        model.rememberExecutedCommand(command);
    }

    /**
     * Saves the Jarvis to local storage.
     * Saves the history manager to local storage.
     *
     * @throws CommandException If there was an {@code IOException} when saving a component to local storage.
     */
    private void saveModel() throws CommandException {
        try {
            storage.saveHistoryManager(model.getHistoryManager());
            storage.saveCcaTracker(model.getCcaTracker());
            storage.saveCoursePlanner(model.getCoursePlanner());
            storage.savePlanner(model.getPlanner());
            storage.saveFinanceTracker(model.getFinanceTracker());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    public ObservableList<Command> getExecutedCommandsList() {
        return model.getExecutedCommandsList();
    }

    @Override
    public ObservableList<Command> getInverselyExecutedCommandsList() {
        return model.getInverselyExecutedCommandsList();
    }

    // Planner ===========================================================================

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<Task> getUnfilteredTaskList() {
        return model.getUnfilteredTaskList();

    }

    @Override
    public ObservableList<Task> getTasksToday() {
        return model.getTasksToday();
    }

    @Override
    public ObservableList<Task> getTasksThisWeek() {
        return model.getTasksThisWeek();
    }


    // Course Planner ====================================================================

    @Override
    public ObservableList<Course> getUnfilteredCourseList() {
        return model.getUnfilteredCourseList();
    }

    @Override
    public ObservableStringValue getCourseTextDisplay() {
        return model.getDisplayText();
    }
}
