package budgetbuddy.model.script;

import static java.util.Objects.requireNonNull;

import budgetbuddy.commons.util.AppUtil;

/**
 * Represents the name of a script.
 * Guarantees: immutable, and is valid as specified
 */
public class ScriptName {
    public static final String MESSAGE_CONSTRAINTS = "Script names must contain only "
            + "alphanumeric characters, dashes, and underscores, and must not be empty";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}-_]+";

    private final String name;

    /**
     * Constructs a script name.
     *
     * @param name A valid script name
     * @throws IllegalArgumentException if {@code name} is not a valid script name
     */
    public ScriptName(String name) {
        requireNonNull(name);
        AppUtil.checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.name = name;
    }

    /**
     * Returns true if {@code name} is a valid script name.
     *
     * @param name The string to validate
     * @return true if {@code name} is a valid script name
     */
    public boolean isValidName(String name) {
        return name.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScriptName that = (ScriptName) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
