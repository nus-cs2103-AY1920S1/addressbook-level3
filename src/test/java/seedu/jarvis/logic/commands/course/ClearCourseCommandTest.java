package seedu.jarvis.logic.commands.course;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.course.TypicalCourses.getTypicalCoursePlanner;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.testutil.course.CourseBuilder;

public class ClearCourseCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void hasInverseExecution() {
        ClearCourseCommand clearCourseCommand = new ClearCourseCommand();
        assertTrue(clearCourseCommand.hasInverseExecution());
    }

    @Test
    public void execute_nonEmptyCoursePlanner_success() {
        model = new ModelManager(
            new CcaTracker(),
            new HistoryManager(),
            new FinanceTracker(),
            new UserPrefs(),
            new Planner(),
            new CoursePlanner()
        );
        Model expectedModel = new ModelManager(
            new CcaTracker(),
            new HistoryManager(),
            new FinanceTracker(),
            new UserPrefs(),
            new Planner(),
            getTypicalCoursePlanner()
        );
        expectedModel.setCoursePlanner(new CoursePlanner());
        assertCommandSuccess(new ClearCourseCommand(), model,
            ClearCourseCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeInverse_success() {
        ClearCourseCommand clearCourseCommand = new ClearCourseCommand();
        model = new ModelManager(
            new CcaTracker(),
            new HistoryManager(),
            new FinanceTracker(),
            new UserPrefs(),
            new Planner(),
            new CoursePlanner()
        );
        Model expectedModel = new ModelManager(
            new CcaTracker(),
            new HistoryManager(),
            new FinanceTracker(),
            new UserPrefs(),
            new Planner(),
            getTypicalCoursePlanner()
        );
        Course validCourse = new CourseBuilder().build();
        model.addCourse(validCourse);
        expectedModel.setCoursePlanner(new CoursePlanner());
        assertCommandSuccess(clearCourseCommand, model, ClearCourseCommand.MESSAGE_SUCCESS,
            expectedModel);
        expectedModel.setCoursePlanner(getTypicalCoursePlanner());
        assertCommandInverseSuccess(clearCourseCommand, model, ClearCourseCommand.MESSAGE_INVERSE_SUCCESS,
            expectedModel);
    }

    @Test
    public void repeatedExecutionAndInverseExecution() {
        ClearCourseCommand clearCourseCommand = new ClearCourseCommand();
        model = new ModelManager(
            new CcaTracker(),
            new HistoryManager(),
            new FinanceTracker(),
            new UserPrefs(),
            new Planner(),
            new CoursePlanner()
        );
        Model expectedModel = new ModelManager(
            new CcaTracker(),
            new HistoryManager(),
            new FinanceTracker(),
            new UserPrefs(),
            new Planner(),
            getTypicalCoursePlanner()
        );
        Course validCourse = new CourseBuilder().build();
        int cycles = 1000;
        IntStream.range(0, cycles)
            .forEach(index -> {
                expectedModel.setCoursePlanner(new CoursePlanner());
                assertCommandSuccess(clearCourseCommand, model,
                    ClearCourseCommand.MESSAGE_SUCCESS, expectedModel);
                expectedModel.setCoursePlanner(getTypicalCoursePlanner());
                assertCommandInverseSuccess(clearCourseCommand, model,
                    ClearCourseCommand.MESSAGE_INVERSE_SUCCESS, expectedModel);
            });
    }
}
