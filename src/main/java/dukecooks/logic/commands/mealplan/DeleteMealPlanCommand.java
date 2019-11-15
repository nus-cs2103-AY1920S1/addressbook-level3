package dukecooks.logic.commands.mealplan;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.DeleteCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.mealplan.components.MealPlan;

/**
 * Deletes a meal plan identified using it's displayed index from Duke Cooks.
 */
public class DeleteMealPlanCommand extends DeleteCommand {

    public static final String VARIANT_WORD = "mealplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the meal plan identified by the index number used in the displayed meal plan list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + " 1";

    public static final String MESSAGE_DELETE_MEALPLAN_SUCCESS = "Deleted MealPlanBook: %1$s";

    private static Event event;

    private final Index targetIndex;

    public DeleteMealPlanCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<MealPlan> lastShownList = model.getFilteredMealPlanList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        MealPlan mealPlanToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteMealPlan(mealPlanToDelete);

        event = Event.getInstance();
        event.set("mealPlan", "all");

        return new CommandResult(String.format(MESSAGE_DELETE_MEALPLAN_SUCCESS, mealPlanToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMealPlanCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteMealPlanCommand) other).targetIndex)); // state check
    }
}
