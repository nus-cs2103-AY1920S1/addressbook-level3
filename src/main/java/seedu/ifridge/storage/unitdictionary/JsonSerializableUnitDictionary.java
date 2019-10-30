package seedu.ifridge.storage.unitdictionary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.model.UnitDictionary;

import java.util.*;

/**
 * An Immutable waste archive that is serializable to JSON format.
 */
@JsonRootName(value = "unitdictionary")
public class JsonSerializableUnitDictionary {
    public static final String MESSAGE_DUPLICATE_ITEMS = "Dictionary contains duplicate items(s).";
    private final List<JsonAdaptedDictionaryItem> unitDictionary = new ArrayList<>();

    @JsonCreator
    public JsonSerializableUnitDictionary(@JsonProperty("unitdictionary")
                                                      List<JsonAdaptedDictionaryItem> unitDictionary) {
        this.unitDictionary.addAll(unitDictionary);
    }

    public JsonSerializableUnitDictionary(UnitDictionary source) {
        HashMap<String, String> sourceMap = source.getUnitDictionary();
        Set<String> names = sourceMap.keySet();
        for (String name : names) {
            JsonAdaptedDictionaryItem dictionaryItem = new JsonAdaptedDictionaryItem(name, sourceMap.get(name));
            unitDictionary.add(dictionaryItem);
        }
    }

    /**
     * Converts this unit dictionary into the model's unit dictionary.
     *
     * @throws IllegalValueException if there are any data constraints violated
     */
    public UnitDictionary toModelType() throws IllegalValueException {
        HashMap<String, String> modelMap = new HashMap<>();
        for (JsonAdaptedDictionaryItem jsonAdaptedDictionaryItem : unitDictionary) {
            String[] dictionaryItem = jsonAdaptedDictionaryItem.toModelType();
            if (modelMap.get(dictionaryItem[0]) != null) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEMS);
            }
            modelMap.put(dictionaryItem[0], dictionaryItem[1]);
        }
        UnitDictionary modelUnitDictionary = new UnitDictionary(modelMap);

        return modelUnitDictionary;
    }
}
