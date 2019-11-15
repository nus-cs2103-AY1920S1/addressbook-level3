package seedu.revision.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.revision.testutil.TypicalMcqs.MCQ_C;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
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

/**
 * @author wilfredbtan
 */
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

    @BeforeAll
    public static void runHeadless() {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
    }

    /**
     * Will be called with {@code @Before} semantics, i.e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) {
        Storage storageStub = new StorageStub();
        RevisionTool testRevisionTool = new RevisionTool();
        testRevisionTool.addAnswerable(MCQ_C);
        testRevisionTool.addAnswerable(TRUE_FALSE_STUB);
        testRevisionTool.addAnswerable(SAQ_STUB);
        Model model = new ModelManager(testRevisionTool, new UserPrefs(), new HistoryStub());
        Logic logic = new LogicManager(model, storageStub);
        startQuizWindow = new StartQuizWindow(stage, logic, normalMode);
        startQuizWindow.fillInnerParts();
        stage.show();
    }

    /**
     * Enters a valid answer and checks that the progress in the progress bar has increased.
     * @param robot robot used to simulate user actions. Will be injected by the test runner.
     */
    @Test
    public void typeValidAnswer_shouldProgressToNextQuestion(FxRobot robot) {
        robot.clickOn(".commandTextField");
        double previousProgress = startQuizWindow.getProgressIndicatorBar().getCurrentProgress();
        robot.write(ANSWER_VALID_MCQ);
        robot.type(KeyCode.ENTER);
        double currentProgress = startQuizWindow.getProgressIndicatorBar().getCurrentProgress();
        assertNotEquals(previousProgress, currentProgress);
    }

    /**
     * Enters an invalid answer and checks that the progress in the progress bar has increased.
     * @param robot robot used to simulate user actions. Will be injected by the test runner.
     */
    @Test
    public void typeInvalidAnswer_shouldNotProgressToNextQuestion(FxRobot robot) {
        double previousProgress = startQuizWindow.getProgressIndicatorBar().getCurrentProgress();
        robot.clickOn(".commandTextField");
        robot.write(ANSWER_INVALID);
        robot.type(KeyCode.ENTER);
        double currentProgress = startQuizWindow.getProgressIndicatorBar().getCurrentProgress();
        assertEquals(previousProgress, currentProgress);
    }

    /**
     * Enters a valid answer and checks that an alert is shown when the end of the level has been reached.
     * @param robot robot used to simulate user actions. Will be injected by the test runner.
     */
    @Test
    public void typeValidAnswer_endOfLevelReached_shouldShowNextLevelAlert(FxRobot robot) {
        robot.clickOn(".commandTextField");
        robot.write(ANSWER_VALID_MCQ);
        robot.type(KeyCode.ENTER);
        int numberOfAlerts = getNumberOfWindowsShown(robot, AlertDialog.NEXT_LEVEL_TITLE);
        assertEquals(1, numberOfAlerts);
        robot.type(KeyCode.ENTER);
    }

    /**
     * Enters a 3 valid answers (simulating each level of the quiz) and checks that an alert is shown when the
     * end of the quiz has been reached.
     * @param robot robot used to simulate user actions. Will be injected by the test runner.
     */
    @Test
    public void typeValidAnswer_endOfQuizReached_shouldShowEndAlert(FxRobot robot) {
        robot.clickOn(".commandTextField");
        robot.write(ANSWER_VALID_MCQ);
        robot.type(KeyCode.ENTER);
        robot.type(KeyCode.ENTER); //Go to next level
        robot.write(ANSWER_VALID_TRUEFALSE);
        robot.type(KeyCode.ENTER);
        robot.type(KeyCode.ENTER); //Go to next level
        robot.write(ANSWER_VALID_SAQ);
        robot.type(KeyCode.ENTER);
        assertEquals("Level 3", startQuizWindow.getLevelLabel().getLevelLabel().getText());
        int numberOfAlerts = getNumberOfWindowsShown(robot, AlertDialog.END_QUIZ_TITLE);
        assertEquals(1, numberOfAlerts);
        robot.type(KeyCode.ENTER);
    }

    /**
     * Enters the exit command and checks whether the window returns to configuration mode and quiz mode is dismissed.
     * @param robot robot used to simulate user actions. Will be injected by the test runner.
     */
    @Test
    public void typeExit_shouldReturnFromQuizModeToConfigurationMode(FxRobot robot) {
        robot.clickOn(".commandTextField");
        robot.write(COMMAND_EXIT);
        robot.type(KeyCode.ENTER);
        int numberOfMainWindows = getNumberOfWindowsShown(robot, "CS2103 Revision Tool");
        assertEquals(1, numberOfMainWindows);
        int numberOfQuizWindows = getNumberOfWindowsShown(robot, "Quiz Mode");
        assertEquals(0, numberOfQuizWindows);
    }

    private int getNumberOfWindowsShown(FxRobot fxRobot, String stageTitle) {
        return (int) fxRobot.listTargetWindows().stream()
                .filter(window -> window instanceof Stage && ((Stage) window).getTitle().equals(stageTitle))
                .count();
    }

}
