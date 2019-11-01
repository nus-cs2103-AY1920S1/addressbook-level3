package seedu.sugarmummy.recmfood.model;

/**
 * Marks the option flags for selective food recommendations. E.g. '-m' in 'recmf -m' to only show meal
 * recommendations.
 */
public class Flag {

    private static final String VALIDATION_REGEX = "^-[a-zA-Z]+";

    private final String flag;

    public Flag(String flag) {
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
