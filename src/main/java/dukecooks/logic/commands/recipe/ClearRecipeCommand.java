package dukecooks.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import dukecooks.logic.commands.ClearCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.model.Model;
import dukecooks.model.recipe.RecipeBook;

/**
 * Clears Duke Cooks.
 */
public class ClearRecipeCommand extends ClearCommand {

    public static final String VARIANT_WORD = "recipe";
    public static final String MESSAGE_SUCCESS = "Recipe Book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setRecipeBook(new RecipeBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
