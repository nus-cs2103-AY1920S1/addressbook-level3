package seedu.address.logic.parser;

/**
 * Marks the option flags for selective food recommendations.
 * E.g. '-m' in 'recmf -m' to only show meal recommendations.
 */
public class Flag {
    private final String flag;

    public Flag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
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
