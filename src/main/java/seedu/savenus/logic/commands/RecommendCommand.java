package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.model.Model;
import seedu.savenus.model.recommend.RecommendationSystem;

//@@author jon-chua
/**
 * Recommends food to the user.
 */
public class RecommendCommand extends Command {

    public static final String COMMAND_WORD = "recommend";

    public static final String MESSAGE_SUCCESS = "Here are your recommendations, based on your budget of $%.2f "
        + "per meal (assuming 2 meals per day and $%.2f over %d days):";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.setRecommendationSystemInUse(true);
        return new CommandResult(String.format(MESSAGE_SUCCESS, RecommendationSystem.getInstance().getDailyBudget(),
                RecommendationSystem.getInstance().getBudget(), RecommendationSystem.getInstance().getDaysToExpire()));
    }
}
