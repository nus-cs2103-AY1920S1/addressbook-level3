package seedu.tarence.model.builder;

import java.util.Optional;

import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.MatricNum;
import seedu.tarence.model.student.NusnetId;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;


/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder extends PersonBuilder {

    public static final String DEFAULT_MATRIC = "A0123456X";
    public static final String DEFAULT_MODULE = "CS1101S";
    public static final String DEFAULT_NUSNETID = "e0123456";
    public static final String DEFAULT_TUTORIAL = "T01";

    private Optional<MatricNum> matricNum;
    private ModCode modCode;
    private Optional<NusnetId> nusnetId;
    private TutName tutName;

    public StudentBuilder() {
        super();
        matricNum = Optional.<MatricNum>of(new MatricNum(DEFAULT_MATRIC));
        modCode = new ModCode(DEFAULT_MODULE);
        nusnetId = Optional.<NusnetId>of(new NusnetId(DEFAULT_NUSNETID));
        tutName = new TutName(DEFAULT_TUTORIAL);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        super(studentToCopy);
        matricNum = studentToCopy.getMatricNum();
        nusnetId = studentToCopy.getNusnetId();
        modCode = studentToCopy.getModCode();
        tutName = studentToCopy.getTutName();
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
     * Sets the {@code MatricNum} of the {@code Student} that we are building to empty.
     */
    public StudentBuilder withoutMatricNum() {
        this.matricNum = Optional.empty();
        return this;
    }

    /**
     * Sets the {@code NusnetId} of the {@code Student} that we are building to empty.
     */
    public StudentBuilder withoutNusnetId() {
        this.nusnetId = Optional.empty();
        return this;
    }

    /**
     * Sets the {@code NusnetId} of the {@code Student} that we are building.
     */
    public StudentBuilder withNusnetId(String nusnetId) {
        this.nusnetId = Optional.<NusnetId>of(new NusnetId(nusnetId));
        return this;
    }

    /**
     * Sets the {@code ModCode} of the {@code Student} that we are building.
     */
    public StudentBuilder withModCode(String modCode) {
        this.modCode = new ModCode(modCode);
        return this;
    }

    /**
     * Sets the {@code TutName} of the {@code Student} that we are building.
     */
    public StudentBuilder withTutName(String tutName) {
        this.tutName = new TutName(tutName);
        return this;
    }

    public Student build() {
        return new Student(name, email, matricNum, nusnetId, modCode, tutName);
    }

}
