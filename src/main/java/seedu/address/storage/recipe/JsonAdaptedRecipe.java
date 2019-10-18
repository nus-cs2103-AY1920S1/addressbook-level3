package seedu.address.storage.recipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Name;
import seedu.address.model.recipe.components.Calories;
import seedu.address.model.recipe.components.Carbs;
import seedu.address.model.recipe.components.Fats;
import seedu.address.model.recipe.components.Ingredient;
import seedu.address.model.recipe.components.Protein;
import seedu.address.model.recipe.components.Recipe;

/**
 * Jackson-friendly version of {@link Recipe}.
 */
class JsonAdaptedRecipe {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recipe's %s field is missing!";

    private final String name;
    private final String calories;
    private final String carbs;
    private final String fats;
    private final String protein;
    private final List<JsonAdaptedIngredient> ingredients = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRecipe} with the given recipe details.
     */
    @JsonCreator
    public JsonAdaptedRecipe(@JsonProperty("name") String name,
                             @JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients,
                             @JsonProperty("calories") String calories,
                             @JsonProperty("carbs") String carbs,
                             @JsonProperty("fats") String fats,
                             @JsonProperty("protein") String protein) {
        if (ingredients != null) {
            this.ingredients.addAll(ingredients);
        }
        this.name = name;
        this.calories = calories;
        this.carbs = carbs;
        this.fats = fats;
        this.protein = protein;
    }

    /**
     * Converts a given {@code Recipe} into this class for Jackson use.
     */
    public JsonAdaptedRecipe(Recipe source) {
        name = source.getName().fullName;
        calories = source.getCalories().value;
        carbs = source.getCarbs().value;
        fats = source.getFats().value;
        protein = source.getProtein().value;
        ingredients.addAll(source.getIngredients().stream()
                .map(JsonAdaptedIngredient::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted recipe object into the model's {@code Recipe} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted recipe.
     */
    public Recipe toModelType() throws IllegalValueException {
        final List<Ingredient> recipeIngredients = new ArrayList<>();
        for (JsonAdaptedIngredient ingredient : ingredients) {
            recipeIngredients.add(ingredient.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (calories == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Calories.class.getSimpleName()));
        }
        if (!Calories.isValidCalories(calories)) {
            throw new IllegalValueException(Calories.MESSAGE_CONSTRAINTS);
        }
        final Calories modelCalories = new Calories(calories);

        if (carbs == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Carbs.class.getSimpleName()));
        }
        if (!Carbs.isValidCarbs(carbs)) {
            throw new IllegalValueException(Carbs.MESSAGE_CONSTRAINTS);
        }
        final Carbs modelCarbs = new Carbs(carbs);

        if (fats == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Fats.class.getSimpleName()));
        }
        if (!Fats.isValidFats(fats)) {
            throw new IllegalValueException(Fats.MESSAGE_CONSTRAINTS);
        }
        final Fats modelFats = new Fats(fats);

        if (protein == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Protein.class.getSimpleName()));
        }
        if (!Protein.isValidProtein(protein)) {
            throw new IllegalValueException(Protein.MESSAGE_CONSTRAINTS);
        }
        final Protein modelProtein = new Protein(protein);

        final Set<Ingredient> modelIngredients = new HashSet<>(recipeIngredients);
        return new Recipe(modelName, modelIngredients, modelCalories, modelCarbs, modelFats, modelProtein);
    }

}
