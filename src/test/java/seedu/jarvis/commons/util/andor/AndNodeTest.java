package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.commons.util.andor.AndOrStubsUtil.CHILDREN;
import static seedu.jarvis.commons.util.andor.AndOrStubsUtil.CourseStub;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.course.Course;

public class AndNodeTest {
    private static final List<Course> COLLECTION_PASSES_REQUIREMENTS = List.of(
        new CourseStub("aaa"),
        new CourseStub("bbb"),
        new CourseStub("ccc"),
        new CourseStub("ddd"),
        new CourseStub("eee"),
        new CourseStub("fff")
    );

    private static final List<Course> COLLECTION_FAILS_REQUIREMENTS = List.of(
        new CourseStub("bbb"),
        new CourseStub("ccc"),
        new CourseStub("ddd"),
        new CourseStub("eee")
    );

    @Test
    public void hasFulfilledCondition_validInput_returnsTrue() {
        AndNode an = new AndNode(null, null, CHILDREN);
        assertTrue(() -> an.hasFulfilledCondition(COLLECTION_PASSES_REQUIREMENTS));
    }

    @Test
    public void hasFulfilledCondition_invalidInput_returnsFalse() {
        AndNode an = new AndNode(null, null, CHILDREN);
        assertFalse(() -> an.hasFulfilledCondition(COLLECTION_FAILS_REQUIREMENTS));
    }

    @Test
    public void toString_returnsStringForm() {
        assertEquals("all of",
                new AndNode(null, null, null).toString());
    }
}
