package dukecooks.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.GotoCommand;
import dukecooks.model.Model;

/**
 * Directs to Recipe in DukeCooks to the user.
 */
public class GotoRecipeCommand extends GotoCommand {

    public static final String VARIANT_WORD = "recipe";

    public static final String MESSAGE_SUCCESS = "You are now at the recipe book";

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
