package seedu.tarence.testutil;

import java.util.Optional;

import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.MatricNum;
import seedu.tarence.model.student.NusnetId;
import seedu.tarence.model.student.Student;


/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder extends PersonBuilder {

    public static final String DEFAULT_MATRIC = "A0123456X";
    public static final String DEFAULT_NUSNETID = "e0123456";

    private Optional<MatricNum> matricNum;
    private Optional<NusnetId> nusnetId;

    public StudentBuilder() {
        super();
        matricNum = Optional.<MatricNum>of(new MatricNum(DEFAULT_MATRIC));
        nusnetId = Optional.<NusnetId>of(new NusnetId(DEFAULT_NUSNETID));
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        super(studentToCopy);
        matricNum = studentToCopy.getMatricNum();
        nusnetId = studentToCopy.getNusnetId();
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
     * Sets the {@code MatricNum} of the {@code Student} that we are building.
     */
    public StudentBuilder withMatricNum(String matricNum) {
        this.matricNum = Optional.<MatricNum>of(new MatricNum(matricNum));
        return this;
    }

    /**
     * Sets the {@code NusnetId} of the {@code Student} that we are building.
     */
    public StudentBuilder withNusnetId(String nusnetId) {
        this.nusnetId = Optional.<NusnetId>of(new NusnetId(nusnetId));
        return this;
    }

    public Student build() {
        return new Student(name, email, matricNum, nusnetId);
    }

}
