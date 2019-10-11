package seedu.tarence.model.tutorial;

import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;


import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.exceptions.StudentNotFoundException;
import seedu.tarence.model.tutorial.exceptions.WeekNotFoundException;

/**
 * Represents an Attendance for a Tutorial.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Attendance {
    private final Map<Week, Map<Student, Boolean>> attendance;
  
    /**
     * Every field must be present and not null.
     */
    public Attendance(Set<Week> weeks, List<Student> students) {
        requireAllNonNull(weeks, students);
        attendance = new TreeMap<>();

        for (Week week : weeks) {
            attendance.put(week, new HashMap<>());
            for (Student student: students) {
                attendance.get(week).put(student, false);
            }
        }
    }

    public Map<Week, Map<Student, Boolean>> getAttendance() {

        return attendance;
    }

    /**
     * Returns attendance for that week if it exists, else throws an error.
     */
    public Map<Student, Boolean> getWeek(Week week) throws WeekNotFoundException {

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
     * Sets attendance of Student in specified Week.
     * Throws error if Week does not exist.
     */
    public void setAttendance(Week week,
            Student student, boolean isPresent) throws WeekNotFoundException {
        try {
            this.isPresent(week, student);
            getWeek(week).replace(student, isPresent);
        } catch (StudentNotFoundException e) {
            getWeek(week).put(student, isPresent);
        }
    }

    /**
     * Toggles attendance of Student in specified Week.
     * Throws error if Week does not exist.
     */
    public void setAttendance(Week week,
            Student student) throws WeekNotFoundException {
        try {
            setAttendance(week, student, !isPresent(week, student));
        } catch (StudentNotFoundException e) {
            setAttendance(week, student, true);
        }
    }

    /**
     * Adds Student to Attendance, used when adding a Student to a Tutorial.
     */
    public void addStudent(Student student) throws WeekNotFoundException {
        Set<Week> weeks = attendance.keySet();
        for (Week week : weeks) {
            setAttendance(week, student, false);
        };

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
