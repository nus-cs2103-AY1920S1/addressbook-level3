package seedu.ifridge.storage.unitdictionary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    public JsonAdaptedDictionaryItem(@JsonProperty("name") String name, @JsonProperty("unitType") String unitType) {
        this.name = name;
        this.unitType = unitType;
    }

    /**
     * Converts this Jackson-friendly adapted dictionaryItem object into string array to be inserted into the model's
     * dictionary map.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public String[] toModelType() throws IllegalValueException {
        String[] dictionaryItem = {name, unitType};
        return dictionaryItem;
    }

}
