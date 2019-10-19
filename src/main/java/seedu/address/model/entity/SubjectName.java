package seedu.address.model.entity;

import static java.util.Objects.requireNonNull;

/**
 * Encapsulates possible topics for a Hackathon project.
 */
public enum SubjectName {
    ENVIRONMENTAL("Environmental"),
    SOCIAL("Social"),
    HEALTH("Health"),
    EDUCATION("Education");

    public static final String MESSAGE_CONSTRAINTS =
            "SubjectName should be one of the following:\n"
            + "\t1. Environmental\n"
            + "\t2. Social\n"
            + "\t3. Health\n"
            + "\t4. Education";


    private static final String VALIDATION_REGEX = "^(?i)(Environmental|Social|Health|Education)(?-i)$";

    private final String subjectNameString;

    private SubjectName(String subjectNameString) {
        requireNonNull(subjectNameString);
        this.subjectNameString = subjectNameString;
    }

    /**
     * Returns if a given string is a valid name.
     * @param test Name.
     * @return boolean whether test is in valid name format.
     */
    public static boolean isValidSubjectName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return this.subjectNameString;
    }

    public String toStorageValue() {
        return this.name();
    }

}
