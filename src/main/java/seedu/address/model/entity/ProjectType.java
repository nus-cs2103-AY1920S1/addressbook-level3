package seedu.address.model.entity;

public enum ProjectType {

    PLACEHOLDER("placeholder");

    private final String projectTypeString;
    public static final String MESSAGE_CONSTRAINTS = "Placeholder text here"; //TODO: Update this

    private ProjectType(String projectTypeString) {
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
    public String toStorageValue(){
        return this.toString();
    }

    public static boolean isValidProjectType(String projectType) {
        try {
            ProjectType validProjectType = ProjectType.valueOf(projectType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
