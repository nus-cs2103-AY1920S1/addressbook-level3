package seedu.address.testutil.recipe;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.recipe.EditRecipeCommand;
import seedu.address.logic.commands.recipe.EditRecipeCommand.EditRecipeDescriptor;
import seedu.address.model.common.Name;
import seedu.address.model.recipe.components.Calories;
import seedu.address.model.recipe.components.Carbs;
import seedu.address.model.recipe.components.Fats;
import seedu.address.model.recipe.components.Ingredient;
import seedu.address.model.recipe.components.Protein;
import seedu.address.model.recipe.components.Recipe;

/**
 * A utility class to help with building EditRecipeDescriptor objects.
 */
public class EditRecipeDescriptorBuilder {

    private EditRecipeCommand.EditRecipeDescriptor descriptor;

    public EditRecipeDescriptorBuilder() {
        descriptor = new EditRecipeCommand.EditRecipeDescriptor();
    }

    public EditRecipeDescriptorBuilder(EditRecipeCommand.EditRecipeDescriptor descriptor) {
        this.descriptor = new EditRecipeCommand.EditRecipeDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRecipeDescriptor} with fields containing {@code recipe}'s details
     */
    public EditRecipeDescriptorBuilder(Recipe recipe) {
        descriptor = new EditRecipeDescriptor();
        descriptor.setName(recipe.getName());
        descriptor.setIngredients(recipe.getIngredients());
        descriptor.setCalories(recipe.getCalories());
        descriptor.setCarbs(recipe.getCarbs());
        descriptor.setFats(recipe.getFats());
        descriptor.setProtein(recipe.getProtein());
    }

    /**
     * Sets the {@code Name} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Parses the {@code ingredients} into a {@code Set<Ingredient>} and set it to the {@code EditRecipeDescriptor}
     * that we are building.
     */
    public EditRecipeDescriptorBuilder withIngredients(String... ingredients) {
        Set<Ingredient> ingredientSet = Stream.of(ingredients).map(Ingredient::new).collect(Collectors.toSet());
        descriptor.setIngredients(ingredientSet);
        return this;
    }

    /**
     * Sets the {@code Calories} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withCalories(String calories) {
        descriptor.setCalories(new Calories(calories));
        return this;
    }

    /**
     * Sets the {@code Carbs} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withCarbs(String carbs) {
        descriptor.setCarbs(new Carbs(carbs));
        return this;
    }

    /**
     * Sets the {@code Fars} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withFats(String fats) {
        descriptor.setFats(new Fats(fats));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withProtein(String protein) {
        descriptor.setProtein(new Protein(protein));
        return this;
    }

    public EditRecipeDescriptor build() {
        return descriptor;
    }
}
