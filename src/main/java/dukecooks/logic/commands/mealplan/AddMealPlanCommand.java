package dukecooks.logic.commands.mealplan;

import static dukecooks.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.AddCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.mealplan.components.MealPlan;

/**
 * Adds a recipe to Duke Cooks.
 */
public class AddMealPlanCommand extends AddCommand {

    public static final String VARIANT_WORD = "mealplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meal plan to Duke Cooks. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_INGREDIENT + "INGREDIENTS... ";

    public static final String MESSAGE_SUCCESS = "New meal plan added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This meal plan already exists in Duke Cooks";

    private final MealPlan toAdd;

    private static Event event;

    /**
     * Creates an AddRecipeCommand to add the specified {@code MealPlanBook}
     */
    public AddMealPlanCommand(MealPlan mealPlan) {
        requireNonNull(mealPlan);
        toAdd = mealPlan;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasMealPlan(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECIPE);
        }

        model.addMealPlan(toAdd);

        event = Event.getInstance();
        event.set("mealplan", "all");

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMealPlanCommand // instanceof handles nulls
                && toAdd.equals(((AddMealPlanCommand) other).toAdd));
    }
}
