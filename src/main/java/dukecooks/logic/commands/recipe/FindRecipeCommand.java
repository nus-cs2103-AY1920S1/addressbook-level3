package dukecooks.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.FindCommand;
import dukecooks.model.Model;
import dukecooks.model.recipe.components.RecipeNameContainsKeywordsPredicate;

/**
 * Finds and lists all recipes in Duke Cooks whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindRecipeCommand extends FindCommand {

    public static final String VARIANT_WORD = "recipe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all recipes whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + " chicken noodle";

    private static Event event;

    private final RecipeNameContainsKeywordsPredicate predicate;

    public FindRecipeCommand(RecipeNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecipeList(predicate);

        event = Event.getInstance();
        event.set("recipe", "all");

        return new CommandResult(
                String.format(Messages.MESSAGE_RECIPE_LISTED_OVERVIEW, model.getFilteredRecipeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindRecipeCommand // instanceof handles nulls
                && predicate.equals(((FindRecipeCommand) other).predicate)); // state check
    }
}
