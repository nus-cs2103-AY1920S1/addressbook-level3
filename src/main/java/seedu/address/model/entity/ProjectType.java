package seedu.address.model.entity;

public enum ProjectType {
    PLACEHOLDER("placeholder");

    private final String projectTypeString;

    private ProjectType(String projectTypeString) {
        this.projectTypeString = projectTypeString;
    }

    @Override
    public String toString() {
        return this.projectTypeString;
    }
}
