package seedu.algobase.model;

import java.util.UUID;

/**
 * Unique ID for AlgoBase models.
 */
public class Id {
    private final String idValue;

    public Id(String idValue) {
        this.idValue = idValue;
    }

    /**
     * Generate Id based on unix timestamp.
     */
    public static Id generateId() {
        return new Id(UUID.randomUUID().toString());
    }

    /**
     * Generate Id based on input value
     */
    public static Id generateId(String idValue) {
        return new Id(idValue);
    }

    public String getIdValue() {
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
        return idValue;
    }
}
