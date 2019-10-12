package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.function.Predicate;

import seedu.savenus.model.Model;
import seedu.savenus.model.food.Food;

/**
 * Recommends food to the user.
 */
public class RecommendCommand extends Command {

    public static final String COMMAND_WORD = "recommend";

    public static final String MESSAGE_SUCCESS = "Here are your recommendations:";

    // Placeholder, to be replaced with user's current budget
    private static final Predicate<Food> BUDGET_RECOMMENDATION = f -> Double.parseDouble(f.getPrice().value) < 50;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        /*
         * Currently this only filters based on given budget and sorts them by price.
         * The recommendations can be cleared using the list command.
         */
        model.updateFilteredFoodList(BUDGET_RECOMMENDATION);
        model.updateComparator(Comparator.comparingDouble(x -> Double.parseDouble(x.getPrice().value)));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
