package seedu.deliverymans.storage.restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.database.RestaurantDatabase;
import seedu.deliverymans.model.database.ReadOnlyRestaurantDatabase;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * An Immutable RestaurantDatabase that is serializable to JSON format.
 */
@JsonRootName(value = "restaurantdatabase")
public class JsonSerializableRestaurantDatabase {
    public static final String MESSAGE_DUPLICATE_RESTAURANT = "Restaurants list contains duplicate restaurant(s).";

    private final List<JsonAdaptedRestaurant> restaurants = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRestaurantDatabase} with the given restaurants.
     */
    @JsonCreator
    public JsonSerializableRestaurantDatabase(@JsonProperty("restaurants") List<JsonAdaptedRestaurant> restaurants) {
        this.restaurants.addAll(restaurants);
    }

    /**
     * Converts a given {@code ReadOnlyRestaurantDatabase} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRestaurantDatabase}.
     */
    public JsonSerializableRestaurantDatabase(ReadOnlyRestaurantDatabase source) {
        restaurants.addAll(source.getRestaurantList().stream().map(JsonAdaptedRestaurant::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code RestaurantDatabase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public RestaurantDatabase toModelType() throws IllegalValueException {
        RestaurantDatabase restaurantDatabase = new RestaurantDatabase();
        for (JsonAdaptedRestaurant jsonAdaptedRestaurant : restaurants) {
            Restaurant restaurant = jsonAdaptedRestaurant.toModelType();
            if (restaurantDatabase.hasRestaurant(restaurant)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RESTAURANT);
            }
            restaurantDatabase.addRestaurant(restaurant);
        }
        return restaurantDatabase;
    }
}
