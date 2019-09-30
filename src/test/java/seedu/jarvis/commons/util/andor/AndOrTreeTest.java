package seedu.jarvis.commons.util.andor;

import org.junit.jupiter.api.Test;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.model.course.Course;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AndOrTreeTest {
    @Test
    public void buildTree_invalidCourse_returnsEmptyTree() {
        List<String> toTest = List.of("CS1231", "", "34542SD", "randomstring", "CS1231SS");
        for (String course : toTest) {
            assertThrows(IOException.class, () -> AndOrTree.buildTree(course));
        }
    }

    @Test
    public void fulfillsCondition_sufficientRequirements_returnsTrue() {
        try {
            Map<String, List<Course>> toTest = Map.of(
                    "CS3244", List.of(
                            CourseUtil.getCourse("CS2040"),
                            CourseUtil.getCourse("ST2334"),
                            CourseUtil.getCourse("MA1505"),
                            CourseUtil.getCourse("MA1101R")
                    ),
                    "CS3230", List.of(
                            CourseUtil.getCourse("CS2040"),
                            CourseUtil.getCourse("CS1231")
                    ),
                    "CS2102", List.of(
                            CourseUtil.getCourse("CS1010"),
                            CourseUtil.getCourse("CS2040"),
                            CourseUtil.getCourse("CS1231")
                    ),
                    "CS2040", List.of(
                            CourseUtil.getCourse("CS1010")
                    )
            );

            toTest.forEach((course, taken) -> {
                AndOrTree tree = assertDoesNotThrow(() -> AndOrTree.buildTree(course));
                assertTrue(tree.fulfillsCondition(taken));
            });
        } catch (IOException e) {
            fail("IOException thrown.");
        }
    }

    @Test
    public void fulfillsCondition_insufficientRequirements_returnsFalse() {
        try {
            Map<String, List<Course>> toTest = Map.of(
                    "CS3244", List.of(
                            CourseUtil.getCourse("CS2040"),
                            CourseUtil.getCourse("ST2334"),
                            CourseUtil.getCourse("MA1511"),
                            CourseUtil.getCourse("MA1101R")
                    ),
                    "CS3230", List.of(
                            CourseUtil.getCourse("CS2040"),
                            CourseUtil.getCourse("CS1010"),
                            CourseUtil.getCourse("MA1521")
                    ),
                    "CS2102", List.of(
                            CourseUtil.getCourse("CS1010"),
                            CourseUtil.getCourse("CS2040"),
                            CourseUtil.getCourse("CS2030"),
                            CourseUtil.getCourse("CS2010")
                    ),
                    "CS2040", List.of(
                            CourseUtil.getCourse("CS1101S"),
                            CourseUtil.getCourse("CS1010J")
                    )
            );

            toTest.forEach((course, taken) -> {
                AndOrTree tree = assertDoesNotThrow(() -> AndOrTree.buildTree(course));
                assertFalse(tree.fulfillsCondition(taken));
            });
        } catch (IOException e) {
            fail("IOException thrown.");
        }
    }
}
