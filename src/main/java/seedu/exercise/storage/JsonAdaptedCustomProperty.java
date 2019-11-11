package seedu.exercise.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.logic.parser.Prefix;
import seedu.exercise.model.property.custom.CustomProperty;
import seedu.exercise.model.property.custom.ParameterType;

/**
 * Jackson-friendly version of {@link CustomProperty};
 */
public class JsonAdaptedCustomProperty {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CustomProperty's %s field is missing!";

    private final String prefixName;
    private final String fullName;
    private final String parameterType;

    /**
     * Constructs a {@code JsonAdaptedCustomProperty} with the given custom property details.
     */
    @JsonCreator
    public JsonAdaptedCustomProperty(@JsonProperty("prefixName") String prefixName,
                                     @JsonProperty("fullName") String fullName,
                                     @JsonProperty("parameterType") String parameterType) {
        this.prefixName = prefixName;
        this.fullName = fullName;
        this.parameterType = parameterType;
    }

    /**
     * Constructs a given {@code CustomProperty} into this class for Jackson use.
     */
    public JsonAdaptedCustomProperty(CustomProperty source) {
        this.prefixName = source.getPrefix().getPrefixName();
        this.fullName = source.getFullName();
        this.parameterType = source.getParameterType().getParameterName();
    }

    /**
     * Converts a Jackson-friendly adapted custom property object into its corresponding
     * model's {@code CustomProperty} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted custom property
     */
    public CustomProperty toModelType() throws IllegalValueException {
        final Prefix modelPrefix = toModelPrefix();
        final String modelFullName = toModelFullName();
        final ParameterType modelParameterType = toModelParameterType();
        return new CustomProperty(modelPrefix, modelFullName, modelParameterType);
    }

    /**
     * Converts the prefix name of a Jackson-friendly adapted custom property object into its corresponding
     * model's {@code Prefix} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted custom property
     */
    private Prefix toModelPrefix() throws IllegalValueException {
        if (prefixName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Prefix.class.getSimpleName()));
        }
        if (!CustomProperty.isValidPrefixName(prefixName)) {
            throw new IllegalValueException(CustomProperty.PREFIX_NAME_CONSTRAINTS);
        }
        return new Prefix(prefixName + "/");
    }

    /**
     * Converts the full name of a Jackson-friendly adapted custom property object into its corresponding
     * model's name.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted custom property
     */
    private String toModelFullName() throws IllegalValueException {
        String fullNameField = "Full Name";
        if (fullName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, fullNameField));
        }
        if (!CustomProperty.isValidFullName(fullName)) {
            throw new IllegalValueException(CustomProperty.FULL_NAME_CONSTRAINTS);
        }
        return fullName.trim();
    }

    /**
     * Converts the parameter type of a Jackson-friendly adapted custom property object into its corresponding
     * model's {@code ParameterType} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted custom property
     */
    private ParameterType toModelParameterType() throws IllegalValueException {
        if (parameterType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ParameterType.class.getSimpleName()));
        }

        if (!ParameterType.isValidParameterType(parameterType)) {
            throw new IllegalValueException(ParameterType.PARAMETER_CONSTRAINTS);
        }

        if (parameterType.equals(ParameterType.NUMBER.getParameterName())) {
            return ParameterType.NUMBER;
        } else if (parameterType.equals(ParameterType.TEXT.getParameterName())) {
            return ParameterType.TEXT;
        } else {
            return ParameterType.DATE;
        }
    }
}
