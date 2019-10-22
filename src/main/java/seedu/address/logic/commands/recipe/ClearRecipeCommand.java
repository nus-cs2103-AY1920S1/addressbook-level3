package seedu.address.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.recipe.RecipeBook;

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
