package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Prefix {
    private final String prefix;

    private ArrayList<String> commands = new ArrayList<>();

    public Prefix(String prefix, String... commands) {
        this.prefix = prefix;
        this.commands.addAll(Arrays.stream(commands).collect(Collectors.toList()));
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public String getPrefix() {
        return prefix;
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
