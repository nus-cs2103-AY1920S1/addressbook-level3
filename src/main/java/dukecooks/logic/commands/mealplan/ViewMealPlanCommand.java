package dukecooks.logic.commands.mealplan;

import static dukecooks.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ViewCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.mealplan.components.MealPlan;

/**
 * Views a mealplan in DukeCooks.
 */
public class ViewMealPlanCommand extends ViewCommand {

    public static final String VARIANT_WORD = "mealplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views a specific mealplan "
            + "Parameters: "
            + "INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + " 2";

    public static final String MESSAGE_SUCCESS = "You are viewing mealplan : %1$s";

    private static Event event;

    private final Index targetIndex;

    /**
     * Creates an ViewMealPlanCommand to view the specified MealPlan
     */
    public ViewMealPlanCommand(Index targetIndex) {
        requireAllNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<MealPlan> lastShownList = model.getFilteredMealPlanList();

        int index = targetIndex.getZeroBased();

        // check if index is out of bounds
        if (index >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEALPLAN_DISPLAYED_INDEX);
        }

        String type = "update-" + index;

        // Set your events here
        event = Event.getInstance();
        event.set("mealPlan", type);

        return new CommandResult(String.format(MESSAGE_SUCCESS, lastShownList.get(index).getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewMealPlanCommand // instanceof handles nulls
                && targetIndex.equals(((ViewMealPlanCommand) other).targetIndex));
    }
}
