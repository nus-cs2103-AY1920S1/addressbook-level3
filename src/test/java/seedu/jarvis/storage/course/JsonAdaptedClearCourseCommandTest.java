package seedu.jarvis.storage.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.course.TypicalCourses.CS1010;
import static seedu.jarvis.testutil.course.TypicalCourses.CS3230;
import static seedu.jarvis.testutil.course.TypicalCourses.MA1521;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.course.ClearCourseCommand;
import seedu.jarvis.storage.history.commands.course.JsonAdaptedClearCourseCommand;

/**
 * Tests the behaviour of {@code JsonAdaptedClearCourseCommand}.
 */
public class JsonAdaptedClearCourseCommandTest {

    @Test
    public void toModelType_clearCoursesNoArguments_returnsClearCourseCommand() throws Exception {
        ClearCourseCommand clearCourseCommand = new ClearCourseCommand();
        JsonAdaptedClearCourseCommand jsonAdaptedClearCourseCommand = new JsonAdaptedClearCourseCommand(
                clearCourseCommand);
        assertEquals(clearCourseCommand, jsonAdaptedClearCourseCommand.toModelType());
    }

    @Test
    public void toModelType_clearCoursesWithArguments_returnsClearCourseCommand() throws Exception {
        ClearCourseCommand clearCourseCommand = new ClearCourseCommand(Arrays.asList(MA1521, CS1010, CS3230));
        JsonAdaptedClearCourseCommand jsonAdaptedClearCourseCommand = new JsonAdaptedClearCourseCommand(
                clearCourseCommand);
        assertEquals(clearCourseCommand, jsonAdaptedClearCourseCommand.toModelType());
    }
}
