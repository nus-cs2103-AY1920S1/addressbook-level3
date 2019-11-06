package seedu.address.logic.commands.leaderboardcommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TIE_BREAK;

import java.util.ArrayList;
import java.util.Comparator;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Team;

/**
 * Displays the current leaderboard on the UI.
 */
public abstract class LeaderboardCommand extends Command {

    public static final String COMMAND_WORD = "leaderboard";
    public static final String MESSAGE_NO_TEAM = "Leaderboard cannot be generated: no Teams present in Alfred. "
            + "\nAdd more Teams to view Leaderboard.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": displays the leaderboard as it currently stands, "
            + "with teams sorted in "
            + " descending order of their score and tie-break methods used if specified. \n"
            + "Format: " + COMMAND_WORD + "\n"
            + "Format (For Tie-break): " + COMMAND_WORD + " " + PREFIX_TIE_BREAK + "[Tie-break Methods]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TIE_BREAK + "moreParticipants random";

    protected ArrayList<Comparator<Team>> comparators;

    public LeaderboardCommand(ArrayList<Comparator<Team>> comparators) {
        this.comparators = comparators;
    }

    /**
     * Checks if there are no teams currently added as per {@code model}.
     *
     * @throws CommandException if there are no teams added to Alfred.
     */
    public void checkNoTeams(Model model) throws CommandException {
        if (model.getTeamList().isEmpty()) {
            throw new CommandException(MESSAGE_NO_TEAM);
        }
    }

}
