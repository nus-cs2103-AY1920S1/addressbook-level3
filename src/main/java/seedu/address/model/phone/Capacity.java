package seedu.address.model.phone;

/**
 * Represents a Phone's memory capacity in the SML.
 */
public enum Capacity {
    SIZE_8GB("8GB"),
    SIZE_16GB("16GB"),
    SIZE_32GB("32GB"),
    SIZE_64GB("64GB"),
    SIZE_128GB("128GB"),
    SIZE_256GB("256GB"),
    SIZE_512GB("512GB"),
    SIZE_1024GB("1024GB");

    public final String value;

    Capacity(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
