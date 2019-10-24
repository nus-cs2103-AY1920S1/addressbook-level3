package dukecooks.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import dukecooks.model.Model;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ListCommand;

/**
 * Lists all recipes in the Duke Cooks to the user.
 */
public class ListRecipeCommand extends ListCommand {

    public static final String VARIANT_WORD = "recipe";

    public static final String MESSAGE_SUCCESS = "Listed all recipes";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecipeList(Model.PREDICATE_SHOW_ALL_RECIPES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
