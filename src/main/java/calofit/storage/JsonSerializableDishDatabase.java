package calofit.storage;

import calofit.commons.exceptions.IllegalValueException;
import calofit.model.dish.DishDatabase;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.dish.Dish;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable DishDatabase that is serializable to JSON format.
 */
@JsonRootName(value = "dishes")
class JsonSerializableDishDatabase {

    public static final String MESSAGE_DUPLICATE_DISHES = "Dishes list contains duplicate dish(s).";

    private final List<JsonAdaptedDish> dishes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableDishDatabase} with the given persons.
     */
    @JsonCreator
    public JsonSerializableDishDatabase(@JsonProperty("dishes") List<JsonAdaptedDish> dishes) {
        this.dishes.addAll(dishes);
    }

    /**
     * Converts a given {@code ReadOnlyDishDatabase} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableDishDatabase}.
     */
    public JsonSerializableDishDatabase(ReadOnlyDishDatabase source) {
        dishes.addAll(source.getDishList().stream().map(JsonAdaptedDish::new).collect(Collectors.toList()));
    }

    /**
     * Converts this dish database into the model's {@code DishDatabase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DishDatabase toModelType() throws IllegalValueException {
        DishDatabase dishDatabase = new DishDatabase();
        for (JsonAdaptedDish jsonAdaptedDish : dishes) {
            Dish dish = jsonAdaptedDish.toModelType();
            if (dishDatabase.hasDish(dish)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DISHES);
            }
            dishDatabase.addDish(dish);
        }
        return dishDatabase;
    }

}
