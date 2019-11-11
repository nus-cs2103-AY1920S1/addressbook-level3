package seedu.planner.logic.parser;

//@@author oscarsu97

/**
 * Represents the argument that is tagged to each prefix in an argument string.
 */
public class PrefixArgument {
    private final Prefix prefix;

    private final String argValue;

    public PrefixArgument(Prefix prefix, String argValue) {
        this.prefix = prefix;
        this.argValue = argValue;
    }

    public String getArgValue() {
        return argValue;
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public String toString() {
        return prefix + argValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Prefix)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Prefix otherPrefix = (Prefix) obj;
        return otherPrefix.getPrefix().equals(getPrefix());
    }

    @Override
    public int hashCode() {
        return prefix.hashCode() + argValue.hashCode();
    }
}
