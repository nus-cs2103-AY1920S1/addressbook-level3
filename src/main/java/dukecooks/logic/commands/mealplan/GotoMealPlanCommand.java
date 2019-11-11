package dukecooks.logic.commands.mealplan;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.GotoCommand;
import dukecooks.model.Model;

/**
 * Directs to Recipe in DukeCooks to the user.
 */
public class GotoMealPlanCommand extends GotoCommand {

    public static final String VARIANT_WORD = "mealplan";

    public static final String MESSAGE_SUCCESS = "You are now at your meal plans";

    private static Event event;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMealPlanList(Model.PREDICATE_SHOW_ALL_MEALPLANS);
        event = Event.getInstance();
        event.set("mealPlan", "all");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
