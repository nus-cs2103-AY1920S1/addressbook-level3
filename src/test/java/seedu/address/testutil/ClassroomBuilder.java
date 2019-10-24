package seedu.address.testutil;

import seedu.address.model.Classroom;
import seedu.address.model.student.Student;

/**
 * A utility class to help with building Addressbook objects.
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

    /**
     * Adds a new {@code Student} to the {@code Classroom} that we are building.
     */
    public ClassroomBuilder withStudent(Student student) {
        classroom.addStudent(student);
        return this;
    }

    public Classroom build() {
        return classroom;
    }
}
