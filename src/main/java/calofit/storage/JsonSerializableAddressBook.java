package calofit.storage;

import calofit.commons.exceptions.IllegalValueException;
import calofit.model.AddressBook;
import calofit.model.ReadOnlyAddressBook;
import calofit.model.meal.Dish;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_DISHES = "Dishes list contains duplicate dish(s).";

    private final List<JsonAdaptedDish> dishes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("dishes") List<JsonAdaptedDish> dishes) {
        this.dishes.addAll(dishes);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        dishes.addAll(source.getDishList().stream().map(JsonAdaptedDish::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedDish jsonAdaptedDish : dishes) {
            Dish dish = jsonAdaptedDish.toModelType();
            if (addressBook.hasDish(dish)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DISHES);
            }
            addressBook.addDish(dish);
        }
        return addressBook;
    }

}
