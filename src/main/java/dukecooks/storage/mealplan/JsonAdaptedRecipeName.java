package dukecooks.storage.mealplan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.recipe.components.RecipeName;

/**
 * Jackson-friendly version of {@link RecipeName}.
 */
class JsonAdaptedRecipeName {

    private final String recipeName;

    /**
     * Constructs a {@code JsonAdaptedRecipeName} with the given {@code recipeName}.
     */
    @JsonCreator
    public JsonAdaptedRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    /**
     * Converts a given {@code RecipeName} into this class for Jackson use.
     */
    public JsonAdaptedRecipeName(RecipeName source) {
        recipeName = source.fullName;
    }

    @JsonValue
    public String getRecipeNameName() {
        return recipeName;
    }

    /**
     * Converts this Jackson-friendly adapted ingredient object into the model's {@code RecipeName} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ingredient.
     */
    public RecipeName toModelType() throws IllegalValueException {
        if (!RecipeName.isValidName(recipeName)) {
            throw new IllegalValueException(RecipeName.MESSAGE_CONSTRAINTS);
        }
        return new RecipeName(recipeName);
    }

}
