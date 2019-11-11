package seedu.jarvis.storage.history.commands.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_COURSE;
import static seedu.jarvis.testutil.course.TypicalCourses.MA1521;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.course.DeleteCourseCommand;

/**
 * Tests the behaviour of {@code JsonAdaptedDeleteCourseCommand}.
 */
public class JsonAdaptedDeleteCourseCommandTest {

    @Test
    public void toModelType_validCourseValidIndex_returnsDeleteCourseCommand() throws Exception {
        DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand(MA1521, INDEX_FIRST_COURSE);
        JsonAdaptedDeleteCourseCommand jsonAdaptedDeleteCourseCommand = new JsonAdaptedDeleteCourseCommand(
                deleteCourseCommand);
        assertEquals(deleteCourseCommand, jsonAdaptedDeleteCourseCommand.toModelType());
    }

    @Test
    public void toModelType_validCourseNullIndex_returnsDeleteCourseCommand() throws Exception {
        DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand(INDEX_FIRST_COURSE);
        JsonAdaptedDeleteCourseCommand jsonAdaptedDeleteCourseCommand = new JsonAdaptedDeleteCourseCommand(
                deleteCourseCommand);
        assertEquals(deleteCourseCommand, jsonAdaptedDeleteCourseCommand.toModelType());
    }
}
