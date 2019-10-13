package seedu.exercise.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.logic.parser.Prefix;
import seedu.exercise.model.exercise.CustomProperty;
import seedu.exercise.model.exercise.ParameterType;

/**
 * Jackson-friendly version of {@link CustomProperty};
 */
public class JsonAdaptedCustomProperty {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CustomProperty's %s field is missing!";

    private final String shortName;
    private final String fullName;
    private final String parameterType;

    /**
     * Constructs a {@code JsonAdaptedCustomProperty} with the given custom property details.
     */
    @JsonCreator
    public JsonAdaptedCustomProperty(@JsonProperty("shortName") String shortName,
                                     @JsonProperty("fullName") String fullName,
                                     @JsonProperty("parameterType") String parameterType) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.parameterType = parameterType;
    }

    /**
     * Constructs a given {@code CustomProperty} into this class for Jackson use.
     */
    public JsonAdaptedCustomProperty(CustomProperty source) {
        this.shortName = source.getPrefix().getPrefixName();
        this.fullName = source.getFullName();
        this.parameterType = source.getParameterType().getParameterName();
    }

    /**
     * Converts the short name of a Jackson-friendly adapted custom property object into its corresponding
     * model's {@code Prefix} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted custom property
     */
    public CustomProperty toModelType() throws IllegalValueException {
        final Prefix modelShortName = toModelShortName();
        final String modelFullName = toModelFullName();
        final ParameterType modelParameterType = toModelParameterType();
        return new CustomProperty(modelShortName, modelFullName, modelParameterType);
    }

    /**
     * Converts the short name of a Jackson-friendly adapted custom property object into its corresponding
     * model's {@code Prefix} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted custom property
     */
    private Prefix toModelShortName() throws IllegalValueException {
        if (shortName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Prefix.class.getSimpleName()));
        }
        if (!CustomProperty.isValidShortName(shortName)) {
            throw new IllegalValueException(CustomProperty.SHORT_NAME_CONSTRAINTS);
        }
        return new Prefix(shortName + "/");
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
