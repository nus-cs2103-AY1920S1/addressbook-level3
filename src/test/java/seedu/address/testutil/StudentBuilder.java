package seedu.address.testutil;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.student.Matric;
import seedu.address.model.student.Student;


/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder extends PersonBuilder {

    public static final String DEFAULT_MATRIC = "A0123456X";

    private Matric matric;

    public StudentBuilder() {
        super();
        matric = new Matric(DEFAULT_MATRIC);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        super(studentToCopy);
        matric = studentToCopy.getMatric();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Matric} of the {@code Student} that we are building.
     */
    public StudentBuilder withMatric(String matric) {
        this.matric = new Matric(matric);
        return this;
    }

    public Student build() {
        return new Student(name, email, matric);
    }

}
