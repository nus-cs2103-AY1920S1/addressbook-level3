package dukecooks.testutil.recipe;

import java.util.Set;

import dukecooks.logic.commands.recipe.AddRecipeCommand;
import dukecooks.logic.commands.recipe.EditRecipeCommand;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.model.recipe.components.Recipe;

/**
 * A utility class for Recipe.
 */
public class RecipeUtil {

    /**
     * Returns an add command string for adding the {@code recipe}.
     */
    public static String getAddRecipeCommand(Recipe recipe) {
        return AddRecipeCommand.COMMAND_WORD + " " + AddRecipeCommand.VARIANT_WORD + " " + getRecipeDetails(recipe);
    }

    /**
     * Returns the part of command string for the given {@code recipe}'s details.
     */
    public static String getRecipeDetails(Recipe recipe) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + recipe.getName().fullName + " ");
        recipe.getIngredients().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_INGREDIENT + s.ingredientName + " ")
        );
        sb.append(CliSyntax.PREFIX_CALORIES + recipe.getCalories().value + " ");
        sb.append(CliSyntax.PREFIX_CARBS + recipe.getCarbs().value + " ");
        sb.append(CliSyntax.PREFIX_FATS + recipe.getFats().value + " ");
        sb.append(CliSyntax.PREFIX_PROTEIN + recipe.getProtein().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditRecipeDescriptor}'s details.
     */
    public static String getEditRecipeDescriptorDetails(EditRecipeCommand.EditRecipeDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name.fullName).append(" "));
        if (descriptor.getIngredientsToAdd().isPresent()) {
            Set<Ingredient> ingredients = descriptor.getIngredientsToAdd().get();
            if (ingredients.isEmpty()) {
                sb.append(CliSyntax.PREFIX_INGREDIENT);
            } else {
                ingredients.forEach(s -> sb.append(CliSyntax.PREFIX_INGREDIENT).append(s.ingredientName).append(" "));
            }
        }
        descriptor.getCalories().ifPresent(calories -> sb.append(CliSyntax.PREFIX_CALORIES).append(calories.value)
                .append(" "));
        descriptor.getCarbs().ifPresent(carbs -> sb.append(CliSyntax.PREFIX_CARBS).append(carbs.value).append(" "));
        descriptor.getFats().ifPresent(fats -> sb.append(CliSyntax.PREFIX_FATS).append(fats.value).append(" "));
        descriptor.getProtein().ifPresent(protein -> sb.append(CliSyntax.PREFIX_PROTEIN).append(protein.value)
                .append(" "));
        return sb.toString();
    }
}
