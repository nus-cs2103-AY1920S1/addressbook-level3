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
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

/**
 * Shows the full leaderboard as it currently stands based
 * on the teams' scores, with ties broken on random selection.
 */
public class LeaderboardWithRandomCommand extends LeaderboardCommand {

    public static final String MESSAGE_SUCCESS = "Showing Leaderboard as it Stands with Random Winners.";
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates a new {@code LeaderboardWithRandomCommand} object.
     *
     * @param comparators the comparators to be used for tiebreaks.
     * @param subjectName the subject name to be used for filtering the leaderboard by. Allows input to be null
     *                    if the user hasn't specified a subject to filter by.
     */
    public LeaderboardWithRandomCommand(ArrayList<Comparator<Team>> comparators, SubjectName subjectName) {
        super(comparators, subjectName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert comparators != null : "The comparators list should not be null";
        checkNoTeams(model);
        int numberOfTeams = model.getTeamList().list().size();
        model.setTopKRandom(numberOfTeams, comparators, subjectName);

        logger.info("Showing Leaderboard with Random Winners.");
        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(MESSAGE_SUCCESS, CommandType.L);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof LeaderboardWithRandomCommand
                && comparators.equals(((LeaderboardWithRandomCommand) other).comparators));
    }
}
