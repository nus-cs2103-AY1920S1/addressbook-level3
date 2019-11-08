package guitests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.revision.testutil.TypicalMcqs.MCQ_STUB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import seedu.revision.logic.Logic;
import seedu.revision.logic.LogicManager;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.RevisionTool;
import seedu.revision.model.UserPrefs;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.answerable.Saq;
import seedu.revision.model.answerable.TrueFalse;
import seedu.revision.model.category.Category;
import seedu.revision.model.quiz.Mode;
import seedu.revision.model.quiz.NormalMode;
import seedu.revision.storage.Storage;
import seedu.revision.stubs.HistoryStub;
import seedu.revision.stubs.StorageStub;
import seedu.revision.ui.AlertDialog;
import seedu.revision.ui.StartQuizWindow;

@ExtendWith(ApplicationExtension.class)
public class StartQuizWindowTest {

    private static final TrueFalse TRUE_FALSE_STUB =
            new TrueFalse(new Question("The term Design Patterns was popularized by a book whose authors are "
                    + "also known as the ‘Three Amigos’."), new ArrayList<>(Arrays.asList(new Answer("false"))),
                    new Difficulty("2"), new HashSet<>(Arrays.asList(new Category("Design Patterns"),
                    new Category("Week 9"))));

    private static final Saq SAQ_STUB =
            new Saq(new Question("The term Design Patterns was popularized by a book whose authors are "
                    + "also known as the ‘Three Amigos’."), new ArrayList<>(Arrays.asList(new Answer("false"))),
                    new Difficulty("3"), new HashSet<>(Arrays.asList(new Category("Design Patterns"),
                    new Category("Week 9"))));

    private static final String COMMAND_EXIT = "exit";
    private static final String ANSWER_VALID_MCQ = "A";
    private static final String ANSWER_VALID_TRUEFALSE = "True";
    private static final String ANSWER_VALID_SAQ = "Short Answer";
    private static final String ANSWER_INVALID = "invalid answer";
    private static final Mode normalMode = new NormalMode();

    private StartQuizWindow startQuizWindow;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) {
        Storage storageStub = new StorageStub();
        RevisionTool testRevisionTool = new RevisionTool();
        testRevisionTool.addAnswerable(MCQ_STUB);
        testRevisionTool.addAnswerable(TRUE_FALSE_STUB);
        testRevisionTool.addAnswerable(SAQ_STUB);
        Model model = new ModelManager(testRevisionTool, new UserPrefs(), new HistoryStub());
        Logic logic = new LogicManager(model, storageStub);
        startQuizWindow = new StartQuizWindow(stage, logic, normalMode);
        startQuizWindow.fillInnerParts();
        stage.show();
    }

    @Test
    public void typeValidAnswer_shouldProgressToNextQuestion(FxRobot robot) {
        robot.clickOn(".commandTextField");
        double previousProgress = startQuizWindow.getProgressIndicatorBar().getCurrentProgress();
        //startQuizWindow.getCommandBox().getCommandTextField().setText(ANSWER_VALID_MCQ);
        robot.type(KeyCode.A);
        //robot.write(ANSWER_VALID_MCQ);
        //startQuizWindow.getCommandBox().getCommandTextField().fireEvent(new ActionEvent());
        robot.type(KeyCode.ENTER);
        double currentProgress = startQuizWindow.getProgressIndicatorBar().getCurrentProgress();
        assertNotEquals(previousProgress, currentProgress);
    }

    //pass
    @Test
    public void typeInvalidAnswer_shouldNotProgressToNextQuestion(FxRobot robot) {
        double previousProgress = startQuizWindow.getProgressIndicatorBar().getCurrentProgress();
        robot.clickOn(".commandTextField");
        robot.type(KeyCode.X);
        //startQuizWindow.getCommandBox().getCommandTextField().setText(ANSWER_INVALID);
        //robot.write(ANSWER_INVALID);
        robot.type(KeyCode.ENTER);
        double currentProgress = startQuizWindow.getProgressIndicatorBar().getCurrentProgress();
        assertEquals(previousProgress, currentProgress);
    }

    //pass
    @Test
    public void typeValidAnswer_endOfLevelReached_shouldShowNextLevelAlert(FxRobot robot) {
        robot.clickOn(".commandTextField");
        startQuizWindow.getCommandBox().getCommandTextField().setText(ANSWER_VALID_MCQ);
        //robot.write(ANSWER_VALID_MCQ);
        robot.type(KeyCode.ENTER);
        int numberOfAlerts = getNumberOfAlertsShown(robot, AlertDialog.NEXT_LEVEL_TITLE);
        assertEquals(1, numberOfAlerts);
        robot.type(KeyCode.ENTER);
    }

    @Test
    public void typeValidAnswer_endOfQuizReached_shouldShowEndAlert(FxRobot robot) {
        robot.clickOn(".commandTextField");
        startQuizWindow.getCommandBox().getCommandTextField().setText(ANSWER_VALID_MCQ);
        //robot.write(ANSWER_VALID_MCQ);
        robot.type(KeyCode.ENTER);
        robot.type(KeyCode.ENTER); //Go to next level
        startQuizWindow.getCommandBox().getCommandTextField().setText(ANSWER_VALID_TRUEFALSE);
        //robot.write(ANSWER_VALID_TRUEFALSE);
        robot.type(KeyCode.ENTER);
        robot.type(KeyCode.ENTER); //Go to next level
        startQuizWindow.getCommandBox().getCommandTextField().setText(ANSWER_VALID_SAQ);
        //robot.write(ANSWER_VALID_SAQ);
        robot.type(KeyCode.ENTER);
        assertEquals("Level 3", startQuizWindow.getLevelLabel().getLevelLabel().getText());
        int numberOfAlerts = getNumberOfAlertsShown(robot, AlertDialog.END_QUIZ_TITLE);
        assertEquals(1, numberOfAlerts);
        robot.type(KeyCode.ENTER);
    }

    @Test
    public void typeExit_shouldReturnFromQuizModeToConfigurationMode(FxRobot robot) {
        robot.clickOn(".commandTextField");
        startQuizWindow.getCommandBox().getCommandTextField().setText(COMMAND_EXIT);
        robot.sleep(250);
        //robot.write(COMMAND_EXIT);
        robot.type(KeyCode.ENTER);
        int numberOfAlerts = getNumberOfAlertsShown(robot, "CS2103 Revision Tool");
        assertEquals(1, numberOfAlerts);
    }

    @Test
    public void typeExit_shouldNotShowQuizMode(FxRobot robot) {
        robot.clickOn(".commandTextField");
        startQuizWindow.getCommandBox().getCommandTextField().setText(COMMAND_EXIT);
        robot.sleep(250);
        //robot.write(COMMAND_EXIT);
        robot.type(KeyCode.ENTER);
        int numberOfAlerts = getNumberOfAlertsShown(robot, "Quiz Mode");
        assertEquals(0, numberOfAlerts);
    }

    private int getNumberOfAlertsShown(FxRobot fxRobot, String stageTitle) {
        return (int) fxRobot.listTargetWindows().stream()
                .filter(window -> window instanceof Stage && ((Stage) window).getTitle().equals(stageTitle))
                .count();
    }

}
