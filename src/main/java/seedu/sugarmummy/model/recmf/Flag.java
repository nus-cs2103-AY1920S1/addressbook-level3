package seedu.sugarmummy.model.recmf;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

/**
 * Marks the option flags for selective food recommendations. E.g. '-m' in 'recmf -m' to only show meal
 * recommendations.
 */
public class Flag {

    public static final String FLAG_SIGNAL = "-";
    public static final String MESSAGE_CONSTRAINTS = "Flag should begin with \"" + FLAG_SIGNAL + "\" and be followed by"
            + "the following food types: nsv, sv, f, p, s, m";

    private static final String VALIDATION_REGEX = "^" + FLAG_SIGNAL + "[a-zA-Z]+";

    private final String flag;

    public Flag(String flag) {
        requireNonNull(flag);
        checkArgument(isValidFlag(flag), MESSAGE_CONSTRAINTS);
        this.flag = flag;
    }

    public static boolean isValidFlag(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getFlag() {
        return flag;
    }

    /**
     * Returns a string of the flag but without the leading flag signal.
     */
    public String getFlagContent() {
        return flag.substring(1);
    }

    public String toString() {
        return getFlag();
    }

    @Override
    public int hashCode() {
        return flag == null ? 0 : flag.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Flag)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Flag other = (Flag) obj;
        return other.getFlag().equals(this.getFlag());
    }
}
