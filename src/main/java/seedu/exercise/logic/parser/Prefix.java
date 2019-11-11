package seedu.exercise.logic.parser;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. 'm/' in 'add Run m/Leg'.
 * <p>
 * The name of the prefix refers to the text before '/'.
 * E.g. the name of 'm/' is 'm'.
 */
public class Prefix {
    private final String prefix;

    public Prefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Returns the name of the prefix together with the "/".
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Returns the name of the prefix.
     */
    public String getPrefixName() {
        return prefix.substring(0, prefix.length() - 1);
    }

    public String toString() {
        return getPrefix();
    }

    @Override
    public int hashCode() {
        return prefix == null ? 0 : prefix.hashCode();
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
}
