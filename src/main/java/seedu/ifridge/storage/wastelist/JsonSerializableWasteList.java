package seedu.ifridge.storage.wastelist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.model.ReadOnlyWasteList;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.storage.JsonAdaptedGroceryItem;

/**
 * A WasteList that is serializable to JSON format.
 */
@JsonRootName(value = "wastelist")
public class JsonSerializableWasteList {

    private final List<JsonAdaptedGroceryItem> wastelist = new ArrayList<>();
    private final String wastemonth;

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableWasteList(@JsonProperty("wastemonth") String wasteMonth,
                                     @JsonProperty("wastelist") List<JsonAdaptedGroceryItem> wasteitems) {
        this.wastemonth = wasteMonth;
        this.wastelist.addAll(wasteitems);
    }

    /**
     * Converts a given {@code ReadOnlyWasteList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWasteList}.
     */
    public JsonSerializableWasteList(ReadOnlyWasteList source) {
        this.wastemonth = source.getWasteMonth().toStorageFormat();
        wastelist.addAll(source.getWasteList().stream().map(JsonAdaptedGroceryItem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this waste list into the model's {@code WasteList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public WasteList toModelType() throws IllegalValueException {
        WasteMonth wasteMonth = new WasteMonth(this.wastemonth);
        WasteList wasteList = new WasteList(wasteMonth);
        for (JsonAdaptedGroceryItem jsonAdaptedGroceryItem : this.wastelist) {
            GroceryItem food = jsonAdaptedGroceryItem.toModelType();
            wasteList.addWasteItem(food);
        }
        return wasteList;
    }
}
