package seedu.jarvis.storage.history.commands.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.course.TypicalCourses.getTypicalCourses;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.course.AddCourseCommand;

/**
 * Tests the behaviour of {@code JsonAdaptedAddCourseCommand}.
 */
public class JsonAdaptedAddCourseCommandTest {

    @Test
    public void toModelType_addCourses_returnsAddCourseCommand() throws Exception {
        AddCourseCommand addCourseCommand = new AddCourseCommand(getTypicalCourses());
        JsonAdaptedAddCourseCommand jsonAdaptedAddCourseCommand = new JsonAdaptedAddCourseCommand(addCourseCommand);
        assertEquals(addCourseCommand, jsonAdaptedAddCourseCommand.toModelType());
    }
}
