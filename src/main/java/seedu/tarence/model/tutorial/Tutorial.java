package seedu.tarence.model.tutorial;

import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.student.Student;

/**
 * Represents a Tutorial.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutorial {
    public static final int NOT_SUBMITTED = -1;

    // Identity fields
    protected final TutName tutName;
    protected final TimeTable timeTable;
    protected List<Student> students;
    protected ModCode modCode;
    protected Attendance attendance;
    protected Map<Assignment, Map<Student, Integer>> assignments;

    /**
     * Every field must be present and not null.
     */
    public Tutorial(TutName tutName, DayOfWeek day, LocalTime startTime,
            Set<Week> weeks, Duration duration,
            List<Student> students, ModCode modCode) {
        requireAllNonNull(tutName, day, startTime, weeks, students, modCode);
        this.tutName = tutName;
        this.timeTable = new TimeTable(day, startTime, weeks, duration);
        this.students = students;
        this.modCode = modCode;
        this.attendance = new Attendance(weeks, students);
        this.assignments = new TreeMap<>();
    }

    public TutName getTutName() {
        return tutName;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public List<Student> getStudents() {
        return students;
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the application.
     * The person identity of {@code editedStudent} must not be the same as another existing student in the application.
     */
    public void setStudent(Student target, Student editedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).isSameStudent(target)) {
                students.set(i, editedStudent);
            }
        }
    }

    public ModCode getModCode() {
        return modCode;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    /**
     * Adds a Student to a Tutorial.
     */
    public void addStudent(Student student) {
        students.add(student);
        attendance.addStudent(student);
    }

    /**
     * Removes a Student from a Tutorial.
     */
    public void deleteStudent(Student student) {
        students.remove(student);
        attendance.deleteStudent(student);
    }

    /**
     * Sets a Student's Attendance.
     */
    public void setAttendance(Week week, Student student) {
        attendance.setAttendance(week, student);
    }

    /**
     * Adds an Assignment to a Tutorial.
     */
    public void addAssignment(Assignment assignment) {
        assignments.put(assignment, new HashMap<>());
        for (Student student : students) {
            assignments.get(assignment).put(student, NOT_SUBMITTED);
        }
    }

    /**
     * Removes an Assignment from a Tutorial. Returns true if removal is successful
     */
    public boolean deleteAssignment(Assignment assignment) {
        return assignments.remove(assignment) != null;
    }

    /**
     * Removes an Assignment from a Tutorial via its index.
     */
    public boolean deleteAssignment(Index assignIndex) {
        Set<Assignment> keys = ((TreeMap<Assignment, Map<Student, Integer>>) assignments).navigableKeySet();
        Integer index = assignIndex.getZeroBased();
        int i = 0;
        for (Assignment assignment : keys) {
            if (index == i) {
                return assignments.remove(assignment) != null;
            }
            i++;
        }
        return false;
    }

    /**
     * Sets an Assignment in a Tutorial.
     */
    public void setAssignment(Assignment target, Assignment assignment) {
        addAssignment(assignment);
        assignments.get(target).putAll(assignments.get(assignment));
        deleteAssignment(target);
    }

    /**

     * Returns true if both tutorials have the same identity or data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tutorial)) {
            return false;
        }

        Tutorial otherTutorial = (Tutorial) other;
        return otherTutorial.getTutName().equals(getTutName())
                && otherTutorial.getStudents().equals(getStudents())
                && otherTutorial.getModCode().equals(getModCode())
                && otherTutorial.getTimeTable().equals(getTimeTable())
                && otherTutorial.getAttendance().equals(getAttendance());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tutName, timeTable, students);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTutName());
        builder.append(" | ");
        builder.append(getTimeTable().getDay().toString());
        builder.append(" | ");
        builder.append(getTimeTable().getStartTime().toString());
        builder.append(" | ");
        builder.append(getTimeTable().getWeeks().toString());
        builder.append(" | ");
        builder.append(getTimeTable().getDuration().toString());
        builder.append(" | Students: ");
        for (Student s : students) {
            builder.append(s.toString());

        }
        builder.append(" | ");
        builder.append(getModCode().toString());

        return builder.toString();
    }

    /**
     * Returns true if both Tutorials have the same name, timetable and modcode.
     *
     */
    public boolean isSameTutorial(Tutorial otherTutorial) {
        if (otherTutorial == this) {
            return true;
        }
        return otherTutorial != null
                && otherTutorial.getTimeTable().equals(getTimeTable())
                && otherTutorial.getTutName().equals(getTutName())
                && otherTutorial.getModCode().equals(getModCode());
    }

}
