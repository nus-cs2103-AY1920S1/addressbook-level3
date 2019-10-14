package seedu.address.model.exercise;

public enum Intensity {
    HIGH,
    MEDIUM,
    LOW;

    public static final String MESSAGE_CONSTRAINTS =
            "Intensity should only include the integers 1, 2, 3 or strings low, medium and high"
                    + " and should not be blank";

    public static final String VALIDATION_REGEX = "\b(1|2|3|low|medium|high)\b";

    public static boolean isValidIntensity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
