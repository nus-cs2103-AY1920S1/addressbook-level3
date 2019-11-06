package seedu.address.logic.commands.topteamscommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TIE_BREAK;

import java.util.ArrayList;
import java.util.Comparator;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Team;

/**
 * Displays the top specified number of teams on the UI.
 */
public abstract class TopTeamsCommand extends Command {

    public static final String COMMAND_WORD = "getTop";
    public static final String INVALID_VALUE_WARNING = "The value of K inputted is not a valid positive integer.";
    public static final String MESSAGE_NO_TEAM = "Top Teams cannot be generated: no Teams present in Alfred. "
            + "\nAdd more Teams to use this command.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": shows the top K teams as the leaderboard stands"
            + " where K is an integer value you type in. Teams are sorted in "
            + "descending order of their score and tie-break methods used if specified.\n"
            + "Format: " + COMMAND_WORD + " K \n"
            + "Format (With Tiebreak): " + COMMAND_WORD + " K " + PREFIX_TIE_BREAK + "[Tie-break Methods]\n"
            + "For example: " + COMMAND_WORD + " 5 " + PREFIX_TIE_BREAK + "moreParticipants random";

    protected int numberOfTeams;
    protected ArrayList<Comparator<Team>> comparators;

    public TopTeamsCommand(int k, ArrayList<Comparator<Team>> comparators) {
        this.numberOfTeams = k;
        this.comparators = comparators;
    }

    /**
     * Checks if there are no teams currently added as per {@code model}.
     *
     * @throws CommandException if there are no teams added in Alfred.
     */
    public void checkNoTeams(Model model) throws CommandException {
        if (model.getTeamList().isEmpty()) {
            throw new CommandException(MESSAGE_NO_TEAM);
        }
    }
}
