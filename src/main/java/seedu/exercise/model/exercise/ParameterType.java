package seedu.exercise.model.exercise;

import static seedu.exercise.commons.core.ValidationRegex.ONLY_ALPHABETS_AND_SPACE;
import static seedu.exercise.commons.core.ValidationRegex.ONLY_NUMBERS;

/**
 * Encapsulates the different parameter types that a custom property can take in.
 */
public enum ParameterType {
    TEXT("Text"),
    DATE("Date"),
    NUMBER("Number");

    public static final String PARAMETER_CONSTRAINTS = "Parameter types should be one of the following: Number, "
        + "Date or Text";
    public static final String TEXT_CONSTRAINTS = "Text should contain only alphabets and should not be blank.";
    public static final String NUMBER_CONSTRAINTS = "Number should contain only numbers and should not be blank.";
    private final String parameterName;

    ParameterType(String parameterName) {
        this.parameterName = parameterName;
    }

    /**
     * Checks if the given {@code String string} is a valid parameter type.
     *
     * @param string the parameter type to be validated
     * @return true if and only if the given string is a valid parameter type
     */
    public static boolean isValidParameterType(String string) {
        return string.equals(TEXT.parameterName)
            || string.equals(DATE.parameterName)
            || string.equals(NUMBER.parameterName);
    }

    /**
     * Checks if the given {@code String value} is a valid text for the parameter type {@code Text}.
     * {@code Text} requires the value to contain only alphabets.
     *
     * @param value the value to be checked
     * @return true if and only if the the given value is a valid text
     */
    public static boolean isValidText(String value) {
        return value.matches(ONLY_ALPHABETS_AND_SPACE);
    }

    /**
     * Checks if the given {@code String value} is a valid value for the parameter type {@code Number}.
     * {@code Number} requires the value to contain only numbers.
     *
     * @param value the value to be checked
     * @return true if and only if the given value is a valid number
     */
    public static boolean isValidNumber(String value) {
        return value.matches(ONLY_NUMBERS);
    }

    /**
     * Returns the parameter name of the {@code ParameterType} instance.
     *
     * @return a string representing the name of the {@code ParameterType} instance
     */
    public String getParameterName() {
        return this.parameterName;
    }

}
