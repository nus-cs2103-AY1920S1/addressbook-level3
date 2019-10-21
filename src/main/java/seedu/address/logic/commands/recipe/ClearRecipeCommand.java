package seedu.address.logic.commands.recipe;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.recipe.RecipeBook;

import static java.util.Objects.requireNonNull;

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
