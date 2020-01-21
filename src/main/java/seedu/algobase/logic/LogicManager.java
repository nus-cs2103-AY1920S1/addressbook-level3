package seedu.algobase.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
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
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.model.gui.TabManager;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.task.Task;
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
    private final CommandHistory history;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        algoBaseParser = new AlgoBaseParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        try {
            Command command = algoBaseParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        try {
            storage.saveAlgoBase(model.getAlgoBase());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }


        TabManager tabManager = getGuiState().getTabManager();
        tabManager.refreshTabManager();

        return commandResult;
    }

    @Override
    public ReadOnlyAlgoBase getAlgoBase() {
        return model.getAlgoBase();
    }

    @Override
    public GuiState getGuiState() {
        return model.getGuiState();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public ObservableList<Problem> getProcessedProblemList() {
        return model.getFilteredProblemList();
    }

    @Override
    public ObservableList<Tag> getProcessedTagList() {
        return model.getFilteredTagList();
    }

    @Override
    public ObservableList<Plan> getProcessedPlanList() {
        return model.getFilteredPlanList();
    }

    @Override
    public ObservableList<Task> getProcessedTaskList() {
        return model.getCurrentTaskList();
    }

    @Override
    public StringProperty getCurrentPlan() {
        return model.getCurrentPlan();
    }

    @Override
    public IntegerProperty getCurrentDoneCount() {
        return model.getCurrentDoneCount();
    }

    @Override
    public IntegerProperty getCurrentUndoneCount() {
        return model.getCurrentUndoneCount();
    }

    @Override
    public IntegerProperty getCurrentTaskCount() {
        return model.getCurrentTaskCount();
    }

    @Override
    public ObservableList<ProblemSearchRule> getProcessedFindRuleList() {
        return model.getFilteredFindRuleList();
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
