package seedu.tarence.model.tutorial;

import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.exceptions.StudentNotFoundException;
import seedu.tarence.model.tutorial.exceptions.WeekNotFoundException;

/**
 * Represents an Attendance for a Tutorial.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Attendance {
    private final HashMap<Week, HashMap<Student, Boolean>> attendance;

    /**
     * Every field must be present and not null.
     */
    public Attendance(Set<Week> weeks, List<Student> students) {
        requireAllNonNull(weeks, students);
        attendance = new HashMap<>();
        for (Week week : weeks) {
            attendance.put(week, new HashMap<>());
            for (Student student: students) {
                attendance.get(week).put(student, false);
            }
        }
    }

    public HashMap<Week, HashMap<Student, Boolean>> getAttendance() {
        return attendance;
    }

    /**
     * Returns attendance for that week if it exists, else throws an error.
     */
    public HashMap<Student, Boolean> getWeek(Week week) throws WeekNotFoundException {
        if (!attendance.containsKey(week)) {
            throw new WeekNotFoundException();
        }
        return attendance.get(week);
    }

    /**
     * Returns true if Student is present for the week, else false.
     * Throws error if Student or week does not exist.
     */
    public boolean isPresent(Week week, Student student) throws StudentNotFoundException, WeekNotFoundException {
        if (!this.getWeek(week).containsKey(student)) {
            throw new StudentNotFoundException();
        }

        return attendance.get(week).get(student);
    }

    /**
     * Sets attendance of Student in specified week.
     * Throws error if Student or week does not exist.
     */
    public void setAttendance(Week week,
            Student student, boolean isPresent) throws StudentNotFoundException, WeekNotFoundException {
        this.isPresent(week, student);
        getWeek(week).replace(student, isPresent);
    }

    /**
     * Adds student to attendance for particular week.
     * Throws error if week does not exist.
     */
    public void addStudentToWeek(Week week,
            Student student, boolean isPresent) throws WeekNotFoundException {
        getWeek(week).put(student, isPresent);
    }

    /**
     * Returns true if both attendances have the same identity or data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Attendance)) {
            return false;
        }

        Attendance otherAttendance = (Attendance) other;
        return otherAttendance.getAttendance().equals(this.getAttendance());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(attendance);
    }

    @Override
    public String toString() {
        return attendance.toString();
    }
}
