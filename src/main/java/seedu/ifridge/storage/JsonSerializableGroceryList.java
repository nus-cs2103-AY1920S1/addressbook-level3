package seedu.ifridge.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.ReadOnlyGroceryList;
import seedu.ifridge.model.food.GroceryItem;

/**
<<<<<<< HEAD
 * An Immutable GroceryList that is serializable to JSON format.
=======
 * An Immutable AddressBook that is serializable to JSON format.
>>>>>>> master
 */
@JsonRootName(value = "grocerylist")
class JsonSerializableGroceryList {

    public static final String MESSAGE_DUPLICATE_GROCERY_ITEM = "Grocery list contains duplicate grocery item(s)";

    private final List<JsonAdaptedGroceryItem> grocerylist = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableGroceryList(@JsonProperty("grocerylist") List<JsonAdaptedGroceryItem> grocerylist) {
        this.grocerylist.addAll(grocerylist);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableGroceryList(ReadOnlyGroceryList source) {
        grocerylist.addAll(source.getGroceryList().stream()
                .map(JsonAdaptedGroceryItem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public GroceryList toModelType() throws IllegalValueException {
        GroceryList groceryList = new GroceryList();
        for (JsonAdaptedGroceryItem jsonAdaptedGroceryItem : this.grocerylist) {
            GroceryItem groceryItem = jsonAdaptedGroceryItem.toModelType();
            if (groceryList.hasGroceryItem(groceryItem)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_GROCERY_ITEM);
            }
            groceryList.addGroceryItem(groceryItem);
        }
        return groceryList;
    }

}
