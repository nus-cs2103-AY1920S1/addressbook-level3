package seedu.address.model.common;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.cap.module.Credit;
import seedu.address.model.cap.module.Grade;
import seedu.address.model.cap.module.ModuleCode;
import seedu.address.model.cap.module.Semester;
import seedu.address.model.cap.module.Title;

/**
 * Represents a NUS modules in the capLog.
 */
public class Module {

    private final ModuleCode moduleCode;
    private final Title title;
    private final Semester semester;
    private final Credit credit;
    private final Grade grade;

    /**
     *  Constructs a {@code Module}.
     *
     * @param moduleCode Representative codes for the module.
     * @param title The title for the module.
     * @param credit Module credits that provides the weight
     */
    public Module(ModuleCode moduleCode, Title title, Semester semester,
                  Credit credit, Grade grade) {
        requireNonNull(moduleCode);
        requireNonNull(title);
        requireNonNull(semester);
        requireNonNull(credit);
        requireNonNull(grade);
        this.moduleCode = moduleCode;
        this.title = title;
        this.semester = semester;
        this.credit = credit;
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
                && otherModule.getGrade().equals(getGrade());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleCode, title, credit, grade);
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
                .append(" Grade: ")
                .append(getGrade());
        return builder.toString();
    }
}
