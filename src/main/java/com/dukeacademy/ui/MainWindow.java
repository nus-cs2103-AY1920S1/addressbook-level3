package com.dukeacademy.ui;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.CommandLogic;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandKeywordException;
import com.dukeacademy.logic.problemstatement.ProblemStatementLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.program.TestCaseResult;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Stage primaryStage;
    private final CommandLogic commandLogic;
    private final QuestionsLogic questionsLogic;
    private final ProgramSubmissionLogic programSubmissionLogic;
    private final ProblemStatementLogic problemStatementLogic;

    // Independent Ui parts residing in this Ui container
    private QuestionListPanel questionListPanel;
    private ResultDisplay resultDisplay;
    private final HelpWindow helpWindow;
    private Editor editorPanel;
    private CodeResultPanel codeResultPanel;
    private ProblemStatementPanel problemStatementPanel;
    private HomePage homePage;

    @FXML
    private StackPane problemStatementPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane questionListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private AnchorPane editorPlaceholder;

    @FXML
    private AnchorPane codeResultPanelPlaceholder;

    @FXML
    private HBox activityWindowPlaceholder;

    @FXML
    private AnchorPane homePagePlaceholder;

    /**
     * Instantiates a new Main window.
     *
     * @param primaryStage           the primary stage
     * @param commandLogic           the command logic
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     * @param problemStatementLogic  the problem statement logic
     */
    public MainWindow(Stage primaryStage, CommandLogic commandLogic, QuestionsLogic questionsLogic,
                      ProgramSubmissionLogic programSubmissionLogic,
                      ProblemStatementLogic problemStatementLogic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.commandLogic = commandLogic;
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
        this.problemStatementLogic = problemStatementLogic;

        // Configure the UI
        setWindowDefaultSize();

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    /**
     * Gets primary stage.
     *
     * @return the primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Updates the homepage placeholder residing in this main window.
     */
    private void updateHomePage() {
        homePage = new HomePage(questionsLogic.getFilteredQuestionsList());
        homePagePlaceholder.getChildren().add(homePage.getRoot());
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        questionListPanel = new QuestionListPanel(questionsLogic.getFilteredQuestionsList());
        questionListPanelPlaceholder.getChildren().add(questionListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter =
                new StatusBarFooter(Path.of("hello"));
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(
            commandText -> executeCommand(commandText));
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        editorPanel = new Editor(programSubmissionLogic.getCurrentQuestionObservable());
        editorPlaceholder.getChildren().add(editorPanel.getRoot());
        programSubmissionLogic.setUserProgramSubmissionChannel(editorPanel::getUserProgram);

        List<TestCaseResult> sampleTestCaseResults = new ArrayList<>();
        sampleTestCaseResults.add(
                TestCaseResult.getSuccessfulTestCaseResult("3", "Fizz"));
        sampleTestCaseResults.add(
                TestCaseResult.getFailedTestCaseResult("25", "Buzz", "FizzBuzz"));
        sampleTestCaseResults.add(
                TestCaseResult.getSuccessfulTestCaseResult("15", "FizzBuzz"));

        codeResultPanel = new CodeResultPanel(programSubmissionLogic.getTestResultObservable());
        codeResultPanelPlaceholder.getChildren().add(codeResultPanel.getRoot());

        problemStatementPanel = new ProblemStatementPanel(
            problemStatementLogic.getProblemStatementObservable());
        problemStatementPlaceholder.getChildren().add(problemStatementPanel.getRoot());

        updateHomePage();
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize() {
        primaryStage.setFullScreen(true);
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML private void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Show.
     */
    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Gets person list panel.
     *
     * @return the person list panel
     */
    public QuestionListPanel getPersonListPanel() {
        return questionListPanel;
    }

    /**
     * Gets editor panel.
     *
     * @return the editor panel
     */
    public Editor getEditorPanel() {
        return editorPanel;
    }

    /**
     * Gets run code result panel.
     *
     * @return the run code result panel
     */
    public CodeResultPanel getRunCodeResultPanel() {
        return codeResultPanel;
    }

    /**
     * Gets home page.
     *
     * @return the home page
     */
    public HomePage getHomePage() {
        return homePage;
    }

    /**
     * Executes the command and returns the result.
     */
    private CommandResult executeCommand(String commandText) throws CommandException, InvalidCommandKeywordException,
            InvalidCommandArgumentsException {
        try {
            CommandResult commandResult = commandLogic.executeCommand(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isView()) {
                problemStatementPanel.setProblemStatement(questionsLogic
                    .getProblemStatement());
            }

            if (commandResult.isBookmark()) {
                updateHomePage();
            }

            if (commandResult.isAttempt()) {
                updateHomePage();
            }

            return commandResult;
        } catch (CommandException | InvalidCommandArgumentsException | InvalidCommandKeywordException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
