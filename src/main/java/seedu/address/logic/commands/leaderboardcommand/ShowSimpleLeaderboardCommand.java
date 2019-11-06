package seedu.address.logic.commands.leaderboardcommand;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Team;

/**
 * Shows the full leader board as it currently stands based
 * on the teams' scores.
 */
public class ShowSimpleLeaderboardCommand extends LeaderboardCommand {

    public static final String MESSAGE_SUCCESS = "Showing Leaderboard as it Stands.";
    private final Logger logger = LogsCenter.getLogger(getClass());

    public ShowSimpleLeaderboardCommand(ArrayList<Comparator<Team>> comparators) {
        super(comparators);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert comparators != null : "The comparators list should not be null";
        checkNoTeams(model);
        model.setSimpleLeaderboard(comparators);

        logger.info("Showing Leaderboard.");
        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(MESSAGE_SUCCESS, CommandType.L);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowSimpleLeaderboardCommand // instanceof handles nulls
                && comparators.equals(((ShowSimpleLeaderboardCommand) other).comparators));
    }
}
