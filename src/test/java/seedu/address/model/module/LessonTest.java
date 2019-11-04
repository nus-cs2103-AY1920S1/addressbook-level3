package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.websocket.Cache;

class LessonTest {
    private Module module1;
    private Semester sem1;
    private ArrayList<Lesson> lessons;

    @BeforeEach
    void setUp() {
        module1 = Cache.loadModule(new ModuleId("2019/2020", "CS2040")).get();
        sem1 = module1.getSemester(SemesterNo.SEMESTER_1);
        lessons = sem1.getTimetable();
    }

    @Test
    void testToString() {
        Lesson lesson = lessons.get(0);
        assertTrue(lesson.toString().contains("ClassNo"));
        assertTrue(lesson.toString().contains("Weeks"));
        assertTrue(lesson.toString().contains("LessonType"));
        assertTrue(lesson.toString().contains("Day"));
        assertTrue(lesson.toString().contains("Start"));
        assertTrue(lesson.toString().contains("End"));
        assertTrue(lesson.toString().contains("Venue"));
    }

    @Test
    void testEquals() {
        Lesson lesson1 = lessons.get(0);
        Lesson lesson2 = lessons.get(0);
        Lesson lesson3 = lessons.get(1);
        assertTrue(lesson1.equals(lesson1));
        assertTrue(lesson1.equals(lesson2));
        assertFalse(lesson1.equals(lesson3));
    }

    @Test
    void testHashCode() {
        Lesson lesson1 = lessons.get(0);
        Lesson lesson2 = lessons.get(0);
        Lesson lesson3 = lessons.get(1);
        assertEquals(lesson1.hashCode(), lesson1.hashCode());
        assertEquals(lesson1.hashCode(), lesson2.hashCode());
        assertNotEquals(lesson1.hashCode(), lesson3.hashCode());
    }
}
