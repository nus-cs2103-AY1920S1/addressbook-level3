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
    private final List<JsonAdaptedRecipeName> day1 = new ArrayList<>();
    private final List<JsonAdaptedRecipeName> day2 = new ArrayList<>();
    private final List<JsonAdaptedRecipeName> day3 = new ArrayList<>();
    private final List<JsonAdaptedRecipeName> day4 = new ArrayList<>();
    private final List<JsonAdaptedRecipeName> day5 = new ArrayList<>();
    private final List<JsonAdaptedRecipeName> day6 = new ArrayList<>();
    private final List<JsonAdaptedRecipeName> day7 = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMealPlan} with the given meal plan details.
     */
    @JsonCreator
    public JsonAdaptedMealPlan(@JsonProperty("name") String name,
                               @JsonProperty("day1") List<JsonAdaptedRecipeName> day1,
                               @JsonProperty("day2") List<JsonAdaptedRecipeName> day2,
                               @JsonProperty("day3") List<JsonAdaptedRecipeName> day3,
                               @JsonProperty("day4") List<JsonAdaptedRecipeName> day4,
                               @JsonProperty("day5") List<JsonAdaptedRecipeName> day5,
                               @JsonProperty("day6") List<JsonAdaptedRecipeName> day6,
                               @JsonProperty("day7") List<JsonAdaptedRecipeName> day7) {
        this.name = name;
        if (day1 != null) {
            this.day1.addAll(day1);
        }
        if (day2 != null) {
            this.day2.addAll(day2);
        }
        if (day3 != null) {
            this.day3.addAll(day3);
        }
        if (day4 != null) {
            this.day4.addAll(day4);
        }
        if (day5 != null) {
            this.day5.addAll(day5);
        }
        if (day6 != null) {
            this.day6.addAll(day6);
        }
        if (day7 != null) {
            this.day7.addAll(day7);
        }
    }

    /**
     * Converts a given {@code MealPlan} into this class for Jackson use.
     */
    public JsonAdaptedMealPlan(MealPlan source) {
        name = source.getName().fullName;
        day1.addAll(source.getDay1().stream()
                .map(JsonAdaptedRecipeName::new)
                .collect(Collectors.toList()));
        day2.addAll(source.getDay2().stream()
                .map(JsonAdaptedRecipeName::new)
                .collect(Collectors.toList()));
        day3.addAll(source.getDay3().stream()
                .map(JsonAdaptedRecipeName::new)
                .collect(Collectors.toList()));
        day4.addAll(source.getDay4().stream()
                .map(JsonAdaptedRecipeName::new)
                .collect(Collectors.toList()));
        day5.addAll(source.getDay5().stream()
                .map(JsonAdaptedRecipeName::new)
                .collect(Collectors.toList()));
        day6.addAll(source.getDay6().stream()
                .map(JsonAdaptedRecipeName::new)
                .collect(Collectors.toList()));
        day7.addAll(source.getDay7().stream()
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
        for (JsonAdaptedRecipeName recipe : this.day1) {
            day1.add(recipe.toModelType());
        }

        final List<RecipeName> day2 = new ArrayList<>();
        for (JsonAdaptedRecipeName recipe : this.day2) {
            day2.add(recipe.toModelType());
        }

        final List<RecipeName> day3 = new ArrayList<>();
        for (JsonAdaptedRecipeName recipe : this.day3) {
            day3.add(recipe.toModelType());
        }

        final List<RecipeName> day4 = new ArrayList<>();
        for (JsonAdaptedRecipeName recipe : this.day4) {
            day4.add(recipe.toModelType());
        }

        final List<RecipeName> day5 = new ArrayList<>();
        for (JsonAdaptedRecipeName recipe : this.day5) {
            day5.add(recipe.toModelType());
        }

        final List<RecipeName> day6 = new ArrayList<>();
        for (JsonAdaptedRecipeName recipe : this.day6) {
            day6.add(recipe.toModelType());
        }

        final List<RecipeName> day7 = new ArrayList<>();
        for (JsonAdaptedRecipeName recipe : this.day7) {
            day7.add(recipe.toModelType());
        }

        return new MealPlan(modelName, day1, day2, day3, day4, day5, day6, day7);
    }

}
