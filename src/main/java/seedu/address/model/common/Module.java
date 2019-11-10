package seedu.address.model.common;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.cap.person.Credit;
import seedu.address.model.cap.person.Faculty;
import seedu.address.model.cap.person.Grade;
import seedu.address.model.cap.person.ModuleCode;
import seedu.address.model.cap.person.Semester;
import seedu.address.model.cap.person.Title;

/**
 * Represents a NUS modules in the address book.
 */
public class Module {

    private ModuleCode moduleCode;
    private Title title;
    private Semester semester;
    private Credit credit;
    private Faculty faculty;
    private Grade grade;

    /**
     *  Constructs a {@code Module}.
     *
     * @param moduleCode Representative codes for the module.
     * @param title The title for the module.
     * @param credit Module credits that provides the weight
     * @param faculty The faculty the module is held at.
     */
    public Module(ModuleCode moduleCode, Title title, Semester semester,
                  Credit credit, Faculty faculty, Grade grade) {
        requireNonNull(moduleCode);
        requireNonNull(title);
        requireNonNull(semester);
        requireNonNull(credit);
        requireNonNull(faculty);
        requireNonNull(grade);
        this.moduleCode = moduleCode;
        this.title = title;
        this.semester = semester;
        this.credit = credit;
        this.faculty = faculty;
        this.grade = grade;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Title getTitle() {
        return title;
    }

    public Semester getSemester() {
        return semester;
    }

    public Credit getCredit() {
        return credit;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public Grade getGrade() {
        return grade;
    }

    /**
     * Compares if two modules are the same.
     * @param otherModule module of comparison
     * @return
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null
                && otherModule.getModuleCode().equals(getModuleCode())
                && (otherModule.getTitle().equals(getTitle()) || otherModule.getCredit() == this.getCredit());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getModuleCode().equals(getModuleCode())
                && otherModule.getTitle().equals(getTitle())
                && otherModule.getCredit() == (getCredit())
                && otherModule.getFaculty().equals(getFaculty())
                && otherModule.getGrade().equals(getGrade());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleCode, title, credit, faculty, grade);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModuleCode())
                .append(" Title: ")
                .append(getTitle())
                .append(" Semester: ")
                .append(getSemester())
                .append(" Credit: ")
                .append(getCredit())
                .append(" Faculty: ")
                .append(getFaculty())
                .append(" Grade: ")
                .append(getGrade());
        return builder.toString();
    }
}
