package seedu.address.storage.wastelist;

import java.time.LocalDate;
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
import seedu.address.model.waste.WasteMonth;
import seedu.address.storage.JsonAdaptedFood;

/**
 * A WasteList that is serializable to JSON format.
 */
@JsonRootName(value = "wastelist")
public class JsonSerializableWasteList {

    private final List<JsonAdaptedFood> wasteList = new ArrayList<>();
    private final String wasteMonthString;

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableWasteList(@JsonProperty("wastemonth") String wasteMonth,
                                     @JsonProperty("wastelist") List<JsonAdaptedFood> wasteitems) {
        this.wasteMonthString = wasteMonth;
        this.wasteList.addAll(wasteitems);
    }

    /**
     * Converts a given {@code ReadOnlyWasteList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWasteList}.
     */
    public JsonSerializableWasteList(ReadOnlyWasteList source) {
        this.wasteMonthString = source.getWasteMonth().toStorageFormat();
        wasteList.addAll(source.getWasteList().stream().map(JsonAdaptedFood::new).collect(Collectors.toList()));
    }

    /**
     * Converts this waste list into the model's {@code WasteList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public WasteList toModelType() throws IllegalValueException {
        
        WasteMonth wasteMonth = new WasteMonth(this.wasteMonthString);
        WasteList wasteList = new WasteList(wasteMonth);
        for (JsonAdaptedFood jsonAdaptedFood : this.wasteList) {
            GroceryItem food = jsonAdaptedFood.toModelType();
            wasteList.addWasteItem(food);
        }
        return wasteList;
    }
}
