package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.commons.util.CourseUtilTest.INVALID_COURSE_CODES;
import static seedu.jarvis.commons.util.CourseUtilTest.VALID_COURSE_CODES;
import static seedu.jarvis.commons.util.CourseUtilTest.VALID_COURSE_CODES_NO_PREREQ;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.exceptions.CourseNotFoundException;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.model.course.Course;

/**
 * @author ryanYtan
 */
public class AndOrTreeTest {

    @Test
    public void buildTree_validCourse_doesNotThrowException() {
        for (String course : VALID_COURSE_CODES) {
            assertDoesNotThrow(() -> AndOrTree.buildTree(course));
        }
    }

    @Test
    public void buildTree_invalidCourse_throwsException() {
        for (String course : INVALID_COURSE_CODES) {
            assertThrows(CourseNotFoundException.class, () -> AndOrTree.buildTree(course));
        }
    }

    @Test
    public void buildTree_validCourseWithNoRequirements_doesNotThrowException() {
        for (String course : VALID_COURSE_CODES_NO_PREREQ) {
            assertDoesNotThrow(() -> AndOrTree.buildTree(course));
        }
    }

    @Test
    public void fulfillCondition_courseWithNoPrereqs_printsNoPrereqString() {
        for (String course : VALID_COURSE_CODES_NO_PREREQ) {
            assertEquals(
                    assertDoesNotThrow(() -> AndOrTree.buildTree(course).toString()),
                    course + " has no prerequisites!");
        }
    }

    @Test
    public void fulfillsCondition_sufficientRequirements_returnsTrueAndDoesNotThrowException() {
        assertDoesNotThrow(() -> {
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
        });
    }

    @Test
    public void fulfillsCondition_insufficientRequirements_returnsFalseAndDoesNotThrowException() {
        assertDoesNotThrow(() -> {
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
        });
    }
}
