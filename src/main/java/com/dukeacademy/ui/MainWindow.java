package com.dukeacademy.ui;

import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.CommandLogic;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandKeywordException;
import com.dukeacademy.logic.commands.tab.TabCommand;
import com.dukeacademy.logic.notes.NotesLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.state.Activity;
import com.dukeacademy.model.state.ApplicationState;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
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
    private final NotesLogic notesLogic;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;

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
    private AnchorPane notesPagePlaceholder;

    @FXML
    private AnchorPane helpPagePlaceholder;


    /**
     * Instantiates a new Main window.
     *
     * @param primaryStage           the primary stage
     * @param commandLogic           the command logic
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     */
    public MainWindow(Stage primaryStage, CommandLogic commandLogic, QuestionsLogic questionsLogic,
                      ProgramSubmissionLogic programSubmissionLogic, NotesLogic notesLogic,
                      ApplicationState applicationState) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.commandLogic = commandLogic;
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
        this.notesLogic = notesLogic;

        applicationState.getCurrentActivityObservable().addListener(this::selectTabFromActivity);
        tabPane.getSelectionModel().selectedIndexProperty().addListener(new TabChangeListener());

        // Configure the UI
        setWindowDefaultSize();
    }

    /**
     * Gets primary stage.
     *
     * @return the primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
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

        NotesPage notesPage = new NotesPage(notesLogic);
        notesPagePlaceholder.getChildren().add(notesPage.getRoot());

        HelpPage helpPage = new HelpPage();
        helpPagePlaceholder.getChildren().add(helpPage.getRoot());

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
     * Closes the application.
     */
    @FXML
    private void handleExit() {
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

        if (activity == Activity.NOTE) {
            this.tabPane.getSelectionModel().select(3);
        }

        if (activity == Activity.HELP) {
            this.tabPane.getSelectionModel().select(4);
        }
    }

    /**
     * Custom listener class to listen out to user's tab changes using mouse click.
     */
    private class TabChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if (newValue.intValue() == 0) {
                resultDisplay.setFeedbackToUser(TabCommand.FEEDBACK + Activity.HOME.toString());
            }

            if (newValue.intValue() == 1) {
                resultDisplay.setFeedbackToUser(TabCommand.FEEDBACK + Activity.QUESTION.toString());
            }

            if (newValue.intValue() == 2) {
                resultDisplay.setFeedbackToUser(TabCommand.FEEDBACK + Activity.WORKSPACE.toString());
            }

            if (newValue.intValue() == 3) {
                resultDisplay.setFeedbackToUser(TabCommand.FEEDBACK + Activity.NOTE.toString());
            }

            if (newValue.intValue() == 4) {
                resultDisplay.setFeedbackToUser(TabCommand.FEEDBACK + Activity.HELP.toString());
            }
        }
    }
}
