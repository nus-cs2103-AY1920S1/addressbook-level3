package seedu.address.logic.parser;

import java.util.NoSuchElementException;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Prefix {
    private final String prefix;

    public Prefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    /**
     * Get the Class Name that the prefix represents.
     *
     * @return Class Name.
     */
    public String getPrefixClass() {
        String className = CliSyntax.PREFIX_CLASS_MAP.get(prefix);
        if (className == null) {
            throw new NoSuchElementException("This prefix is not representing any class.");
        }
        return className;
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
