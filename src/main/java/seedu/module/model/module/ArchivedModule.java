package seedu.module.model.module;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents an Archived Module. An Archived Module is an Object containing data on a module
 * provided by the institution. Should be read-only.
 */
public class ArchivedModule implements Module {

    // Identity field
    private final String moduleCode;
    private final String title;
    private final String description;
    private final Optional<String> prerequisite;
    private final Optional<String> preclusion;
    private final SemesterDetailList semesterDetails;

    /**
     * Every field must be present and not null.
     */
    public ArchivedModule(String moduleCode, String title, String description, String prerequisite,
        String preclusion, SemesterDetailList semesterDetails) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
        this.prerequisite = Optional.ofNullable(prerequisite);
        this.preclusion = Optional.ofNullable(preclusion);
        this.semesterDetails = semesterDetails;
    }

    public Optional<String> getPreclusion() {
        return preclusion;
    }

    public SemesterDetailList getSemesterDetails() {
        return semesterDetails;
    }

    public Optional<String> getPrerequisite() {
        return prerequisite;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns true if both archived modules of the same name have the same identity field.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameArchivedModule(ArchivedModule otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null && otherModule.getModuleCode().equals(getModuleCode());
    }

    /**
     * Returns true if both archived modules have the same identity and data fields.
     * This defines a stronger notion of equality between two modules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ArchivedModule)) {
            return false;
        }

        ArchivedModule otherModule = (ArchivedModule) other;
        return otherModule.getModuleCode().equals(getModuleCode()) && otherModule.getTitle().equals(getTitle())
                && otherModule.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleCode, title, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModuleCode()).append(" Title: ").append(getTitle()).append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }

}
