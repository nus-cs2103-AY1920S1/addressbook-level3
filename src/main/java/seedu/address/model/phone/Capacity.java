package seedu.address.model.phone;

import java.util.ArrayList;
import java.util.List;

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

    public static final String MESSAGE_CONSTRAINTS =
            "Capacity should only be these numbers { 8, 16, 32, 64, 128, 256, 512, 1024 }. ";

    public static final String VALIDATION_REGEX = "\\d{1,}";
    public static final List<String> VALID_CAPACITY_STRINGS =
            new ArrayList<>(List.of("8", "16", "32", "64", "128", "256", "512", "1024"));

    public final String value;

    Capacity(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    /**
     * Returns true if a given string is a valid capacity.
     */
    public static boolean isValidCapacity(String test) {
        return VALID_CAPACITY_STRINGS
                .stream()
                .anyMatch(str -> str.equals(test));

    }
}
