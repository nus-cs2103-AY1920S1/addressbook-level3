package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Recipe objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";

    private Name name;
    private Set<Ingredient> ingredients;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        ingredients = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code recipeToCopy}.
     */
    public PersonBuilder(Recipe recipeToCopy) {
        name = recipeToCopy.getName();
        ingredients = new HashSet<>(recipeToCopy.getIngredients());
    }

    /**
     * Sets the {@code Name} of the {@code Recipe} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code ingredients} into a {@code Set<Ingredient>} and set it to the {@code Recipe} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.ingredients = SampleDataUtil.getIngredientSet(tags);
        return this;
    }


    public Recipe build() {
        return new Recipe(name, ingredients);
    }

}
