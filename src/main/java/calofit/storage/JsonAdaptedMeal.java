package calofit.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import calofit.commons.exceptions.IllegalValueException;
import calofit.model.dish.Dish;
import calofit.model.meal.Meal;
import calofit.model.util.Timestamp;

/**
 * Jackson-friendly version of {@link Meal}.
 */
public class JsonAdaptedMeal {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meal's %s field is missing!";

    private final JsonAdaptedDish dish;
    private final String time;

    /**
     * Constructs a {@code JsonAdaptedMeal} with the given meal details.
     */
    @JsonCreator
    public JsonAdaptedMeal(@JsonProperty("dish") JsonAdaptedDish dish,
                           @JsonProperty("time") String time) {
        this.dish = dish;
        this.time = time;
    }

    /**
     * Converts a given {@code Meal} into this class for Jackson use.
     */
    public JsonAdaptedMeal(Meal source) {
        dish = new JsonAdaptedDish(source.getDish());
        time = source.getTimestamp().getDateTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted meal object into the model's {@code Meal} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meal.
     */
    public Meal toModelType() throws IllegalValueException {

        if (dish == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Dish.class.getSimpleName()));
        }

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Timestamp.class.getSimpleName()));
        }

        final Dish modelDish = dish.toModelType();

        final Timestamp modelTime = new Timestamp(LocalDateTime.parse(time));

        return new Meal(modelDish, modelTime);
    }

}
