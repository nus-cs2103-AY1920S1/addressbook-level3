package seedu.address.model.entity;

/**
 * Represents a Project type to indicate the type of Entity.
 * Guarantees: Project type values are validated according to enum type, immutable.
 */
public enum ProjectType {

    PLACEHOLDER("placeholder");

    // Data fields
    public static final String MESSAGE_CONSTRAINTS = "Placeholder text here"; //TODO: Update this
    private final String projectTypeString;

    /**
     * Constructs an {@code ProjectType}.
     *
     * @param projectTypeString
     */
    ProjectType(String projectTypeString) {
        this.projectTypeString = projectTypeString;
    }

    /**
     * Returns string representation of object.
     *
     * @return Project type in string format.
     */
    @Override
    public String toString() {
        return this.projectTypeString;
    }

    /**
     * Returns string representation of object, for storage.
     *
     * @return Project type in string format.
     */
    public String toStorageValue() {
        return this.name();
    }

    /**
     * Returns if a given string is a valid ProjectType.
     *
     * @param projectType String of project type.
     * @return boolean whether projectType is in valid format.
     */
    public static boolean isValidProjectType(String projectType) {
        try {
            ProjectType validProjectType = ProjectType.valueOf(projectType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
