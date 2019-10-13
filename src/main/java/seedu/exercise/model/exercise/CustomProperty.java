package seedu.exercise.model.exercise;

import static seedu.exercise.commons.core.ValidationRegex.ONLY_ALPHABETS;
import static seedu.exercise.commons.core.ValidationRegex.ONLY_ALPHABETS_AND_SPACE;
import static seedu.exercise.commons.util.AppUtil.checkArgument;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.exercise.logic.parser.Prefix;

/**
 * Represents a custom property that an {@code Exercise} object can have.
 */
public class CustomProperty {
    public static final String FULL_NAME_CONSTRAINTS = "Full names should contain only alphabets and should"
        + " not be blank.";
    public static final String SHORT_NAME_CONSTRAINTS = "Short names should contain only alphabets and should"
        + " have no spaces and should not be blank.";

    private final Prefix prefix;
    private final String fullName;
    private final ParameterType parameterType;

    /**
     * Instantiates a new {@code CustomProperty} instance.
     *
     * @param fullName      the full name of the custom property
     * @param prefix        the prefix for the custom property
     * @param parameterType the string used to validate an input for the custom property
     */
    public CustomProperty(Prefix prefix, String fullName, ParameterType parameterType) {
        requireAllNonNull(fullName, prefix, parameterType);
        checkArgument(isValidFullName(fullName), FULL_NAME_CONSTRAINTS);
        checkArgument(isValidShortName(prefix.getPrefixName()), SHORT_NAME_CONSTRAINTS);
        this.fullName = fullName;
        this.prefix = prefix;
        this.parameterType = parameterType;
    }

    /**
     * Checks if the given full name is valid.
     *
     * @param test the full name of a custom property
     * @return true if and only if the full name contains only alphabets
     */
    public static boolean isValidFullName(String test) {
        return test.matches(ONLY_ALPHABETS_AND_SPACE);
    }

    /**
     * Checks if the given short name is valid. The short name of a custom property refers to the text before
     * '/' in its prefix.
     *
     * @param test the short name of a custom property
     * @return true if and only if the short name contains only alphabets and does not contain any whitespaces.
     */
    public static boolean isValidShortName(String test) {
        return test.matches(ONLY_ALPHABETS);
    }

    /**
     * Retrieves the full name of the {@code CustomProperty} instance.
     */
    public String getFullName() {
        return this.fullName;
    }

    /**
     * Retrieves the prefix of the {@code CustomProperty} instance.
     *
     * @return a {@code Prefix} object that represents the prefix of the instance
     */
    public Prefix getPrefix() {
        return this.prefix;
    }

    /**
     * Retrieves the parameter type of the {@code CustomProperty} instance.
     *
     * @return a {@code ParameterType} object that represents the parameter type of the instance
     */
    public ParameterType getParameterType() {
        return this.parameterType;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CustomProperty)) {
            return false;
        }

        CustomProperty otherProperty = (CustomProperty) other;
        return fullName.equals(otherProperty.fullName)
            && prefix.equals(otherProperty.prefix)
            && parameterType.equals(otherProperty.parameterType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, prefix, parameterType);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" Full Name: ")
            .append(fullName)
            .append(" Short Name: ")
            .append(prefix.getPrefixName());
        return builder.toString();
    }
}
