package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditRecipeCommand;
import seedu.address.logic.commands.EditRecipeCommand.EditRecipeDescriptor;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.ingredient.Ingredient;

/**
 * A utility class to help with building EditRecipeDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditRecipeCommand.EditRecipeDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditRecipeCommand.EditRecipeDescriptor();
    }

    public EditPersonDescriptorBuilder(EditRecipeCommand.EditRecipeDescriptor descriptor) {
        this.descriptor = new EditRecipeCommand.EditRecipeDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRecipeDescriptor} with fields containing {@code recipe}'s details
     */
    public EditPersonDescriptorBuilder(Recipe recipe) {
        descriptor = new EditRecipeDescriptor();
        descriptor.setName(recipe.getName());
        descriptor.setIngredients(recipe.getIngredients());
    }

    /**
     * Sets the {@code Name} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Ingredient>} and set it to the {@code EditRecipeDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Ingredient> ingredientSet = Stream.of(tags).map(Ingredient::new).collect(Collectors.toSet());
        descriptor.setIngredients(ingredientSet);
        return this;
    }

    public EditRecipeDescriptor build() {
        return descriptor;
    }
}
