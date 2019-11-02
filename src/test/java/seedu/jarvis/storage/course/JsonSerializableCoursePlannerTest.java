package seedu.jarvis.storage.course;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.util.JsonUtil;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.testutil.course.TypicalCourses;

/**
 * Tests the behaviour of {@code JsonSerializableCoursePlanner}.
 */
public class JsonSerializableCoursePlannerTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableCoursePlannerTest");
    private static final Path TYPICAL_COURSES_FILE = TEST_DATA_FOLDER.resolve("typicalCoursePlanner.json");

    @Test
    public void toModelType_typicalCoursesFile_success() throws Exception {
        JsonSerializableCoursePlanner dataFromFile = JsonUtil.readJsonFile(TYPICAL_COURSES_FILE,
                JsonSerializableCoursePlanner.class).get();
        CoursePlanner coursePlannerFromFile = dataFromFile.toModelType();
        CoursePlanner typicalCoursePlanner = TypicalCourses.getTypicalCoursePlanner();
        assertEquals(coursePlannerFromFile, typicalCoursePlanner);
    }
}
