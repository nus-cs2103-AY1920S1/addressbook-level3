package seedu.address.logic.commands.leaderboardcommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TIE_BREAK;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.Predicates;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

/**
 * Displays the current leaderboard on the UI.
 */
public abstract class LeaderboardCommand extends Command {

    public static final String COMMAND_WORD = "leaderboard";
    public static final String MESSAGE_NO_TEAM = "Leaderboard cannot be generated: no Teams present in Alfred. "
            + "\nAdd more Teams to view Leaderboard.";
    public static final String MESSAGE_NO_TEAM_SUBJECT = "Leaderboard cannot be generated: no Teams present"
            + " in Alfred for category %s. "
            + "\nAdd more Teams to use this command.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": displays the leaderboard as it currently stands, "
            + "with teams sorted in "
            + " descending order of their score and tie-break methods used if specified. \n"
            + "Format: " + COMMAND_WORD + "\n"
            + "Format (For Tie-break): " + COMMAND_WORD + " " + PREFIX_TIE_BREAK + "[Tie-break Methods]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TIE_BREAK + "moreParticipants random";

    protected ArrayList<Comparator<Team>> comparators;
    protected SubjectName subjectName;

    private final Logger logger = LogsCenter.getLogger(getClass());

    public LeaderboardCommand(ArrayList<Comparator<Team>> comparators, SubjectName subjectName) {
        this.comparators = comparators;
        this.subjectName = subjectName;
    }

    /**
     * Checks if there are no teams currently added as per {@code model} and if a subject is specified
     * then whether there are any teams with the particular subject present.
     *
     * @throws CommandException if there are no teams added to Alfred.
     */
    public void checkNoTeams(Model model) throws CommandException {
        if (model.getTeamList().isEmpty()) {
            logger.severe("No teams stored within Alfred.");
            throw new CommandException(MESSAGE_NO_TEAM);
        }
        if (subjectName != null) {
            FilteredList<Team> teamList = new FilteredList<>(model.getFilteredTeamList());
            teamList.setPredicate(Predicates.getPredicateFilterTeamBySubject(subjectName).negate());
            if (teamList.size() == 0) {
                logger.severe("No teams within Alfred with Subject: " + subjectName.toString());
                throw new CommandException(String.format(MESSAGE_NO_TEAM_SUBJECT, subjectName));
            }
        }
    }

}
