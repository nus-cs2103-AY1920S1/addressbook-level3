package dukecooks.storage.mealplan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanName;

/**
 * Jackson-friendly version of {@link MealPlan}.
 */
class JsonAdaptedMealPlan {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "MealPlan's %s field is missing!";

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedMealPlan} with the given recipe details.
     */
    @JsonCreator
    public JsonAdaptedMealPlan(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code MealPlan} into this class for Jackson use.
     */
    public JsonAdaptedMealPlan(MealPlan source) {
        name = source.getName().fullName;
    }

    /**
     * Converts this Jackson-friendly adapted recipe object into the model's {@code MealPlan} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted recipe.
     */
    public MealPlan toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MealPlanName.class.getSimpleName()));
        }
        if (!MealPlanName.isValidName(name)) {
            throw new IllegalValueException(MealPlanName.MESSAGE_CONSTRAINTS);
        }
        final MealPlanName modelName = new MealPlanName(name);

        return new MealPlan(modelName);
    }

}
