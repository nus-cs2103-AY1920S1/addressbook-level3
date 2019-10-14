package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.model.Model;

/**
 * Recommends food to the user.
 */
public class RecommendCommand extends Command {

    public static final String COMMAND_WORD = "recommend";

    public static final String MESSAGE_SUCCESS = "Here are your recommendations:";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.setRecommendationSystemInUse(true);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
