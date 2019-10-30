package dukecooks.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ListCommand;
import dukecooks.model.Model;

/**
 * Lists all recipes in the Duke Cooks to the user.
 */
public class ListRecipeCommand extends ListCommand {

    public static final String VARIANT_WORD = "recipe";

    public static final String MESSAGE_SUCCESS = "Listed all recipes";

    private static Event event;


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecipeList(Model.PREDICATE_SHOW_ALL_RECIPES);

        event = Event.getInstance();
        event.set("recipe", "all");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
