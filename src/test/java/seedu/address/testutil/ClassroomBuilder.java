package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.classroom.Classroom;
//import seedu.address.model.lesson.Lesson;
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

    /**
     * Adds a new {@code Student} to the {@code Classroom} that we are building.
     */
    public ClassroomBuilder withStudent(Student student) {
        classroom.addStudent(student);
        return this;
    }

    /**
     * Adds a new {@code Assignment} to the {@code Classroom} that we are building.
     */
    public ClassroomBuilder withAssignment(Assignment assignment) {
        classroom.addAssignment(assignment);
        return this;
    }

    /**
     * Adds a new {@code Lesson} to the {@code Classroom} that we are building.
     */
    /*
    public ClassroomBuilder withLesson(Lesson lesson) {
        classroom.addLesson(lesson);
        return this;
    }
     */

    public Classroom build() {
        return classroom;
    }
}
