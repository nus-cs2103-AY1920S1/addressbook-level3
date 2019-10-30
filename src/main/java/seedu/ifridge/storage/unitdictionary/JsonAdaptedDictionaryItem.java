package seedu.ifridge.storage.unitdictionary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedDictionaryItem {

    private final String name;
    private final String unitType;

    /**
     * Constructs a {@code JsonAdaptedDictionaryItem} with the given {@code name} and {@code unitType}.
     */
    @JsonCreator
    public JsonAdaptedDictionaryItem(String name, String unitType) {
        this.name = name;
        this.unitType = unitType;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonValue
    public String getUnitType() {
        return unitType;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public String[] toModelType() throws IllegalValueException {
        String[] dictionaryItem = {name, unitType};
        return dictionaryItem;
    }

}
