package seedu.revision.ui;

import java.util.Iterator;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
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
import seedu.revision.model.quiz.Modes;
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

    protected static final String FXML = "StartQuizWindow.fxml";

    @FXML
    protected StackPane levelPlaceholder;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private MainWindow mainWindow;
    private ObservableList<Answerable> quizList;
    private Mode mode;

    // Independent Ui parts residing in this Ui container
    private CommandBox commandBox;
    private LevelLabel levelLabel;
    private ResultDisplay questionDisplay;
    private AnswersGridPane answersGridPane;
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
        super(FXML, primaryStage, mainLogic);
        this.mode = mode;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        this.quizList = mainLogic.getFilteredSortedAnswerableList();
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

        int nextLevel = Integer.parseInt(quizList.get(0).getDifficulty().value);
        this.timer = new Timer(mode.getTime(nextLevel), this::executeCommand);

        levelLabel = new LevelLabel(nextLevel);
        levelPlaceholder.getChildren().add(levelLabel.getRoot());

        int sizeOfFirstLevel = getSizeOfCurrentLevel(quizList.get(0));
        progressIndicatorBar = new ProgressIndicatorBar(currentProgressIndex, sizeOfFirstLevel,
                "%.0f/" + sizeOfFirstLevel);

        progressAndTimerGridPane = new ScoreProgressAndTimerGridPane(progressIndicatorBar, timer);
        scoreProgressAndTimerPlaceholder.getChildren().add(progressAndTimerGridPane.getRoot());
    }

    private int getSizeOfCurrentLevel(Answerable answerable) {
        ObservableList<Answerable> sectionList = quizList.filtered(a ->
                a.getDifficulty().value.equals(answerable.getDifficulty().value));
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
        try {
            CommandResult commandResult = mainLogic.execute(commandText, currentAnswerable);
            if (commandResult.getFeedbackToUser().equalsIgnoreCase("correct")) {
                // TODO: KhiangLeon use the updateStatistics() method here or in McqInputCommand#execute.
                //  Both has access to the answerable.
                score++;
            }

            timer.resetTimer();

            if (commandResult.isExit()) {
                handleExit();
                return new CommandResult().build();
            }

            if (commandResult.getFeedbackToUser().equalsIgnoreCase("wrong")
                    && mode.value.equals(Modes.ARCADE.toString())) {
                handleEnd();
                return new CommandResult().build();
            }

            if (!answerableIterator.hasNext()) {
                handleEnd();
                return new CommandResult().build();
            }

            currentProgressIndex.set(getCurrentProgressIndex() + 1);

            previousAnswerable = currentAnswerable;
            currentAnswerable = answerableIterator.next();

            if (previousAnswerable != null && answerableIterator.hasNext()) {
                if (previousAnswerable.getDifficulty().compareTo(currentAnswerable.getDifficulty()) < 0) {
                    handleNextLevel(currentAnswerable);
                }
            }

            answerableListPanelPlaceholder.getChildren().remove(answersGridPane.getRoot());
            setAnswerGridPaneByType(currentAnswerable);
            answersGridPane.updateAnswers(currentAnswerable);

            questionDisplay.setFeedbackToUser(currentAnswerable.getQuestion().toString());

            return commandResult;
        } catch (CommandException | ParseException e) {
            questionDisplay.setFeedbackToUser(currentAnswerable.getQuestion().toString() + "\n\n" + e.getMessage());
            throw e;
        }
    }

    /**
     * Handles progression to the next level and receives response from the user.
     * @param nextAnswerable next answerable that will be displayed.
     */
    private void handleNextLevel(Answerable nextAnswerable) {
        int nextLevel = Integer.parseInt(nextAnswerable.getDifficulty().value);
        AlertDialog nextLevelDialog = AlertDialog.getNextLevelAlert(nextLevel, score,
                mainLogic.getFilteredAnswerableList().size());
        //TODO: Khiangleon to replace mainLogic.getFilteredAnswerableList to the total score so far.

        Task<Void> task = new Task<>() {
            @Override
            public Void call() {
                timer.stopTimer();
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            Optional<ButtonType> result = nextLevelDialog.showAndWait();
            if (result.get() == nextLevelDialog.getEndButton()) {
                handleExit();
            } else {
                //Reset UI in the window
                levelLabel = new LevelLabel(nextLevel);
                levelPlaceholder.getChildren().add(levelLabel.getRoot());
                currentProgressIndex.set(0);
                progressIndicatorBar = new ProgressIndicatorBar(currentProgressIndex,
                        getSizeOfCurrentLevel(nextAnswerable),
                        "%.0f/" + getSizeOfCurrentLevel(nextAnswerable));
                //Start a new timer for the next level
                this.timer = new Timer(mode.getTime(nextLevel), this::executeCommand);
                progressAndTimerGridPane = new ScoreProgressAndTimerGridPane(progressIndicatorBar, timer);
                scoreProgressAndTimerPlaceholder.getChildren().add(progressAndTimerGridPane.getRoot());
            }
        });

        //Start the event on a new thread so that showAndWait event is not conflicted with timer animation.
        new Thread(task).start();
    }

    /**
     * Handles ending of quiz session.
     */
    @FXML
    private void handleEnd() {
        currentProgressIndex.set(currentProgressIndex.get() + 1);
        boolean isFailure = mode.value.equals(Modes.ARCADE.toString()) && answerableIterator.hasNext();
        AlertDialog endAlert = AlertDialog.getEndAlert(score, mainLogic.getFilteredAnswerableList().size(), isFailure);
        //TODO: Khiangleon to replace mainLogic.getFilteredAnswerableList to the total score so far.

        Task<Void> task = new Task<>() {
            @Override
            public Void call() {
                timer.stopTimer();
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            Optional<ButtonType> result = endAlert.showAndWait();
            if (result.get() == endAlert.getEndButton()) {
                handleExit();
            } else {
                restartQuiz();
            }
        });

        //Start the event on a new thread so that showAndWait event is not conflicted with timer animation.
        new Thread(task).start();

    }

    /**
     * Restarts the quiz session and resets the progress.
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
        mainLogic.removeFiltersFromAnswerableList();
        mainWindow = new MainWindow(getPrimaryStage(), mainLogic);
        mainWindow.show();
        mainWindow.fillInnerParts();
        mainWindow.resultDisplay.setFeedbackToUser("Great attempt! Type 'start mode/MODE' "
                + "(normal / arcade / custom) to try another quiz!");
    }

    /** Gets the index of the current question to update the progress bar. **/
    public final double getCurrentProgressIndex() {
        return currentProgressIndex.get();
    }

}
