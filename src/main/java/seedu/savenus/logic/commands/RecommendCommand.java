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
            + "per meal: ";

    public static final String BUDGET_SET = "\n(assuming 2 meals per day and $%.2f over %d days)";

    public static final String BUDGET_DAYS_NOT_SET = "\n(You have not set a budget expiry date yet! Please do so to "
            + "ensure more accurate recommendations!)";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.setRecommendationSystemInUse(true);

        RecommendationSystem recInstance = RecommendationSystem.getInstance();

        if (recInstance.getDaysToExpire() == 0) {
            // User has not set daysToExpiry
            return new CommandResult(String.format(MESSAGE_SUCCESS + BUDGET_DAYS_NOT_SET,
                    RecommendationSystem.getInstance().getDailyBudget()));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS + BUDGET_SET,
                    RecommendationSystem.getInstance().getDailyBudget(),
                    RecommendationSystem.getInstance().getBudget(),
                    RecommendationSystem.getInstance().getDaysToExpire()));
        }


    }
}
