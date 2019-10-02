package seedu.address.model.entity;

public enum ProjectType {

    PLACEHOLDER("placeholder");

    private final String projectTypeString;

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
}
