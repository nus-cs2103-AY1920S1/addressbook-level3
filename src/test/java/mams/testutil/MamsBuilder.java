package mams.testutil;

import mams.model.Mams;
import mams.model.student.Student;

/**
 * A utility class to help with building MAMS objects.
 * Example usage: <br>
 *     {@code Mams ab = new MamsBuilder().withStudent("John", "Doe").build();}
 */
public class MamsBuilder {

    private Mams mams;

    public MamsBuilder() {
        mams = new Mams();
    }

    public MamsBuilder(Mams mams) {
        this.mams = mams;
    }

    /**
     * Adds a new {@code Student} to the {@code Mams} that we are building.
     */
    public MamsBuilder withStudent(Student student) {
        mams.addStudent(student);
        return this;
    }

    public Mams build() {
        return mams;
    }
}
