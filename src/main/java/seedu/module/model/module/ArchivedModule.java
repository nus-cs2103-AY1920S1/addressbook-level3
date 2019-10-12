package seedu.module.model.module;

import java.util.Objects;

/**
 * Represents an Archived Module. An Archived Module is an Object containing data on a module
 * provided by the institution. Should be read-only.
 */
public class ArchivedModule {

    // Identity field
    private final String moduleCode;
    private final String title;
    private final String description;

    /**
     * Every field must be present and not null.
     */
    public ArchivedModule(String moduleCode, String title, String description) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
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
