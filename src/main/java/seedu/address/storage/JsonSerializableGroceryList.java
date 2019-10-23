package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.GroceryList;
import seedu.address.model.ReadOnlyGroceryList;
import seedu.address.model.food.GroceryItem;

/**
 * An Immutable GroceryList that is serializable to JSON format.
 */
@JsonRootName(value = "grocerylist")
class JsonSerializableGroceryList {

    public static final String MESSAGE_DUPLICATE_GROCERY_ITEM = "Grocery list contains duplicate grocery item(s).";

    private final List<JsonAdaptedFood> groceryItems = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableGroceryList(@JsonProperty("groceryItems") List<JsonAdaptedFood> groceryItems) {
        this.groceryItems.addAll(groceryItems);
    }

    /**
     * Converts a given {@code ReadOnlyGroceryList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableGroceryList(ReadOnlyGroceryList source) {
        groceryItems.addAll(source.getGroceryList().stream().map(JsonAdaptedFood::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public GroceryList toModelType() throws IllegalValueException {
        GroceryList groceryList = new GroceryList();
        for (JsonAdaptedFood jsonAdaptedFood : groceryItems) {
            GroceryItem food = jsonAdaptedFood.toModelType();
            if (groceryList.hasPerson(food)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_GROCERY_ITEM);
            }
            groceryList.addPerson(food);
        }
        return groceryList;
    }

}
