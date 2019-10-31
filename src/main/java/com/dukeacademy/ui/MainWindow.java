package com.dukeacademy.ui;

import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.CommandLogic;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandKeywordException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.state.Activity;
import com.dukeacademy.model.state.ApplicationState;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
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

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;

    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private TabPane tabPane;

    @FXML
    private AnchorPane homePagePlaceholder;

    @FXML
    private AnchorPane questionsPagePlaceholder;

    @FXML
    private AnchorPane workspacePlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane statusbarPlaceholder;


    /**
     * Instantiates a new Main window.
     *
     * @param primaryStage           the primary stage
     * @param commandLogic           the command logic
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     */
    public MainWindow(Stage primaryStage, CommandLogic commandLogic, QuestionsLogic questionsLogic,
                      ProgramSubmissionLogic programSubmissionLogic, ApplicationState applicationState) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.commandLogic = commandLogic;
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;

        applicationState.getCurrentActivityObservable().addListener(this::selectTabFromActivity);

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
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        CommandBox commandBox = new CommandBox(
                this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        HomePage homePage = new HomePage(questionsLogic.getAllQuestionsList());
        homePagePlaceholder.getChildren().add(homePage.getRoot());

        QuestionsPage questionsPage = new QuestionsPage(questionsLogic.getFilteredQuestionsList(),
                questionsLogic.getSelectedQuestion());
        questionsPagePlaceholder.getChildren().add(questionsPage.getRoot());

        Workspace workspace = new Workspace(programSubmissionLogic.getCurrentQuestionObservable(),
                programSubmissionLogic.getTestResultObservable());
        workspacePlaceholder.getChildren().add(workspace.getRoot());

        programSubmissionLogic.setUserProgramSubmissionChannel(workspace.getUserProgramChannel());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize() {
        primaryStage.setMaximized(true);
    }


    /**
     * Show.
     */
    void show() {
        primaryStage.show();
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    private void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
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
     * Executes the command and returns the result.
     */
    private void executeCommand(String commandText) throws CommandException, InvalidCommandKeywordException,
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

        } catch (CommandException | InvalidCommandArgumentsException | InvalidCommandKeywordException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Helper method to select the correct tab based on the user's current activity.
     * @param activity the user's current activity
     */
    private void selectTabFromActivity(Activity activity) {
        if (activity == Activity.HOME) {
            this.tabPane.getSelectionModel().select(0);
        }

        if (activity == Activity.QUESTION) {
            this.tabPane.getSelectionModel().select(1);
        }

        if (activity == Activity.WORKSPACE) {
            this.tabPane.getSelectionModel().select(2);
        }
    }
}
