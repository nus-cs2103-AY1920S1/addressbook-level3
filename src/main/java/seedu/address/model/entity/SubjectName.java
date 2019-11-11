package seedu.address.model.entity;

import static java.util.Objects.requireNonNull;

/**
 * Encapsulates possible topics for a Hackathon project.
 */
public enum SubjectName {
    ENVIRONMENTAL("Environmental"),
    SOCIAL("Social"),
    HEALTH("Health"),
    EDUCATION("Education"),
    ENTERTAINMENT("Entertainment"),
    OTHER("Other");

    public static final String MESSAGE_CONSTRAINTS =
            "SubjectName should be one of the following:\n"
            + "\t1. Environmental\n"
            + "\t2. Social\n"
            + "\t3. Health\n"
            + "\t4. Education"
            + "\t5. Entertainment"
            + "\t6. Other";



    private static final String VALIDATION_REGEX = "^(?i)(Environmental|Social|Health|Education"
            + "|Entertainment|Other)(?-i)$";

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

    /**
     * Checks if two SubjectName are equal.
     *
     * @param other
     * @return boolean Whether they are equal.
     */
    public boolean equals(SubjectName other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SubjectName)) {
            return false;
        }


        SubjectName otherSubject = ((SubjectName) other);
        return otherSubject.toString().equals(this.toString());
    }

}
