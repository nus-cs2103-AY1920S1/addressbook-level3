package seedu.address.model.common;

import java.util.Objects;
import static java.util.Objects.requireNonNull;
import seedu.address.model.cap.person.ModuleCode;
import seedu.address.model.cap.person.Title;
import seedu.address.model.cap.person.Credit;
import seedu.address.model.cap.person.Faculty;
import seedu.address.model.cap.person.Description;

/**
 * Represents a NUS modules in the address book.
 */
public class Module {
    private ModuleCode moduleCode;
    private Title title;
    private Description description;
    private Credit credit;
    private Faculty faculty;
    private boolean isSU;
    private String preclusion;
    private String prerequisite;

    /**
     *  Constructs a {@code Module}.
     *
     * @param moduleCode Representative codes for the module.
     * @param title The title for the module.
     * @param credit Module credits that provides the weight
     * @param faculty The faculty the module is held at.
     * @param isSU Satisfactory and unsatisfactory option for grade
     */
    public Module(ModuleCode moduleCode, Title title, Description description, Credit credit, Faculty faculty,
                  boolean isSU, String preclusion, String prerequisite) {
        requireNonNull(moduleCode);
        requireNonNull(title);
        requireNonNull(credit);
        requireNonNull(faculty);
        requireNonNull(isSU);
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
        this.credit = credit;
        this.faculty = faculty;
        this.isSU = isSU;
        this.preclusion = preclusion;
        this.prerequisite = prerequisite;
    }

    public Module(ModuleCode moduleCode, Title title, Description description, Credit credit, Faculty faculty) {
        this(moduleCode, title, description, credit,
                faculty, true, "None", "None");
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Title getTitle() {
        return title;
    }

    public Credit getCredit() {
        return credit;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public Description getDescription() {
        return description;
    }

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
                && otherModule.getFaculty().equals(getFaculty());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleCode, title, credit, faculty);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModuleCode())
                .append(" Title: ")
                .append(getTitle())
                .append(" Credit: ")
                .append(getCredit())
                .append(" Faculty: ")
                .append(getFaculty());
        return builder.toString();
    }

}
