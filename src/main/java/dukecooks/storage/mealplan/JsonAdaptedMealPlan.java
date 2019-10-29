package dukecooks.storage.mealplan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.storage.recipe.JsonAdaptedRecipe;

/**
 * Jackson-friendly version of {@link MealPlan}.
 */
class JsonAdaptedMealPlan {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "MealPlan's %s field is missing!";

    private final String name;
    private final List<JsonAdaptedRecipe> recipes1 = new ArrayList<>();
    private final List<JsonAdaptedRecipe> recipes2 = new ArrayList<>();
    private final List<JsonAdaptedRecipe> recipes3 = new ArrayList<>();
    private final List<JsonAdaptedRecipe> recipes4 = new ArrayList<>();
    private final List<JsonAdaptedRecipe> recipes5 = new ArrayList<>();
    private final List<JsonAdaptedRecipe> recipes6 = new ArrayList<>();
    private final List<JsonAdaptedRecipe> recipes7 = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMealPlan} with the given meal plan details.
     */
    @JsonCreator
    public JsonAdaptedMealPlan(@JsonProperty("name") String name,
                               @JsonProperty("day1") List<JsonAdaptedRecipe> recipes1,
                               @JsonProperty("day2") List<JsonAdaptedRecipe> recipes2,
                               @JsonProperty("day3") List<JsonAdaptedRecipe> recipes3,
                               @JsonProperty("day4") List<JsonAdaptedRecipe> recipes4,
                               @JsonProperty("day5") List<JsonAdaptedRecipe> recipes5,
                               @JsonProperty("day6") List<JsonAdaptedRecipe> recipes6,
                               @JsonProperty("day7") List<JsonAdaptedRecipe> recipes7) {
        this.name = name;
        if (recipes1 != null) {
            this.recipes1.addAll(recipes1);
        }
        if (recipes2 != null) {
            this.recipes2.addAll(recipes2);
        }
        if (recipes3 != null) {
            this.recipes3.addAll(recipes3);
        }
        if (recipes4 != null) {
            this.recipes4.addAll(recipes4);
        }
        if (recipes5 != null) {
            this.recipes5.addAll(recipes5);
        }
        if (recipes6 != null) {
            this.recipes6.addAll(recipes6);
        }
        if (recipes7 != null) {
            this.recipes7.addAll(recipes7);
        }
    }

    /**
     * Converts a given {@code MealPlan} into this class for Jackson use.
     */
    public JsonAdaptedMealPlan(MealPlan source) {
        name = source.getName().fullName;
        recipes1.addAll(source.getDay1().stream()
                .map(JsonAdaptedRecipe::new)
                .collect(Collectors.toList()));
        recipes2.addAll(source.getDay2().stream()
                .map(JsonAdaptedRecipe::new)
                .collect(Collectors.toList()));
        recipes3.addAll(source.getDay3().stream()
                .map(JsonAdaptedRecipe::new)
                .collect(Collectors.toList()));
        recipes4.addAll(source.getDay4().stream()
                .map(JsonAdaptedRecipe::new)
                .collect(Collectors.toList()));
        recipes5.addAll(source.getDay5().stream()
                .map(JsonAdaptedRecipe::new)
                .collect(Collectors.toList()));
        recipes6.addAll(source.getDay6().stream()
                .map(JsonAdaptedRecipe::new)
                .collect(Collectors.toList()));
        recipes7.addAll(source.getDay7().stream()
                .map(JsonAdaptedRecipe::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted meal plan object into the model's {@code MealPlan} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meal plan.
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

        final List<Recipe> day1 = new ArrayList<>();
        for (JsonAdaptedRecipe recipe : recipes1) {
            day1.add(recipe.toModelType());
        }

        final List<Recipe> day2 = new ArrayList<>();
        for (JsonAdaptedRecipe recipe : recipes2) {
            day2.add(recipe.toModelType());
        }

        final List<Recipe> day3 = new ArrayList<>();
        for (JsonAdaptedRecipe recipe : recipes3) {
            day3.add(recipe.toModelType());
        }

        final List<Recipe> day4 = new ArrayList<>();
        for (JsonAdaptedRecipe recipe : recipes4) {
            day4.add(recipe.toModelType());
        }

        final List<Recipe> day5 = new ArrayList<>();
        for (JsonAdaptedRecipe recipe : recipes5) {
            day5.add(recipe.toModelType());
        }

        final List<Recipe> day6 = new ArrayList<>();
        for (JsonAdaptedRecipe recipe : recipes6) {
            day6.add(recipe.toModelType());
        }

        final List<Recipe> day7 = new ArrayList<>();
        for (JsonAdaptedRecipe recipe : recipes7) {
            day7.add(recipe.toModelType());
        }

        return new MealPlan(modelName, day1, day2, day3, day4, day5, day6, day7);
    }

}
