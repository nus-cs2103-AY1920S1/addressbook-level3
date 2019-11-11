package seedu.revision.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import seedu.revision.commons.core.GuiSettings;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.Logic;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.ReadOnlyRevisionTool;
import seedu.revision.model.RevisionTool;
import seedu.revision.model.quiz.GraphList;
import seedu.revision.model.quiz.Mode;
import seedu.revision.model.util.SampleDataUtil;
import seedu.revision.ui.answerables.AnswerableListPanel;
import seedu.revision.ui.statistics.GraphListPanel;

/**
 * The History Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed,
 * especially for generating line graphs.
 */
public class HistoryWindow extends ParentWindow {

    protected static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private GraphList graphList;

    /**
     * Initialises the GUI when History is called.
     * @param primaryStage the stage where scenes can be added to.
     * @param logic the logic that will be used to drive the app.
     */
    public HistoryWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage, logic);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

        graphList = new GraphList((logic.getStatisticsList()));
        graphListPanel = new GraphListPanel(graphList.getGraphList());
        answerableListPanelPlaceholder.getChildren().add(graphListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplay.setFeedbackToUser("History of all quiz attempts shown: \n" + logic.getStatisticsList()
                        + "\nYou have attempted " + logic.getStatisticsList().size() + " quizzes so far.");
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getRevisionToolFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, true);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Initialises the session for quiz. Loads the window components for quiz.
     * @throws CommandException
     */
    @FXML
    public void handleStart(Mode mode) throws CommandException {
        if (this.logic.getFilteredAnswerableList().size() > 0) {
            StartQuizWindow startQuizWindow = new StartQuizWindow(getPrimaryStage(), getLogic(), mode);
            startQuizWindow.show();
            startQuizWindow.fillInnerParts();
        } else {
            throw new CommandException("No questions were found matching that category/difficulty"
                    + "Quiz start aborted. Type 'list' to view your full list of questions again.");
        }
    }

    /**
     * Shows history of results as line graphs. Loads the window components for line graphs.
     * @throws CommandException
     */
    @FXML
    public void handleHistory() throws CommandException {
        if (this.logic.getStatisticsList().size() > 0) {
            HistoryWindow historyWindow = new HistoryWindow(getPrimaryStage(), getLogic());
            historyWindow.show();
            historyWindow.fillInnerParts();
        } else {
            throw new CommandException("No past results were found.");
        }
    }

    /**
     * Shows results of all quiz attempts. Loads the window components for results.
     * @throws CommandException
     */
    @FXML
    public void handleStats() throws CommandException {
        if (this.logic.getStatisticsList().size() > 0) {
            StatisticsWindow statisticsWindow = new StatisticsWindow(getPrimaryStage(), getLogic());
            statisticsWindow.show();
            statisticsWindow.fillInnerParts();
        } else {
            throw new CommandException("You have not attempted any quiz yet.");
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    protected void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Opens the restore window.
     */
    @FXML
    public void handleRestore(Model passedModel) {
        AlertDialog restoreAlert = AlertDialog.getRestoreAlert();
        Optional<ButtonType> result = restoreAlert.showAndWait();

        if (result.get() != restoreAlert.getNoButton()) {
            ReadOnlyRevisionTool sampleData;
            sampleData = SampleDataUtil.getSampleRevisionTool();
            passedModel.setRevisionTool(new RevisionTool(sampleData));
        }
    }

    public AnswerableListPanel getAnswerableListPanel() {
        return answerableListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    @Override
    protected CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        MainWindow mainWindow = new MainWindow(primaryStage, logic);
        mainWindow.show(); //This should be called before creating other UI parts
        mainWindow.fillInnerParts();
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }
            if (commandResult.isStart()) {
                handleStart(commandResult.getMode());
            }

            if (commandResult.isShowRestore()) {
                handleRestore(commandResult.getModel());
            }

            if (commandResult.isShowHistory()) {
                handleHistory();
            }

            if (commandResult.isShowStats()) {
                handleStats();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}

