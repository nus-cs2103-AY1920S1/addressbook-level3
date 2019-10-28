package dukecooks.logic.commands.mealplan;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.ClearCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.model.Model;
import dukecooks.model.mealplan.MealPlanBook;

/**
 * Clears Duke Cooks.
 */
public class ClearMealPlanCommand extends ClearCommand {

    public static final String VARIANT_WORD = "mealplan";
    public static final String MESSAGE_SUCCESS = "Meal Plan Book has been cleared!";

    private static Event event;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setMealPlanBook(new MealPlanBook());

        event = Event.getInstance();
        event.set("mealPlan", "all");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
