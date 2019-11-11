package seedu.tarence.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.Assert.assertThrows;
import static seedu.tarence.testutil.TypicalStudents.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.exceptions.StudentNotFoundException;
import seedu.tarence.model.tutorial.exceptions.WeekNotFoundException;

public class AttendanceTest {

    private final Set<Week> weeks = new TreeSet<>(Arrays.asList(new Week(1), new Week(2), new Week(3)));
    private final List<Student> students = new ArrayList<>();
    private final Attendance attendance = new Attendance(weeks, students);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Attendance(null, null));
    }

    @Test
    public void isPresent_weeksNotInList_throwsWeekNotFoundException() {
        assertThrows(WeekNotFoundException.class, () -> attendance.isPresent(new Week(4), ALICE));
    }

    @Test
    public void isPresent_studentNotInList_throwsStudentNotFoundException() {
        assertThrows(StudentNotFoundException.class, () -> attendance.isPresent(new Week(1), ALICE));
    }

    @Test
    public void setAttendance_weeksNotInList_throwsWeekNotFoundException() {
        assertThrows(WeekNotFoundException.class, () -> attendance.setAttendance(new Week(4), ALICE, true));
    }

    @Test
    public void setAttendance() {
        students.add(ALICE);
        Attendance attendanceWithStudent = new Attendance(weeks, students);
        assertFalse(attendanceWithStudent.isPresent(new Week(1), ALICE));
        attendanceWithStudent.setAttendance(new Week(1), ALICE, true);
        assertTrue(attendanceWithStudent.isPresent(new Week(1), ALICE));
    }

    @Test
    public void addStudent() {
        assertThrows(StudentNotFoundException.class, () -> attendance.isPresent(new Week(1), ALICE));
        attendance.addStudent(ALICE);
        assertFalse(attendance.isPresent(new Week(1), ALICE));
    }

    @Test
    public void equals() {
        Attendance attendance1 = new Attendance(weeks, students);
        Attendance attendance2 = new Attendance(weeks, students);
        assertTrue(attendance1.equals(attendance2)); // same students and weeks

        Set<Week> weeks3 = new TreeSet<>(Arrays.asList(new Week(3), new Week(4), new Week(5)));
        Attendance attendance3 = new Attendance(weeks3, students);
        assertFalse(attendance3.equals(attendance1)); // different weeks

        Student[] studentsArray = { ALICE };
        List<Student> students4 = Arrays.asList(studentsArray);
        Attendance attendance4 = new Attendance(weeks, students4);
        assertFalse(attendance4.equals(attendance1)); // different students
    }
}
