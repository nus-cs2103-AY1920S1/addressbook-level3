package dukecooks.logic.commands.mealplan;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.FindCommand;
import dukecooks.model.Model;
import dukecooks.model.mealplan.components.MealPlanNameContainsKeywordsPredicate;

/**
 * Finds and lists all meal plans in Duke Cooks whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindMealPlanCommand extends FindCommand {

    public static final String VARIANT_WORD = "mealplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all meal plans whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + " Vegetarian";

    private static Event event;

    private final MealPlanNameContainsKeywordsPredicate predicate;

    public FindMealPlanCommand(MealPlanNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMealPlanList(predicate);

        event = Event.getInstance();
        event.set("mealPlan", "all");

        return new CommandResult(
                String.format(Messages.MESSAGE_MEALPLAN_LISTED_OVERVIEW, model.getFilteredMealPlanList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMealPlanCommand // instanceof handles nulls
                && predicate.equals(((FindMealPlanCommand) other).predicate)); // state check
    }
}
