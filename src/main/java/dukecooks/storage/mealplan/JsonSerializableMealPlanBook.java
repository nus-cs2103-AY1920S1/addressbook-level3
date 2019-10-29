package dukecooks.storage.mealplan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;
import dukecooks.model.mealplan.components.MealPlan;

/**
 * An Immutable Exercise Catalogue that is serializable to JSON format.
 */
@JsonRootName(value = "mealplanbook")
class JsonSerializableMealPlanBook {

    public static final String MESSAGE_DUPLICATE_MEALPLAN = "Meal Plans list contains duplicate meal plan(s).";

    private final List<JsonAdaptedMealPlan> mealPlans = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMealPlanBook} with the given meal plans.
     */
    @JsonCreator
    public JsonSerializableMealPlanBook(@JsonProperty("mealPlans") List<JsonAdaptedMealPlan> mealPlans) {
        this.mealPlans.addAll(mealPlans);
    }

    /**
    * Converts a given {@code ReadOnlyMealPlanBook} into this class for Jackson use.
    *
    * @param source future changes to this will not affect the created {@code JsonSerializableMealPlanBook}.
    */
    public JsonSerializableMealPlanBook(ReadOnlyMealPlanBook source) {
        mealPlans.addAll(source.getMealPlanList().stream().map(JsonAdaptedMealPlan::new).collect(Collectors.toList()));
    }

    /**
    *  Converts this Exercise Catalogue into the model's {@code MealPlanBook} object.
    *
    * @throws IllegalValueException if there were any data constraints violated.
    */
    public MealPlanBook toModelType() throws IllegalValueException {
        MealPlanBook mealPlanBook = new MealPlanBook();
        for (JsonAdaptedMealPlan jsonAdaptedMealPlan : mealPlans) {
            MealPlan mealPlan = jsonAdaptedMealPlan.toModelType();
            if (mealPlanBook.hasMealPlan(mealPlan)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEALPLAN);
            }
            mealPlanBook.addMealPlan(mealPlan);
        }
        return mealPlanBook;
    }
}
