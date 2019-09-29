package seedu.address.model.Entity;

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
