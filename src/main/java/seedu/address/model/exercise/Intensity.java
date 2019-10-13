package seedu.address.model.exercise;

public enum Intensity {
    HIGH,
    MEDIUM,
    LOW;

    public static final String MESSAGE_CONSTRAINTS =
            "Intensity should only include the integers 1, 2 or 3 and should not be blank";

    public static final String VALIDATION_REGEX = "[123]";

    public static boolean isValidIntensity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
