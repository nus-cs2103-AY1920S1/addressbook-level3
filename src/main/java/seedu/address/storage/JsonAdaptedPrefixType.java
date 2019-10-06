package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entity.PrefixType;

/**
 * JsonAdaptedPrefixType is the JSON representation of {@code PrefixType}.
 */
public class JsonAdaptedPrefixType {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Prefix field is missing!";

    private final String prefixType;

    @JsonCreator
    public JsonAdaptedPrefixType(@JsonProperty("prefixType") String prefixType) {
        this.prefixType = prefixType;
    }

    public JsonAdaptedPrefixType(PrefixType source) {
        prefixType = source.toStorageValue();
    }

    /**
     * This converts the JSON Adapted PrefixType to the actual {@code PrefixType} Object.
     *
     * @return
     * @throws IllegalValueException
     */
    public PrefixType toModelType() throws IllegalValueException {
        if (prefixType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }
        if (!PrefixType.isValidPrefixType(prefixType)) {
            throw new IllegalValueException(PrefixType.MESSAGE_CONSTRAINTS);
        }

        final PrefixType modelPrefixType = PrefixType.valueOf(prefixType);

        return modelPrefixType;

    }
}
