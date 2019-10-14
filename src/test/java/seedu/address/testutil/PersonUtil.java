package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.ingredient.Ingredient;

/**
 * A utility class for Recipe.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code recipe}.
     */
    public static String getAddCommand(Recipe recipe) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(recipe);
    }

    /**
     * Returns the part of command string for the given {@code recipe}'s details.
     */
    public static String getPersonDetails(Recipe recipe) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + recipe.getName().fullName + " ");
        recipe.getIngredients().stream().forEach(
            s -> sb.append(PREFIX_INGREDIENT + s.ingredientName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
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
        return sb.toString();
    }
}
