package seedu.tarence.model.tutorial;

import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

import java.util.List;
import java.util.Objects;

import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.student.Student;

/**
 * Represents a Tutorial.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutorial {

    // Identity fields
    protected final TutName tutName;
    protected final TimeTable timeTable;
    protected List<Student> students;
    protected ModCode modCode;

    /**
     * Every field must be present and not null.
     */
    public Tutorial(TutName tutName, DayOfWeek day, LocalTime startTime,
            List<Integer> weeks, Duration duration, List<Student> students, ModCode modCode) {
        requireAllNonNull(tutName, day, startTime, weeks, students, modCode);
        this.tutName = tutName;
        this.timeTable = new TimeTable(day, startTime, weeks, duration);
        this.students = students;
        this.modCode = modCode;
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

    public void addStudent(Student student) {
        students.add(student);
    }

    public ModCode getModCode() {
        return modCode;
    }

    /**
     * Returns true if both tutorials have the same name and students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Class)) {
            return false;
        }

        Tutorial otherTutorial = (Tutorial) other;
        return otherTutorial.getTutName().equals(getTutName())
                && otherTutorial.getStudents().equals(getStudents());
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
        return builder.toString();
    }

    /**
     * Returns true if both Tutorials have the same name & timetable.
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
