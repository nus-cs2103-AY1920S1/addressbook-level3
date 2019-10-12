package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.commons.util.andor.AndOrStubsUtil.CHILDREN;
import static seedu.jarvis.commons.util.andor.AndOrStubsUtil.CourseStub;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.course.Course;

public class OrNodeTest {
    private static final List<Course> COLLECTION_PASSES_REQUIREMENTS_SINGLE = List.of(
        new CourseStub("aaa")
    );

    private static final List<Course> COLLECTION_PASSES_REQUIREMENTS_MULTIPLE = List.of(
        new CourseStub("aaa"),
        new CourseStub("fff"),
        new CourseStub("ggg")
    );

    private static final List<Course> COLLECTION_FAILS_REQUIREMENTS = List.of(
        new CourseStub("zzz"),
        new CourseStub("yyy")
    );

    @Test
    public void hasFulfilledCondition_validInput_returnsTrue() {
        OrNode or = new OrNode(null, null, CHILDREN);
        assertTrue(() -> or.hasFulfilledCondition(COLLECTION_PASSES_REQUIREMENTS_SINGLE));
        assertTrue(() -> or.hasFulfilledCondition(COLLECTION_PASSES_REQUIREMENTS_MULTIPLE));
    }

    @Test
    public void hasFulfilledCondition_invalidInput_returnsFalse() {
        OrNode or = new OrNode(null, null, CHILDREN);
        assertFalse(() -> or.hasFulfilledCondition(COLLECTION_FAILS_REQUIREMENTS));
    }

    @Test
    public void toString_returnsStringForm() {
        assertEquals("one of",
                new OrNode(null, null, null).toString());
    }
}
