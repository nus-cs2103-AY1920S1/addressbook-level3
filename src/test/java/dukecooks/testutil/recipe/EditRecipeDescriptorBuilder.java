package dukecooks.testutil.recipe;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dukecooks.logic.commands.recipe.EditRecipeCommand;
import dukecooks.model.recipe.components.Calories;
import dukecooks.model.recipe.components.Carbs;
import dukecooks.model.recipe.components.Fats;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.model.recipe.components.Protein;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.RecipeName;

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
        descriptor = new EditRecipeCommand.EditRecipeDescriptor();
        descriptor.setName(recipe.getName());
        descriptor.addIngredients(recipe.getIngredients());
        descriptor.setCalories(recipe.getCalories());
        descriptor.setCarbs(recipe.getCarbs());
        descriptor.setFats(recipe.getFats());
        descriptor.setProtein(recipe.getProtein());
    }

    /**
     * Returns an {@code EditRecipeDescriptor} with fields containing {@code recipe}'s details
     */
    public EditRecipeDescriptorBuilder(Recipe recipeFrom, Recipe recipeTo) {
        descriptor = new EditRecipeCommand.EditRecipeDescriptor();
        descriptor.setName(recipeTo.getName());
        descriptor.addIngredients(recipeTo.getIngredients());
        descriptor.removeIngredients(recipeFrom.getIngredients());
        descriptor.setCalories(recipeTo.getCalories());
        descriptor.setCarbs(recipeTo.getCarbs());
        descriptor.setFats(recipeTo.getFats());
        descriptor.setProtein(recipeTo.getProtein());
    }

    /**
     * Sets the {@code Name} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withRecipeName(String name) {
        descriptor.setName(new RecipeName(name));
        return this;
    }

    /**
     * Parses the {@code ingredients} to be added into a {@code Set<Ingredient>}
     * and set it to the {@code EditRecipeDescriptor}
     * that we are building.
     */
    public EditRecipeDescriptorBuilder withIngredientsToAdd(String... ingredients) {
        Set<Ingredient> ingredientSet = Stream.of(ingredients).map(Ingredient::new).collect(Collectors.toSet());
        descriptor.addIngredients(ingredientSet);
        return this;
    }

    /**
     * Parses the {@code ingredients} for removal into a {@code Set<Ingredient>}
     * and set it to the {@code EditRecipeDescriptor}
     * that we are building.
     */
    public EditRecipeDescriptorBuilder withIngredientsToRemove(String... ingredients) {
        Set<Ingredient> ingredientSet = Stream.of(ingredients).map(Ingredient::new).collect(Collectors.toSet());
        descriptor.removeIngredients(ingredientSet);
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

    public EditRecipeCommand.EditRecipeDescriptor build() {
        return descriptor;
    }
}
