package calofit.storage;

import calofit.commons.exceptions.IllegalValueException;
import calofit.model.AddressBook;
import calofit.model.ReadOnlyAddressBook;
import calofit.model.meal.Meal;
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

    public static final String MESSAGE_DUPLICATE_MEALS = "Meals list contains duplicate meal(s).";

    private final List<JsonAdaptedMeal> meals = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("meals") List<JsonAdaptedMeal> meals) {
        this.meals.addAll(meals);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        meals.addAll(source.getMealList().stream().map(JsonAdaptedMeal::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedMeal jsonAdaptedMeal : meals) {
            Meal meal = jsonAdaptedMeal.toModelType();
            if (addressBook.hasMeal(meal)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEALS);
            }
            addressBook.addMeal(meal);
        }
        return addressBook;
    }

}
