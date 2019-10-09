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
     * Returns true if both students have the same name and share one other attribute.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName())
                && (otherStudent.getEmail().equals(getEmail())
                || otherStudent.getMatricNum().equals(getMatricNum())
                || otherStudent.getNusnetId().equals(getNusnetId()));
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
                && otherStudent.getNusnetId().equals(getNusnetId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, email, matricNum, nusnetId);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Email: ")
                .append(getEmail())
                .append(" Matric Number: ")
                .append(getMatricNum())
                .append(" NUSNET Id: ")
                .append(getNusnetId());
        return builder.toString();
    }

}
