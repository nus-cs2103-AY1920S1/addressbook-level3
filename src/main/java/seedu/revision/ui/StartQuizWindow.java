package seedu.revision.ui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.Logic;
import seedu.revision.logic.commands.CommandResult;
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
    Answer[] wrongAnswerStub = {new Answer("WRONG A"), new Answer("WRONG B"), new Answer("WRONG C"),};
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
    private ResultDisplay resultDisplay;

    private AnswersGridPane answersGridPane;

    @FXML
    private StackPane answerableListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public StartQuizWindow(Stage primaryStage, Logic logic) {
        super(primaryStage, logic);
    }


    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

//        answerableListPanel = new AnswerableListPanel(logic.getFilteredAnswerableList());
//        answerableListPanelPlaceholder.getChildren().add(answerableListPanel.getRoot());
        ObservableList<Answerable> filteredList = logic.getFilteredAnswerableList();

        answersGridPane = new AnswersGridPane(DEFAULT_QUESTION);
        answerableListPanelPlaceholder.getChildren().add(answersGridPane.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    protected void handleExit() {
        mainWindow = new MainWindow(getPrimaryStage(), getLogic());
        mainWindow.show();
        mainWindow.fillInnerParts();
    }

    public AnswerableListPanel getAnswerableListPanel() {
        return answerableListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.revision.logic.Logic#execute(String)
     */
    protected CommandResult executeCommand(String commandText) throws CommandException, ParseException {
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

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
