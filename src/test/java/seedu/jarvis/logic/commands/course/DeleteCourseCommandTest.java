package seedu.jarvis.logic.commands.course;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_COURSE;
import static seedu.jarvis.testutil.course.TypicalCourses.MA1521;
import static seedu.jarvis.testutil.course.TypicalCourses.getTypicalCoursePlanner;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.testutil.course.CourseBuilder;

public class DeleteCourseCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(),
            new UserPrefs(), new Planner(), getTypicalCoursePlanner());

        expectedModel = new ModelManager(
            model.getCcaTracker(),
            model.getHistoryManager(),
            model.getFinanceTracker(),
            new UserPrefs(),
            model.getPlanner(),
            model.getCoursePlanner()
        );
    }

    @Test
    public void hasInverseExecution() {
        DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand(MA1521);
        assertTrue(deleteCourseCommand.hasInverseExecution());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Course courseToDelete = model.getUnfilteredCourseList().get(INDEX_FIRST_COURSE.getZeroBased());
        DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand(INDEX_FIRST_COURSE);

        String expectedMessage = String.format(DeleteCourseCommand.MESSAGE_SUCCESS, courseToDelete);

        expectedModel.deleteCourse(courseToDelete);

        assertCommandSuccess(deleteCourseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCourseUnfilteredList_success() {
        Course courseToDelete = model.getUnfilteredCourseList().get(INDEX_FIRST_COURSE.getZeroBased());
        DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand(courseToDelete);

        String expectedMessage = String.format(DeleteCourseCommand.MESSAGE_SUCCESS, courseToDelete);

        expectedModel.deleteCourse(courseToDelete);
        assertCommandSuccess(deleteCourseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getUnfilteredCourseList().size() + 1);
        DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand(outOfBoundIndex);
        assertCommandFailure(deleteCourseCommand, model, Messages.MESSAGE_INVALID_COURSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidCourseUnfilteredList_throwsCommandException() {
        Course someOtherCourse = new CourseBuilder().withCourseCode("AAAA10000").build();
        DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand(someOtherCourse);
        assertCommandFailure(deleteCourseCommand, model, DeleteCourseCommand.MESSAGE_COURSE_NOT_IN_LIST);
    }

    @Test
    public void executeInverse_success() {
        Course courseToDelete = model.getUnfilteredCourseList().get(INDEX_FIRST_COURSE.getZeroBased());
        DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand(courseToDelete);

        expectedModel.deleteCourse(courseToDelete);

        String expectedMessage = String.format(DeleteCourseCommand.MESSAGE_SUCCESS, courseToDelete);

        String inverseExpectedMessage = String.format(
            DeleteCourseCommand.MESSAGE_INVERSE_SUCCESS_ADD, courseToDelete
        );

        assertCommandSuccess(deleteCourseCommand, model, expectedMessage, expectedModel);

        expectedModel.addCourse(INDEX_FIRST_COURSE.getZeroBased(), courseToDelete);

        assertCommandInverseSuccess(deleteCourseCommand, model, inverseExpectedMessage, expectedModel);
    }

    @Test
    public void repeatedExecutionAndInverseExecution() {
        DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand(INDEX_FIRST_COURSE);

        model = new ModelManager();
        model.addCourse(new CourseBuilder().build());
        expectedModel = new ModelManager(
            model.getCcaTracker(),
            model.getHistoryManager(),
            model.getFinanceTracker(),
            new UserPrefs(),
            model.getPlanner(),
            model.getCoursePlanner()
        );

        int cycles = 1000;
        IntStream.range(0, cycles)
            .forEach(index -> {
                Course courseToDelete = model.getUnfilteredCourseList().get(INDEX_FIRST_COURSE.getZeroBased());
                String expectedMessage = String.format(DeleteCourseCommand.MESSAGE_SUCCESS, courseToDelete);
                String inverseExpectedMessage = String.format(
                    DeleteCourseCommand.MESSAGE_INVERSE_SUCCESS_ADD, courseToDelete
                );

                expectedModel.deleteCourse(courseToDelete);
                assertCommandSuccess(deleteCourseCommand, model, expectedMessage, expectedModel);
                expectedModel.addCourse(courseToDelete);
                assertCommandInverseSuccess(deleteCourseCommand, model, inverseExpectedMessage, expectedModel);
            });
    }
}
