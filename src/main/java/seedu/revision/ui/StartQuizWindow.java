package seedu.revision.ui;

import static seedu.revision.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.MainLogic;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.TrueFalse;
import seedu.revision.model.quiz.Mode;
import seedu.revision.ui.answerables.AnswerableListPanel;
import seedu.revision.ui.answers.AnswersGridPane;
import seedu.revision.ui.answers.McqAnswersGridPane;
import seedu.revision.ui.answers.SaqAnswersGridPane;
import seedu.revision.ui.answers.TfAnswersGridPane;
import seedu.revision.ui.bar.ProgressIndicatorBar;
import seedu.revision.ui.bar.ScoreProgressAndTimerGridPane;
import seedu.revision.ui.bar.Timer;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class StartQuizWindow extends Window {

    private final Logger logger = LogsCenter.getLogger(getClass());

    private MainWindow mainWindow;
    private ObservableList<Answerable> quizList;
    private Mode mode;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay questionDisplay;
    private AnswersGridPane answersGridPane;
    private CommandBox commandBox;
    private ProgressIndicatorBar progressIndicatorBar;
    private Timer timer;
    private ScoreProgressAndTimerGridPane progressAndTimerGridPane;

    private Answerable previousAnswerable;
    private Answerable currentAnswerable;
    private Iterator<Answerable> answerableIterator;
    private int score = 0;

    private ReadOnlyDoubleWrapper currentProgressIndex = new ReadOnlyDoubleWrapper(
            this, "currentProgressIndex", 0);

    public StartQuizWindow(Stage primaryStage, MainLogic mainLogic, Mode mode) {
        super(primaryStage, mainLogic);
        this.mode = mode;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        this.quizList = getListBasedOnMode(this.mode);
        answerableIterator = quizList.iterator();
        currentAnswerable = answerableIterator.next();

        setAnswerGridPaneByType(currentAnswerable);

        questionDisplay = new ResultDisplay();
        questionDisplay.setFeedbackToUser(currentAnswerable.getQuestion().toString());
        resultDisplayPlaceholder.getChildren().add(questionDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(mainLogic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());


        this.timer = getTimerBasedOnMode(this.mode, Integer.parseInt(quizList.get(0).getDifficulty().value));
        int sizeOfFirstLevel = getSizeOfCurrentLevel(quizList.get(0));
        progressIndicatorBar = new ProgressIndicatorBar(currentProgressIndex, sizeOfFirstLevel,
                "%.0f/" + sizeOfFirstLevel);

        progressAndTimerGridPane = new ScoreProgressAndTimerGridPane(progressIndicatorBar, timer);
        scoreProgressAndTimerPlaceholder.getChildren().add(progressAndTimerGridPane.getRoot());
    }

    private int getSizeOfCurrentLevel(Answerable answerable) {
        ObservableList<Answerable> sectionList = quizList.filtered(a ->
                a.getDifficulty().value.equals(answerable.getDifficulty().value));
        logger.info("section size: " + sectionList.size());
        return sectionList.size();
    }

    private void setAnswerGridPaneByType(Answerable currentAnswerable) {
        if (currentAnswerable instanceof Mcq) {
            answersGridPane = new McqAnswersGridPane(currentAnswerable);
        } else if (currentAnswerable instanceof TrueFalse) {
            answersGridPane = new TfAnswersGridPane(currentAnswerable);
        } else {
            answersGridPane = new SaqAnswersGridPane(currentAnswerable);
        }
        answerableListPanelPlaceholder.getChildren().add(answersGridPane.getRoot());
    }

    /**
     * Executes the command and returns the result.
     *
     * @see MainLogic#execute(String, Answerable)
     */
    @Override
    protected CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        timer.resetTimer();
        try {
            CommandResult commandResult = mainLogic.execute(commandText, currentAnswerable);
            logger.info("Question result: " + commandResult.getFeedbackToUser());
            if (commandResult.getFeedbackToUser().equalsIgnoreCase("correct")) {
                // TODO: KhiangLeon use the updateStatistics() method here or in McqInputCommand#execute.
                //  Both has access to the answerable.
                score++;
            }

            if (commandResult.isExit()) {
                handleExit();
                return new CommandResult().withFeedBack("Quiz has ended.").build();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.getFeedbackToUser().equalsIgnoreCase("wrong")
                    && mode.value.equals("arcade")) {
                handleEnd();
                return new CommandResult().withFeedBack("Quiz has ended.").build();
            }

            if (!answerableIterator.hasNext()) {
                handleEnd();
                return new CommandResult().withFeedBack("Quiz has ended.").build();
            }

            currentProgressIndex.set(getCurrentProgressIndex() + 1);

            previousAnswerable = currentAnswerable;
            currentAnswerable = answerableIterator.next();

            if (previousAnswerable != null && answerableIterator.hasNext()) {
                int previousLevel = Integer.parseInt(previousAnswerable.getDifficulty().value);
                int currentLevel = Integer.parseInt(currentAnswerable.getDifficulty().value);
                if (previousLevel < currentLevel) {
                    handleNextLevel(currentAnswerable);
                }
            }

            answerableListPanelPlaceholder.getChildren().remove(answersGridPane.getRoot());
            setAnswerGridPaneByType(currentAnswerable);
            answersGridPane.updateAnswers(currentAnswerable);

            questionDisplay.setFeedbackToUser(currentAnswerable.getQuestion().toString());

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            questionDisplay.setFeedbackToUser(currentAnswerable.getQuestion().toString() + "\n\n" + e.getMessage());
            throw e;
        }
    }

    /**
     * Handles progression to the next level and receives response from the user.
     * @param nextAnswerable next answerable that will be displayed.
     */
    @FXML
    private void handleNextLevel(Answerable nextAnswerable) {
        int nextLevel = Integer.parseInt(nextAnswerable.getDifficulty().value);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.setTitle("Well done!");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("You have completed level " + (nextLevel - 1) + "\n"
                + "Your current score is: " + score + "/" + mainLogic.getFilteredAnswerableList().size() + "\n"
                + "Would you like to proceed to level " + nextLevel + "?\n"
                + "Press [ENTER] to proceed.\n"
                + "Press [ESC] to return to main screen.");

        ButtonType tryAgainButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType endButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(tryAgainButton, endButton);

        Task<Void> task = new Task<>() {
            @Override
            public Void call() throws Exception {
                timer.stopTimer();
                logger.info("---------Timer Stopped!!!!----------");
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            nextLevelHelper(alert, endButton, nextAnswerable, nextLevel);
        });
        new Thread(task).start();
    }

    /**
     * Helper function for the nextLevel method used to show the alert and get user input.
     * @param alert alert that will be shown to the user.
     * @param endButton button that will end the quiz.
     * @param nextAnswerable the next answerable in the quiz.
     * @param nextLevel the next level in the quiz.
     */
    private void nextLevelHelper(Alert alert, ButtonType endButton, Answerable nextAnswerable, int nextLevel) {
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == endButton) {
            handleExit();
        } else {
            currentProgressIndex.set(0);
            progressIndicatorBar = new ProgressIndicatorBar(currentProgressIndex,
                    getSizeOfCurrentLevel(nextAnswerable),
                    "%.0f/" + getSizeOfCurrentLevel(nextAnswerable));
            Timer newTimer = getTimerBasedOnMode(this.mode, nextLevel);
            timer = newTimer;
            progressAndTimerGridPane = new ScoreProgressAndTimerGridPane(progressIndicatorBar, timer);
            scoreProgressAndTimerPlaceholder.getChildren().add(progressAndTimerGridPane.getRoot());
        }
    }

    /**
     * Handles ending of quiz session.
     */
    @FXML
    private void handleEnd() {
        currentProgressIndex.set(currentProgressIndex.get() + 1);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("End of Quiz!");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        if (mode.value.equals("arcade") && answerableIterator.hasNext()) {
            alert.setContentText("Better luck next time! :P Your score is " + score
                    + "/" + mainLogic.getFilteredAnswerableList().size() + "\n"
                    + "Try again?\n"
                    + "Press [ENTER] to try again.\n"
                    + "Press [ESC] to return to main screen.");
        } else {
            alert.setContentText("Quiz has ended! Your final score is " + score
                    + "/" + mainLogic.getFilteredAnswerableList().size() + "\n"
                    + "Try again?\n"
                    + "Press [ENTER] to try again.\n"
                    + "Press [ESC] to return to main screen.");
        }

        ButtonType tryAgainButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType endButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(tryAgainButton, endButton);

        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                timer.stopTimer();
                logger.info("---------Timer Stopped!!!!----------");
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            endHelper(alert, endButton);
        });

        new Thread(task).start();

    }

    /**
     * Helper function to display the alert when quiz ends.
     * @param alert alert that is shown to user when the quiz ends.
     * @param endButton button that will end the quiz.
     */
    private void endHelper(Alert alert, ButtonType endButton) {
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == endButton) {
            handleExit();
        } else {
            restartQuiz();
        }
    }

    /**
     * Restarts the quiz session by resetting progress.
     */
    private void restartQuiz() {
        answerableListPanelPlaceholder.getChildren().remove(answersGridPane.getRoot());
        fillInnerParts();
        score = 0;
        currentProgressIndex.set(0);
        commandBox.getCommandTextField().requestFocus();
    }

    /**
     * Closes the application.
     */
    @FXML
    protected void handleExit() {
        timer.stopTimer();
        mainWindow = new MainWindow(getPrimaryStage(), mainLogic);
        mainWindow.show();
        mainWindow.fillInnerParts();

        if (mode.value.equals("custom")) {
            mainWindow.resultDisplay.setFeedbackToUser("You attempted these questions."
                    + "Type 'list' to view your full list of questions again.");
        }
    }

    public AnswerableListPanel getAnswerableListPanel() {
        return answerableListPanel;
    }


    public final double getCurrentProgressIndex() {
        return currentProgressIndex.get();
    }

    public final ReadOnlyDoubleProperty currentProgressIndexProperty() {
        return currentProgressIndex.getReadOnlyProperty();
    }

    private Timer getTimerBasedOnMode(Mode mode, int nextLevel) {
        switch (mode.value.toLowerCase()) {
        case "normal":
            return new Timer(mode.getTime(), this::executeCommand);
        case "arcade":
            switch(nextLevel) {
            case 2:
                return new Timer(mode.getLevelTwoTime(), this::executeCommand);
            case 3:
                return new Timer(mode.getLevelThreeTime(), this::executeCommand);
            default:
                return new Timer(mode.getTime(), this::executeCommand);
            }
        default:
            logger.warning("invalid mode");
            return new Timer(30, this::executeCommand);
        }
    }

    private ObservableList<Answerable> getListBasedOnMode(Mode mode) {
        Comparator<Answerable> difficultyComparator = Comparator.comparing(answerable ->
                answerable.getDifficulty().value);
        switch (mode.value.toLowerCase()) {
        case "normal":
            ObservableList<Answerable> sortedList = this.mainLogic.getFilteredSortedAnswerableList(
                    PREDICATE_SHOW_ALL_ANSWERABLE, difficultyComparator);
            return sortedList;
        default:
            logger.warning("invalid mode");
            return this.mainLogic.getFilteredSortedAnswerableList(PREDICATE_SHOW_ALL_ANSWERABLE, difficultyComparator);
        }
    }
}
