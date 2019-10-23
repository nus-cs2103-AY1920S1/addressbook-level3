package seedu.revision.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.MainLogic;
import seedu.revision.logic.QuizLogic;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.category.Category;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.Question;


/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class StartQuizWindow extends Window {

    Answer correctAnswerStub = new Answer("CORRECT");
    ArrayList<Answer> correctAnswerSetStub = new ArrayList<>(Arrays.asList(correctAnswerStub));
    Answer[] wrongAnswerStub = {new Answer("WRONG A"), new Answer("WRONG B"), new Answer("WRONG C")};
    ArrayList<Answer> wrongAnswerSetStub = new ArrayList<>(Arrays.asList(wrongAnswerStub));
    Category categoryStub = new Category("math");
    Set<Category> categoriesStub = new HashSet<>(Arrays.asList(categoryStub));

    public MainWindow mainWindow;
    private final Mcq DEFAULT_QUESTION =
            new Mcq(new Question("what is 10 + 10?"), correctAnswerSetStub, wrongAnswerSetStub,
                    new Difficulty("1"), categoriesStub);

    private final Logger logger = LogsCenter.getLogger(getClass());

    // Independent Ui parts residing in this Ui container
    private AnswerableListPanel answerableListPanel;
    private ResultDisplay questionDisplay;
    private AnswersGridPane answersGridPane;
    private CommandBox commandBox;
    private ProgressIndicatorBar progressIndicatorBar;

//    private int currentAnswerableIndex = 0;
    private Answerable currentAnswerable;
    private Iterator<Answerable> answerableIterator;
//    private ObservableList<Answerable> filteredAnswerableList;
    private int score = 0;

    ReadOnlyDoubleWrapper currentReadOnlyIndex = new ReadOnlyDoubleWrapper(this, "currenReadOnlyIndex",
            0);

    public final double getCurrentReadOnlyIndex() {
        return currentReadOnlyIndex.get();
    }
    public final ReadOnlyDoubleProperty currentReadOnlyIndexProperty() {
        return currentReadOnlyIndex.getReadOnlyProperty();
    }

    public StartQuizWindow(Stage primaryStage, MainLogic mainLogic, QuizLogic quizLogic) {
        super(primaryStage, mainLogic, quizLogic);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

        ObservableList<Answerable> filteredAnswerableList = this.mainLogic.getFilteredAnswerableList();
        answerableIterator = filteredAnswerableList.iterator();
        currentAnswerable = answerableIterator.next();

        answersGridPane = new AnswersGridPane(currentAnswerable);
        answerableListPanelPlaceholder.getChildren().add(answersGridPane.getRoot());

        questionDisplay = new ResultDisplay();
        questionDisplay.setFeedbackToUser(currentAnswerable.getQuestion().toString());
        resultDisplayPlaceholder.getChildren().add(questionDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(mainLogic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

//        currentReadOnlyIndex.set(currentAnswerableIndex);
        progressIndicatorBar = new ProgressIndicatorBar(currentReadOnlyIndex, filteredAnswerableList.size(),
                "%s");
//                "%d /" + filteredAnswerableList.size());
        scoreProgressBar.getChildren().add(progressIndicatorBar.getRoot());
    }

    void show() {
        primaryStage.show();
    }

    @FXML
    private void handleEnd() {
        currentReadOnlyIndex.set(currentReadOnlyIndex.get() + 1);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Quiz has ended! Your score is " + score + "\n" +
                "Try again?\n" +
                "Press [ENTER] to try again.\n" +
                "Press [ESC] to return to main screen.");

        ButtonType tryAgainButton = new ButtonType(
                "Yes",
                ButtonBar.ButtonData.OK_DONE
        );
        ButtonType endButton = new ButtonType(
                "No",
                ButtonBar.ButtonData.CANCEL_CLOSE
        );

        alert.getButtonTypes().setAll(tryAgainButton, endButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == tryAgainButton) {
            restartQuiz();
        } else if (result.get() == endButton) {
            handleExit();
        }
    }

    private void restartQuiz() {
        fillInnerParts();
        score = 0;
        currentReadOnlyIndex.set(0);
        commandBox.getCommandTextField().requestFocus();
    }

    /**
     * Closes the application.
     */
    @FXML
    protected void handleExit() {
        //TODO: Need to restore the list to normal state. List gets filtered when quiz starts.
        mainWindow = new MainWindow(getPrimaryStage(), mainLogic, quizLogic);
        mainWindow.show();
        mainWindow.fillInnerParts();
    }

    public AnswerableListPanel getAnswerableListPanel() {
        return answerableListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see QuizLogic#execute(String, Answerable)
     */
    @Override
    protected CommandResult executeCommand ( String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = quizLogic.execute(commandText, currentAnswerable);
            logger.info("Question result: " + commandResult.getFeedbackToUser());
            if (commandResult.getFeedbackToUser() == "correct") {
                score++;
            }

            if (!answerableIterator.hasNext()) {
                handleEnd();
                return new CommandResult("Quiz ended");
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            currentAnswerable = answerableIterator.next();
            currentReadOnlyIndex.set(currentReadOnlyIndex.get() + 1);
//            currentAnswerableIndex++;
//            currentReadOnlyIndex.set(currentAnswerableIndex++);
//            logger.info(currentAnswerable.toString());
            questionDisplay.setFeedbackToUser(currentAnswerable.getQuestion().toString());
            answersGridPane.updateAnswers(currentAnswerable);

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            questionDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
