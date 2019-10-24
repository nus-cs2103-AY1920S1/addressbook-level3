package seedu.exercise.testutil;

import seedu.exercise.logic.parser.Prefix;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.model.property.ParameterType;

/**
 * A utility class to help with building CustomProperty objects.
 */
public class CustomPropertyBuilder {

    private static final String DEFAULT_PREFIX = "r";
    private static final String DEFAULT_FULL_NAME = "Rating";
    private static final String DEFAULT_PARAMETER_TYPE = "Number";

    private Prefix prefix;
    private String fullName;
    private ParameterType parameterType;

    public CustomPropertyBuilder() {
        this.withPrefix(DEFAULT_PREFIX);
        this.withFullName(DEFAULT_FULL_NAME);
        this.withParameterType(DEFAULT_PARAMETER_TYPE);
    }

    public CustomPropertyBuilder(CustomProperty customPropertyToCopy) {
        prefix = customPropertyToCopy.getPrefix();
        fullName = customPropertyToCopy.getFullName();
        parameterType = customPropertyToCopy.getParameterType();
    }

    /**
     * Sets the {@code Prefix} of the {@code CustomProperty} that we are building.
     */
    public CustomPropertyBuilder withPrefix(String prefixName) {
        assert(!prefixName.equals(""));
        this.prefix = new Prefix(prefixName + "/");
        return this;
    }

    /**
     * Sets the full name of the {@code CustomProperty} that we are building.
     */
    public CustomPropertyBuilder withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    /**
     * Sets the {@code ParameterType} of the {@code CustomProperty} that we are building.
     */
    public CustomPropertyBuilder withParameterType(String parameterType) {
        switch (parameterType) {
        case ("Number"):
            this.parameterType = ParameterType.NUMBER;
            return this;

        case ("Text"):
            this.parameterType = ParameterType.TEXT;
            return this;

        case ("Date"):
            this.parameterType = ParameterType.DATE;
            return this;

        default:
            throw new AssertionError("Invalid parameterType");
        }
    }

    public CustomProperty build() {
        return new CustomProperty(prefix, fullName, parameterType);
    }

}
