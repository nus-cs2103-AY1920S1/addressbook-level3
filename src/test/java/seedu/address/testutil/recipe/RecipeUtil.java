package seedu.address.testutil.recipe;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CARBS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FATS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROTEIN;

import java.util.Set;

import seedu.address.logic.commands.recipe.AddRecipeCommand;
import seedu.address.logic.commands.recipe.EditRecipeCommand.EditRecipeDescriptor;
import seedu.address.model.recipe.components.Ingredient;
import seedu.address.model.recipe.components.Recipe;

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
        sb.append(PREFIX_NAME + recipe.getName().fullName + " ");
        recipe.getIngredients().stream().forEach(
            s -> sb.append(PREFIX_INGREDIENT + s.ingredientName + " ")
        );
        sb.append(PREFIX_CALORIES + recipe.getCalories().value + " ");
        sb.append(PREFIX_CARBS + recipe.getCarbs().value + " ");
        sb.append(PREFIX_FATS + recipe.getFats().value + " ");
        sb.append(PREFIX_PROTEIN + recipe.getProtein().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditRecipeDescriptor}'s details.
     */
    public static String getEditRecipeDescriptorDetails(EditRecipeDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        if (descriptor.getIngredients().isPresent()) {
            Set<Ingredient> ingredients = descriptor.getIngredients().get();
            if (ingredients.isEmpty()) {
                sb.append(PREFIX_INGREDIENT);
            } else {
                ingredients.forEach(s -> sb.append(PREFIX_INGREDIENT).append(s.ingredientName).append(" "));
            }
        }
        descriptor.getCalories().ifPresent(calories -> sb.append(PREFIX_CALORIES).append(calories.value).append(" "));
        descriptor.getCarbs().ifPresent(carbs -> sb.append(PREFIX_CARBS).append(carbs.value).append(" "));
        descriptor.getFats().ifPresent(fats -> sb.append(PREFIX_FATS).append(fats.value).append(" "));
        descriptor.getProtein().ifPresent(protein -> sb.append(PREFIX_PROTEIN).append(protein.value).append(" "));
        return sb.toString();
    }
}
