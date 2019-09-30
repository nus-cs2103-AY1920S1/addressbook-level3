package seedu.address.model.entity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public enum SubjectName {
    PLACEHOLDER("placeholder");


    private static final String SPECIAL_CHARACTERS = "-'";

    public static final String MESSAGE_CONSTRAINTS =
            "SubjectName should adhere to the following constraints:\n"
                    +"1. It should contain alphabets, spaces, and these special characters, excluding"
                    + "the parentheses, (" + SPECIAL_CHARACTERS + "). \n"
                    + "2.Contain at least one character";


    private static final String VALIDATION_REGEX = "^[" + SPECIAL_CHARACTERS + " a-zA-Z" + "]+$";



    private final String subjectNameString;

    private SubjectName(String subjectNameString) {
        requireNonNull(subjectNameString);
        checkArgument(isValidSubjectName(subjectNameString), MESSAGE_CONSTRAINTS);
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

    public String toStorageValue(){
        return this.toString();
}
}
