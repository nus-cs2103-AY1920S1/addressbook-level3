package seedu.tarence.model.person;

import java.util.Optional;

/**
 * Represents a person, with additional fields of Matriculation Number and NUSNET ID.
 */
public class Student extends Person {

    private final Optional<MatricNum> matricNum;
    private final Optional<NusnetId> nusnetId;

    public Student(Name name, Email email,
                   Optional<MatricNum> matricNum,
                   Optional<NusnetId> nusnetId) {
        super(name, email);
        this.matricNum = matricNum;
        this.nusnetId = nusnetId;
    }

    public Optional<MatricNum> getMatricNum() {
        return matricNum;
    }

    public Optional<NusnetId> getNusnetId() {
        return nusnetId;
    }
}
