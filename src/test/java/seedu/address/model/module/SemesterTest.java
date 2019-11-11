package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.websocket.Cache;

class SemesterTest {
    private Module module1;
    private Semester sem1;
    private Semester sem2;

    @BeforeEach
    void setUp() {
        module1 = Cache.loadModule(new ModuleId("2019/2020", "CS2040")).get();
        sem1 = module1.getSemester(SemesterNo.SEMESTER_1);
        sem2 = module1.getSemester(SemesterNo.SEMESTER_2);
    }

    @Test
    void findLessons() {
        ArrayList<Lesson> lessons = sem1.findLessons(LessonType.LECTURE, new LessonNo("1"));
        assertTrue(!lessons.isEmpty());
    }

    @Test
    void testToString() {
        assertTrue(sem1.toString().contains("SEMESTER_1"));
        assertTrue(sem1.toString().contains("Exam Date: 2019-12-04"));
        assertTrue(sem1.toString().contains("ClassNo"));
    }

    @Test
    void testEquals() {
        assertTrue(sem1.equals(sem1));
        assertFalse(sem1.equals(sem2));
    }

    @Test
    void testHashCode() {
        assertNotEquals(sem1.hashCode(), sem2.hashCode());
    }
}
