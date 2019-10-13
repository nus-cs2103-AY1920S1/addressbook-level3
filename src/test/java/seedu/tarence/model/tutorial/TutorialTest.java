package seedu.tarence.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.Assert.assertThrows;
import static seedu.tarence.testutil.TypicalStudents.ALICE;
import static seedu.tarence.testutil.TypicalTutorials.CS1020_LAB01;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.student.Student;

public class TutorialTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tutorial(null, null, null, null, null, null, null));
    }

    @Test
    public void isSameTutorial() {
        // same name, timetable and modcode
        Tutorial copy = new TutorialBuilder(CS1020_LAB01).build();
        assertTrue(CS1020_LAB01.isSameTutorial(copy));

        // different timetable
        TimeTable timetable = new TimeTable(DayOfWeek.FRIDAY, LocalTime.MAX, new TreeSet<>(), Duration.ZERO);
        Tutorial diffTimeTable = new TutorialBuilder(CS1020_LAB01).withTimeTable(timetable).build();
        assertFalse(CS1020_LAB01.isSameTutorial(diffTimeTable));

        // different name
        Tutorial diffTutName = new TutorialBuilder(CS1020_LAB01).withTutName("CS1020_LAB02").build();
        assertFalse(CS1020_LAB01.isSameTutorial(diffTutName));

        // different students
        List<Student> students = new ArrayList<>();
        students.add(ALICE);
        Tutorial diffStudents = new TutorialBuilder(CS1020_LAB01).withStudents(students).build();
        assertTrue(CS1020_LAB01.isSameTutorial(diffStudents));

        // different modcode
        Tutorial diffModCode = new TutorialBuilder(CS1020_LAB01).withModCode("CS2040").build();
        assertFalse(CS1020_LAB01.isSameTutorial(diffModCode));
    }

    @Test
    public void equals() {
        // same name and students
        Tutorial copy = new TutorialBuilder(CS1020_LAB01).build();
        assertTrue(CS1020_LAB01.equals(copy));

        // different timetable
        TimeTable timetable = new TimeTable(DayOfWeek.FRIDAY, LocalTime.MAX, new TreeSet<>(), Duration.ZERO);
        Tutorial diffTimeTable = new TutorialBuilder(CS1020_LAB01).withTimeTable(timetable).build();
        assertFalse(CS1020_LAB01.equals(diffTimeTable));

        // different name
        Tutorial diffTutName = new TutorialBuilder(CS1020_LAB01).withTutName("CS1020_LAB02").build();
        assertFalse(CS1020_LAB01.equals(diffTutName));

        // different students
        List<Student> students = new ArrayList<>();
        students.add(ALICE);
        Tutorial diffStudents = new TutorialBuilder(CS1020_LAB01).withStudents(students).build();
        assertFalse(CS1020_LAB01.equals(diffStudents));

        // different modcode
        Tutorial diffModCode = new TutorialBuilder(CS1020_LAB01).withModCode("CS2040").build();
        assertFalse(CS1020_LAB01.equals(diffModCode));
    }
}
