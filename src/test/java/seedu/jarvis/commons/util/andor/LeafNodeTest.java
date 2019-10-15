package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.course.CourseTestUtil.CourseStub;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.course.Course;

public class LeafNodeTest {
    private static final Course EXAMPLE_COURSE = new CourseStub("aaa");

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
        LeafNode leaf = new LeafNode(EXAMPLE_COURSE, null);
        assertTrue(() -> leaf.hasFulfilledCondition(COLLECTION_PASSES_REQUIREMENTS_SINGLE));
        assertTrue(() -> leaf.hasFulfilledCondition(COLLECTION_PASSES_REQUIREMENTS_MULTIPLE));
    }

    @Test
    public void hasFulfilledCondition_invalidInput_returnsFalse() {
        LeafNode leaf = new LeafNode(EXAMPLE_COURSE, null);
        assertFalse(() -> leaf.hasFulfilledCondition(COLLECTION_FAILS_REQUIREMENTS));
    }

    @Test
    public void toString_returnsStringForm() {
        assertEquals("aaa",
            new LeafNode(EXAMPLE_COURSE, null).toString());
        assertEquals("null",
            new LeafNode(null, null).toString());
    }
}
