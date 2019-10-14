package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.model.Model;

/**
 * Lists all food in the menu to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all food";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Clear the recommendation system (if it was used) and show all food items
        model.updateFilteredFoodList(x -> true);
        model.setRecommendationSystemInUse(false);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
