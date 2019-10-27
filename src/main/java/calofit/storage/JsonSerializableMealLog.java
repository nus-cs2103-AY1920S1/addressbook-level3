package calofit.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import calofit.commons.exceptions.IllegalValueException;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.model.meal.ReadOnlyMealLog;

/**
 * An Immutable MealLog that is serializable to JSON format.
 */
@JsonRootName(value = "meals")
public class JsonSerializableMealLog {

    public static final String MESSAGE_DUPLICATE_MEALS = "Meals list contains duplicate meal(s).";

    private final List<JsonAdaptedMeal> meals = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMealLog} with the given meals.
     */
    @JsonCreator
    public JsonSerializableMealLog(@JsonProperty("meals") List<JsonAdaptedMeal> meals) {
        this.meals.addAll(meals);
    }

    /**
     * Converts a given {@code ReadOnlyMealLog} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMealLog}.
     */
    public JsonSerializableMealLog(ReadOnlyMealLog source) {
        meals.addAll(source.getMealLog().stream().map(JsonAdaptedMeal::new).collect(Collectors.toList()));
    }

    /**
     * Converts this meallog into the model's {@code MealLog} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public MealLog toModelType() throws IllegalValueException {
        MealLog mealLog = new MealLog();
        for (JsonAdaptedMeal jsonAdaptedMeal : meals) {
            Meal meal = jsonAdaptedMeal.toModelType();
            if (mealLog.hasMeal(meal)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEALS);
            }
            mealLog.addMeal(meal);
        }
        return mealLog;
    }

}
