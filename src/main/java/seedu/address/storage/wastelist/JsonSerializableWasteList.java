package seedu.address.storage.wastelist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyWasteList;
import seedu.address.model.WasteList;
import seedu.address.model.food.GroceryItem;
import seedu.address.storage.JsonAdaptedFood;

/**
 * An Immutable WasteList that is serializable to JSON format.
 */
@JsonRootName(value = "wastelist")
public class JsonSerializableWasteList {

    private final List<JsonAdaptedFood> wasteList = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableWasteList(@JsonProperty("wasteitems") List<JsonAdaptedFood> wasteitems) {
        // This class will be modified such that the field @JsonProperty will say the waste month
        this.wasteList.addAll(wasteitems);
    }

    /**
     * Converts a given {@code ReadOnlyWasteList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWasteList}.
     */
    public JsonSerializableWasteList(ReadOnlyWasteList source) {
        wasteList.addAll(source.getWasteList().stream().map(JsonAdaptedFood::new).collect(Collectors.toList()));
    }

    /**
     * Converts this waste list into the model's {@code WasteList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public WasteList toModelType() throws IllegalValueException {
        WasteList wasteList = new WasteList();
        for (JsonAdaptedFood jsonAdaptedFood : this.wasteList) {
            GroceryItem food = jsonAdaptedFood.toModelType();
            wasteList.addWasteItem(food);
        }
        return wasteList;
    }
}
