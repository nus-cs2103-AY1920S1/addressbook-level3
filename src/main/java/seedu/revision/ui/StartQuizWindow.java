package seedu.revision.ui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
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
    Set<Answer> correctAnswerSetStub = new HashSet<>(Arrays.asList(correctAnswerStub));
    Answer[] wrongAnswerStub = {new Answer("WRONG A"), new Answer("WRONG B"), new Answer("WRONG C")};
    Set<Answer> wrongAnswerSetStub = new HashSet<>(Arrays.asList(wrongAnswerStub));
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
    private int currentAnswerableIndex = 0;
    private Iterator<Answerable> answerableIterator;
//    private ObservableList<Answerable> filteredAnswerableList;

    @FXML
    private StackPane answerableListPanelPlaceholder;
    @FXML
    private StackPane resultDisplayPlaceholder;
    @FXML
    private StackPane statusbarPlaceholder;

    public StartQuizWindow(Stage primaryStage, MainLogic mainLogic, QuizLogic quizLogic) {
        super(primaryStage, mainLogic, quizLogic);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

        answerableIterator = this.mainLogic.getFilteredAnswerableList().iterator();
//        filteredAnswerableList = logic.getFilteredAnswerableList().sorted();
        Answerable firstAnswerable = answerableIterator.next();
//            Answerable firstAnswerable = filteredAnswerableList.get(currentAnswerableIndex);

        answersGridPane = new AnswersGridPane(firstAnswerable);
        answerableListPanelPlaceholder.getChildren().add(answersGridPane.getRoot());

        questionDisplay = new ResultDisplay();
        questionDisplay.setFeedbackToUser(firstAnswerable.getQuestion().toString());
        resultDisplayPlaceholder.getChildren().add(questionDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(mainLogic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void show() {
        primaryStage.show();
    }

    @FXML
    private void handleEnd() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Quiz has ended! Try again?\n" +
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
//        answerableIterator = logic.getFilteredAnswerableList().iterator();
//        CommandBox commandBox = new CommandBox(this::executeCommand);
//        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Closes the application.
     */
    @FXML
    protected void handleExit() {
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
            Answerable currentAnswerable = answerableIterator.next();
            logger.info(currentAnswerable.toString());
            questionDisplay.setFeedbackToUser(currentAnswerable.getQuestion().toString());
            answersGridPane.updateAnswers(currentAnswerable);
            currentAnswerableIndex++;

            CommandResult commandResult = quizLogic.execute(commandText, currentAnswerable);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            questionDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (!answerableIterator.hasNext()) {
                handleEnd();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            questionDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
