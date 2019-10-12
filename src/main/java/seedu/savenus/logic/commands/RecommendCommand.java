package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.savenus.model.Model;
import seedu.savenus.model.food.Food;

/**
 * Recommends food to the user.
 */
public class RecommendCommand extends Command {

    public static final String COMMAND_WORD = "recommend";

    public static final String MESSAGE_SUCCESS = "Here are your recommendations:";

    // To be replaced with user's current budget
    private static final Predicate<Food> BUDGET_RECOMMENDATION = f -> Double.parseDouble(f.getPrice().value) < 5;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        /*
         * Currently this only sorts based on given budget (to be implemented later)
         * - Also filter out user's restrictions
         *
         * Positive bonuses:
         * 1. User purchased this food > 2 times
         * 2. User purchased food with the same category
         * 3. User purchased food with the same tags
         * 4. User purchased food with the same location
         * 5. User purchased food with the same dining hours
         * 6. User 'likes' this category/tag
         *
         * Negative penalties:
         * 1. User just purchased this food (maybe a decreasing exponential function from time purchased)
         * 2. User 'disliked' this category/tag
         */
        model.updateFilteredFoodList(BUDGET_RECOMMENDATION);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
