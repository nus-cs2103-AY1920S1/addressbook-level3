package dukecooks.storage.mealplan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.model.recipe.components.RecipeName;

/**
 * Jackson-friendly version of {@link MealPlan}.
 */
class JsonAdaptedMealPlan {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "MealPlan's %s field is missing!";

    private final String name;
    private final List<JsonAdaptedRecipeName> recipes1 = new ArrayList<>();
    private final List<JsonAdaptedRecipeName> recipes2 = new ArrayList<>();
    private final List<JsonAdaptedRecipeName> recipes3 = new ArrayList<>();
    private final List<JsonAdaptedRecipeName> recipes4 = new ArrayList<>();
    private final List<JsonAdaptedRecipeName> recipes5 = new ArrayList<>();
    private final List<JsonAdaptedRecipeName> recipes6 = new ArrayList<>();
    private final List<JsonAdaptedRecipeName> recipes7 = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMealPlan} with the given meal plan details.
     */
    @JsonCreator
    public JsonAdaptedMealPlan(@JsonProperty("name") String name,
                               @JsonProperty("day1") List<JsonAdaptedRecipeName> recipes1,
                               @JsonProperty("day2") List<JsonAdaptedRecipeName> recipes2,
                               @JsonProperty("day3") List<JsonAdaptedRecipeName> recipes3,
                               @JsonProperty("day4") List<JsonAdaptedRecipeName> recipes4,
                               @JsonProperty("day5") List<JsonAdaptedRecipeName> recipes5,
                               @JsonProperty("day6") List<JsonAdaptedRecipeName> recipes6,
                               @JsonProperty("day7") List<JsonAdaptedRecipeName> recipes7) {
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
                .map(JsonAdaptedRecipeName::new)
                .collect(Collectors.toList()));
        recipes2.addAll(source.getDay2().stream()
                .map(JsonAdaptedRecipeName::new)
                .collect(Collectors.toList()));
        recipes3.addAll(source.getDay3().stream()
                .map(JsonAdaptedRecipeName::new)
                .collect(Collectors.toList()));
        recipes4.addAll(source.getDay4().stream()
                .map(JsonAdaptedRecipeName::new)
                .collect(Collectors.toList()));
        recipes5.addAll(source.getDay5().stream()
                .map(JsonAdaptedRecipeName::new)
                .collect(Collectors.toList()));
        recipes6.addAll(source.getDay6().stream()
                .map(JsonAdaptedRecipeName::new)
                .collect(Collectors.toList()));
        recipes7.addAll(source.getDay7().stream()
                .map(JsonAdaptedRecipeName::new)
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

        final List<RecipeName> day1 = new ArrayList<>();
        for (JsonAdaptedRecipeName recipe : recipes1) {
            day1.add(recipe.toModelType());
        }

        final List<RecipeName> day2 = new ArrayList<>();
        for (JsonAdaptedRecipeName recipe : recipes2) {
            day2.add(recipe.toModelType());
        }

        final List<RecipeName> day3 = new ArrayList<>();
        for (JsonAdaptedRecipeName recipe : recipes3) {
            day3.add(recipe.toModelType());
        }

        final List<RecipeName> day4 = new ArrayList<>();
        for (JsonAdaptedRecipeName recipe : recipes4) {
            day4.add(recipe.toModelType());
        }

        final List<RecipeName> day5 = new ArrayList<>();
        for (JsonAdaptedRecipeName recipe : recipes5) {
            day5.add(recipe.toModelType());
        }

        final List<RecipeName> day6 = new ArrayList<>();
        for (JsonAdaptedRecipeName recipe : recipes6) {
            day6.add(recipe.toModelType());
        }

        final List<RecipeName> day7 = new ArrayList<>();
        for (JsonAdaptedRecipeName recipe : recipes7) {
            day7.add(recipe.toModelType());
        }

        return new MealPlan(modelName, day1, day2, day3, day4, day5, day6, day7);
    }

}
