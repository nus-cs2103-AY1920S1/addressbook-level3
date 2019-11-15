package seedu.pluswork.logic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.pluswork.commons.core.GuiSettings;
import seedu.pluswork.commons.core.LogsCenter;
import seedu.pluswork.logic.MultiLine.MultiLineManager;
import seedu.pluswork.logic.autocomplete.AutoComplete;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.parser.ProjectDashboardParser;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ReadOnlyProjectDashboard;
import seedu.pluswork.model.calendar.Meeting;
import seedu.pluswork.model.calendar.MeetingQuery;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.mapping.TasMemMapping;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.settings.ClockFormat;
import seedu.pluswork.model.settings.Theme;
import seedu.pluswork.model.statistics.Statistics;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ProjectDashboardParser projectDashboardParser;
    private final MultiLineManager multiLine;
    private final AutoComplete autoComplete;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        projectDashboardParser = new ProjectDashboardParser();
        multiLine = new MultiLineManager(model);
        autoComplete = new AutoComplete(model);
    }

    @Override
    public CommandResult execute(String commandText)
            throws CommandException, ParseException, FileNotFoundException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = projectDashboardParser.parseCommand(commandText);
        commandResult = command.execute(model);

        CommandResult commandResultMl = multiLine.manage(commandResult, command);
        if (!commandResultMl.equals(new CommandResult("No MultiLine"))) {
            try {
                storage.saveProjectDashboard(model.getProjectDashboard());
                storage.saveUserSettings(model.getUserSettings());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
            return commandResultMl;
        }

        try {
            storage.saveProjectDashboard(model.getProjectDashboard());
            storage.saveUserSettings(model.getUserSettings());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyProjectDashboard getProjectDashboard() {
        return model.getProjectDashboard();
    }

    // Task

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTasksList();
    }

    @Override
    public ObservableList<Member> getFilteredMemberList() {
        return model.getFilteredMembersList();
    }

    @Override
    public ObservableList<Task> getFilteredTaskListNotStarted() {
        return model.getFilteredTaskListNotStarted();
    }

    @Override
    public ObservableList<Task> getFilteredTaskListDoing() {
        return model.getFilteredTaskListDoing();
    }

    @Override
    public ObservableList<Task> getFilteredTaskListDone() {
        return model.getFilteredTaskListDone();
    }

    @Override
    public ObservableList<Task> getFilteredTaskListByDeadline() {
        return model.getFilteredTaskListByDeadline();
    }

    @Override
    public ObservableList<Inventory> getFilteredInventoryList() {
        return model.getFilteredInventoriesList();
    }

    @Override
    public ObservableList<TasMemMapping> getFilteredTasMemMappingList() {
        return model.getFilteredTasMemMappingsList();
    }

    @Override
    public Path getProjectDashboardFilePath() {
        return model.getProjectDashboardFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public Statistics getStatistics() {
        return model.getStatistics();
    }

    @Override
    public ObservableList<Meeting> getFilteredMeetingList() {
        return model.getFilteredMeetingList();
    }

    @Override
    public MeetingQuery getMeetingQuery() {
        return model.getMeetingQuery();
    }

    @Override
    public Theme getCurrentTheme() {
        return model.getCurrentTheme();
    }

    @Override
    public ClockFormat getClockFormat() {
        return model.getCurrentClockFormat();
    }

    @Override
    public LinkedList<String> getAutoCompleteResults(String input) {
        return autoComplete.completeText(input);
    }

}
