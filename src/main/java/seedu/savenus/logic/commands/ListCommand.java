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

        // Reset the predicate and comparator, if any was provided
        model.updateFilteredFoodList(Model.PREDICATE_SHOW_ALL_FOOD);
        model.resetRecommendationComparator();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
