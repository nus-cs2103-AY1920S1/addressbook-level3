package mams.logic.parser;

import static java.util.Objects.requireNonNull;

/**
 * Class representing an option. Options are defined by a starting dash,
 * followed by a sequence of characters representing the option name.
 * eg. "-ho", "-c".
 *
 * The difference between a prefix and an option is that an option
 * is only used when we do not care about associating the option with any value -
 * rather, the only thing that matters is if the option is present/specified within the command.
 */
public class Option {
    public static final String DASH = "-";

    private final String fullOptLabel;
    private final String optName;

    public Option(String optName) {
        requireNonNull(optName);
        this.optName = optName;
        this.fullOptLabel = DASH + optName;
    }

    public Option(Option opt) {
        requireNonNull(opt);
        this.optName = opt.optName;
        this.fullOptLabel = opt.getFullOptLabel();
    }

    /**
     * Checks if {@code arg} is a valid option, ie. it is prepended by a '-' character, and
     * is is then proceeded by non-whitespace characters.
     * @param arg argument to be checked
     * @return true if it is a valid option, false otherwise.
     */
    public static boolean isValidOption(String arg) {
        return arg.startsWith(DASH) && arg.length() > 1 && !arg.substring(1, 2).isBlank();
    }

    public String getOptionNameOnly() {
        return optName;
    }

    public String getFullOptLabel() {
        return fullOptLabel;
    }

    @Override
    public String toString() {
        return getFullOptLabel();
    }

    @Override
    public int hashCode() {
        // we only use the full label to hashcode since
        // fullOptName simply adds preceding dash.
        return optName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Option)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Option otherOpt = (Option) obj;
        return otherOpt.getOptionNameOnly().equals(getOptionNameOnly());
    }
}
