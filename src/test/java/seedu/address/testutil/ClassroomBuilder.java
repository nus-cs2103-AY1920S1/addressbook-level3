package seedu.address.testutil;

import java.util.List;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.student.Student;

/**
 * A utility class to help with building Classroom objects.
 * Example usage: <br>
 *     {@code Classroom ab = new ClassroomBuilder().withStudent("John", "Doe").build();}
 */
public class ClassroomBuilder {

    private Classroom classroom;

    public ClassroomBuilder() {
        classroom = new Classroom();
    }

    public ClassroomBuilder(Classroom classroom) {
        this.classroom = classroom;
    }

    public ClassroomBuilder withClassroomName(String classroomName) {
        classroom.setClassroomName(classroomName);
        return this;
    }

    /**
     * Adds a list of new {@code Student} to the {@code Classroom} that we are building.
     */
    public ClassroomBuilder withStudents(List<Student> students) {
        classroom.setStudents(students);
        return this;
    }

    /**
     * Adds a list of new {@code Assignment} to the {@code Classroom} that we are building.
     */
    public ClassroomBuilder withAssignments(List<Assignment> assignments) {
        classroom.setAssignments(assignments);
        return this;
    }

    public Classroom build() {
        return classroom;
    }
}
