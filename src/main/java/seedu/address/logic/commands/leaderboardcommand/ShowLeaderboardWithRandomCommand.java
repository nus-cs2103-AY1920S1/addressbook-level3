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
 * Shows the full leaderboard as it currently stands based
 * on the teams' scores, with ties broken on random selection.
 */
public class ShowLeaderboardWithRandomCommand extends LeaderboardCommand {

    public static final String MESSAGE_SUCCESS = "Showing Leaderboard as it Stands with Random Winners.";
    private final Logger logger = LogsCenter.getLogger(getClass());

    public ShowLeaderboardWithRandomCommand(ArrayList<Comparator<Team>> comparators) {
        super(comparators);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert comparators != null : "The comparators list should not be null";
        checkNoTeams(model);
        int numberOfTeams = model.getTeamList().list().size();
        model.setTopKRandom(numberOfTeams, comparators);

        logger.info("Showing Leaderboard with Random Winners.");
        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(MESSAGE_SUCCESS, CommandType.L);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ShowLeaderboardWithRandomCommand
                && comparators.equals(((ShowLeaderboardWithRandomCommand) other).comparators));
    }
}
