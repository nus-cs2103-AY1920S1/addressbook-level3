package seedu.address.testutil.student;

import seedu.address.model.student.Name;

/**
 * A utility class to help build names.
 */
public class NameBuilder {

    public static final String FULL_NAME = "Jonathan Del";

    private String fullName;

    public NameBuilder() {
        fullName = FULL_NAME;
    }

    /**
     * Initializes the NameBuilder with the data of {@code nameToCopy}.
     */
    public NameBuilder(Name nameToCopy) {
        fullName = nameToCopy.toString();
    }

    /**
     * Sets the {@code fullName} of the {@code Name} that we are building.
     */
    public NameBuilder withName(String name) {
        this.fullName = name;
        return this;
    }

    public Name build() {
        return new Name(fullName);
    }
}
