package seedu.address.calendar.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the name of an event.
 * Guarantees: immutable, not longer than 100 characters, not represented by an empty string
 */
public class Name {
    public static final String MESSAGE_CONSTRAINT = "Name must be present and cannot be longer than 100 characters";
    public static final int MAX_NAME_LENGTH = 100;

    private String name;

    /**
     * Constructs an instance of {@code Name}.
     *
     * @param name {@code String} representation of name
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidNameString(name), MESSAGE_CONSTRAINT);
        this.name = name;
    }

    /**
     * Returns {@code this} as a representative {@code String}.
     *
     * @return {@code this} as a representative {@code String}.
     */
    String asString() {
        return toString();
    }

    /**
     * Checks whether the name string is valid.
     *
     * @param name The given name string
     * @return {@code true} only if the s
     */
    public static boolean isValidNameString(String name) {
        if (name.equals("") || name.length() > MAX_NAME_LENGTH) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Name)) {
            return false;
        }

        Name otherName = (Name) obj;
        String otherNameStr = otherName.name;
        return this.name.equals(otherNameStr);
    }
}
