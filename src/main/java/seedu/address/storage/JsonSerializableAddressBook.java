package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.food.Food;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_FOOD = "foods list contains duplicate food(s).";

    private final List<JsonAdaptedFood> foods = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given foods.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("foods") List<JsonAdaptedFood> foods) {
        this.foods.addAll(foods);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        foods.addAll(source.getFoodList().stream().map(JsonAdaptedFood::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedFood jsonAdaptedFood : foods) {
            Food food = jsonAdaptedFood.toModelType();
            if (addressBook.hasFood(food)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FOOD);
            }
            addressBook.addFood(food);
        }
        return addressBook;
    }

}
