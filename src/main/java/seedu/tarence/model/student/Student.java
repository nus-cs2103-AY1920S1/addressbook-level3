package seedu.tarence.model.student;

import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.tutorial.TutName;

/**
 * Represents a Student.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student extends Person {

    // Identity fields
    protected Optional<MatricNum> matricNum;
    protected Optional<NusnetId> nusnetId;

    // TODO: Assumes student can only belong to one module and tutorial?
    protected ModCode modCode;
    protected TutName tutName;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Email email,
            Optional<MatricNum> matricNum, Optional<NusnetId> nusnetId,
            ModCode modCode, TutName tutName) {
        super(name, email);
        requireAllNonNull(matricNum, nusnetId);
        this.matricNum = matricNum;
        this.nusnetId = nusnetId;
        this.modCode = modCode;
        this.tutName = tutName;
    }

    public Optional<MatricNum> getMatricNum() {
        return matricNum;
    }

    public Optional<NusnetId> getNusnetId() {
        return nusnetId;
    }

    public ModCode getModCode() {
        return modCode;
    }

    public TutName getTutName() {
        return tutName;
    }

    /**
     * Returns true if both students have the same email, nusnet id or matric number and are in the same class.
     * This defines a weaker notion of equality between two students.
     */
    @Override
    public boolean isSamePerson(Person otherPerson) {

        Student otherStudent = (Student) otherPerson;
        if (otherStudent == this) {
            return true;
        } else if (otherStudent == null) {
            return false;
        }

        boolean hasSameEmail = otherStudent.getEmail().equals(getEmail());
        boolean hasSameMatNo = getMatricNum().isPresent() && otherStudent.getMatricNum().equals(getMatricNum());
        boolean hasSameNusId = getNusnetId().isPresent() && otherStudent.getNusnetId().equals(getNusnetId());
        boolean hasSameClass = otherStudent.getTutName().equals(getTutName())
                && otherStudent.getModCode().equals(getModCode());

        return (((hasSameEmail || hasSameMatNo || hasSameNusId) && hasSameClass)
                || (hasSameEmail && (!hasSameMatNo || !hasSameNusId))
                || (hasSameMatNo && !hasSameEmail)
                || (hasSameNusId && !hasSameEmail));
    }

    /**
     * Returns true if both students have the same name, email, matric num and nusid.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName())
                && (otherStudent.getEmail().equals(getEmail())
                && otherStudent.getMatricNum().equals(getMatricNum())
                && otherStudent.getNusnetId().equals(getNusnetId()))
                && (!otherStudent.getModCode().equals(getModCode())
                || !otherStudent.getTutName().equals(getTutName()));
    }

    /**
     * Returns true if both students have the same identity or data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getName().equals(getName())
                && otherStudent.getEmail().equals(getEmail())
                && otherStudent.getMatricNum().equals(getMatricNum())
                && otherStudent.getNusnetId().equals(getNusnetId())
                && otherStudent.getTutName().equals(getTutName())
                && otherStudent.getModCode().equals(getModCode());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, email, matricNum, nusnetId);
    }

    /**
     * Used for saving. Please don't touch.
     *
     * @return String representation of a Student object.
     */
    // TODO: Sorry I touched it but it doesn't seem to break anything.
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Email: ")
                .append(getEmail());
        if (getMatricNum().isPresent()) {
            builder.append(" Matric Number: ")
                    .append(getMatricNum().get());
        }
        if (getNusnetId().isPresent()) {
            builder.append(" NUSNET Id: ")
                .append(getNusnetId().get());
        }
        return builder.toString();
    }

}
