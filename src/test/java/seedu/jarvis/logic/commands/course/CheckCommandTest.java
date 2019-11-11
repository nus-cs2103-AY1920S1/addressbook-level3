package seedu.jarvis.logic.commands.course;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.logic.commands.course.CheckCommand.MESSAGE_CANNOT_TAKE_COURSE;
import static seedu.jarvis.logic.commands.course.CheckCommand.MESSAGE_CAN_TAKE_COURSE;
import static seedu.jarvis.logic.commands.course.CheckCommand.MESSAGE_NO_PREREQS;
import static seedu.jarvis.logic.commands.course.CheckCommand.MESSAGE_SUCCESS;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.course.TypicalCourses.CS3230;
import static seedu.jarvis.testutil.course.TypicalCourses.MA1521;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.commons.util.andor.AndOrTree;
import seedu.jarvis.logic.commands.exceptions.CommandException;
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

public class CheckCommandTest {

    private Model model;
    private Model expectedModel;
    private CheckCommand cc;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(), new UserPrefs(),
                new Planner(), new CoursePlanner());
        expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(), model.getFinanceTracker(),
            new UserPrefs(), model.getPlanner(), model.getCoursePlanner());
        cc = new CheckCommand(MA1521);
    }

    /**
     * Verifies that checking {@code CheckCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        CheckCommand cc = new CheckCommand(MA1521);
        assertFalse(cc.hasInverseExecution());
    }

    /**
     * Verifies that calling inverse execution of {@code CheckCommand} will always throw a
     * {@code CommandException} with the correct message.
     */
    @Test
    public void inverseExecute_throwsCommandException() {
        CheckCommand cc = new CheckCommand(MA1521);
        assertThrows(CommandException.class,
            CheckCommand.MESSAGE_NO_INVERSE, () -> cc.executeInverse(model));
    }

    @Test
    public void execute_noPrereqs_success() {
        Course expectedCourse = new CourseBuilder().build();
        CheckCommand cc = new CheckCommand(expectedCourse);
        String expectedMessage = String.format(MESSAGE_NO_PREREQS, expectedCourse);
        expectedModel.checkCourse(expectedMessage);
        String expectedResult = String.format(MESSAGE_SUCCESS, expectedCourse);
        assertCommandSuccess(cc, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_withPrereqsDoesNotFulfill_cannotTakeCourse() {
        CheckCommand cc = new CheckCommand(CS3230);
        String expectedTreeString = AndOrTree.buildTree(
            CS3230.getCourseCode().toString(),
            CS3230.getPrereqTree().toString(), (c) -> CourseUtil.getCourse(c).get()
        ).toString();
        String expectedMessage = String.format(MESSAGE_CANNOT_TAKE_COURSE, CS3230, expectedTreeString);
        expectedModel.checkCourse(expectedMessage);
        String expectedResult = String.format(MESSAGE_SUCCESS, CS3230);
        assertCommandSuccess(cc, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_withPrereqsDoesNotFulfillsActualCourses_canTakeCourse() {
        Course actualCS3230 = CourseUtil.getCourse("CS3230").get();
        Course actualCS2040 = CourseUtil.getCourse("CS2040").get();
        CheckCommand cc = new CheckCommand(actualCS3230);
        model.addCourse(actualCS2040);
        expectedModel.addCourse(actualCS2040);
        String expectedTreeString = AndOrTree.buildTree(
            actualCS3230.getCourseCode().toString(),
            actualCS3230.getPrereqTree().toString(), (c) -> CourseUtil.getCourse(c).get()
        ).toString();
        String expectedMessage = String.format(MESSAGE_CANNOT_TAKE_COURSE, actualCS3230, expectedTreeString);
        expectedModel.checkCourse(expectedMessage);
        String expectedResult = String.format(MESSAGE_SUCCESS, actualCS3230);
        assertCommandSuccess(cc, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_withPrereqsFulfillsActualCourses_canTakeCourse() {
        Course actualCS3230 = CourseUtil.getCourse("CS3230").get();
        Course actualCS2040 = CourseUtil.getCourse("CS2040").get();
        Course actualCS1231 = CourseUtil.getCourse("CS1231").get();
        CheckCommand cc = new CheckCommand(actualCS3230);
        model.addCourse(actualCS2040);
        model.addCourse(actualCS1231);
        expectedModel.addCourse(actualCS2040);
        expectedModel.addCourse(actualCS1231);
        String expectedTreeString = AndOrTree.buildTree(
                actualCS3230.getCourseCode().toString(),
                actualCS3230.getPrereqTree().toString(), (c) -> CourseUtil.getCourse(c).get()
            ).toString();
        String expectedMessage = String.format(
            MESSAGE_CAN_TAKE_COURSE,
            actualCS3230,
            expectedTreeString
        );
        expectedModel.checkCourse(expectedMessage);
        String expectedResult = String.format(MESSAGE_SUCCESS, actualCS3230);
        assertCommandSuccess(cc, model, expectedResult, expectedModel);
    }
}
