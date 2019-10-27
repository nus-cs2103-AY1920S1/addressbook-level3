package seedu.algobase.model;

/**
 * Unique ID for AlgoBase models.
 */
public class Id {
    private final Long idValue;

    private Id(long idValue) {
        this.idValue = idValue;
    }

    /**
     * Generate Id based on unix timestamp.
     */
    public static Id generateId() {
        return new Id(System.currentTimeMillis() / 1000L);
    }

    /**
     * Generate Id based on unix timestamp.
     */
    public static Id generateId(long idValue) {
        return new Id(idValue);
    }

    public long getIdValue() {
        return this.idValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Id // instanceof handles nulls
            && idValue.equals(((Id) other).idValue)); // state check
    }

    @Override
    public int hashCode() {
        return idValue.hashCode();
    }

    @Override
    public String toString() {
        return Long.toString(this.getIdValue());
    }
}
