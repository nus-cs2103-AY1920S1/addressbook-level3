package seedu.system.logic.commands.insession;

import static java.util.Objects.requireNonNull;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.exceptions.OutOfSessionCommandException;
import seedu.system.model.Model;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Name;
import seedu.system.model.person.Person;

/**
 * Lists the position of an athlete for a specific competition.
 */
public class RankCommand extends Command {
    public static final String COMMAND_WORD = "rank";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": lists an athlete's position for a competition. "
            + "Parameters: "
            + PREFIX_NAME + "ATHLETE NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John ";
    public static final String MESSAGE_COMPETITION_NOT_FOUND = "The competition with the given name does not exist : ";
    public static final String MESSAGE_PARTICIPATION_NOT_FOUND =
        "The competition in session does not have a participation of an athlete with the given name: ";
    public static final String MESSAGE_SUCCESS = " Athlete: %1$s Competition: %2$s \n Rank: %3$d \n Total Score: %4$d "
            + "\n Max Squat: %6$s \n Max Bench Press: %5$s \n Max Deadlift: %7$s ";

    private final Name athleteName;
    //private final MethodOfRanking methodOfRanking;

    /**
     * Creates a RankCommand which lists an athlete's position for a competition
     * @param athleteName name of athlete
     */
    public RankCommand(Name athleteName) {
        requireNonNull(athleteName);
        //requireNonNull(lift);
        this.athleteName = athleteName;
        //this.methodOfRanking = methodOfRanking;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    @Override
    public CommandResult execute(Model model) throws OutOfSessionCommandException {
        requireNonNull(model);

        if (!model.hasOngoingSession()) {
            throw new OutOfSessionCommandException();
        }

        Competition competition = model.getCompetitionOfSession();

        Participation participation = getParticipationOfAthlete(model);
        if (participation == null) {
            return new CommandResult(MESSAGE_PARTICIPATION_NOT_FOUND + athleteName);
        }

        Person athlete = participation.getPerson();

        Predicate<Participation> filterByCompetition = p -> p.getCompetition().isSameElement(competition);
        model.updateFilteredParticipationList(filterByCompetition);
        List<Participation> participationList = model.getFilteredParticipationList();
        List<Participation> pLists = copy(participationList);
        pLists.sort(new OverallScoreComparator());
        int rank = 1;
        int totalScore = 0;
        String[] threeLiftScore = new String[3];
        for (Participation p : pLists) {
            if (p.getPerson().isSameElement(athlete)) {
                totalScore += p.getTotalScore();
                threeLiftScore = p.getThreeLiftScore().split("/");
                break;
            }
            rank += 1;
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, athleteName, competition.getName(), rank, totalScore,
                threeLiftScore[0], threeLiftScore[1], threeLiftScore[2]));
    }

    /**
     * Copies an obs list into a normal java list for sorting purposes
     *
     */
    private List<Participation> copy (List<Participation> participationList) {
        List<Participation> pLists = new ArrayList<>();
        for (Participation p : participationList) {
            pLists.add(p);
        }
        return pLists;

    }

    /**
     * Filters for participation with athleteName and the session Competition
     *
     * @param model {@code Model} which the command should operate on.
     * @return Person object if a Participation contains person with athleteName and competition with competitionName
     */
    private Participation getParticipationOfAthlete(Model model) {
        List<Participation> participationList = model.getFilteredParticipationList();
        Participation participation = null;
        Competition sessionCompetition = model.getCompetitionOfSession();
        for (Participation p : participationList) {
            boolean isSameAthleteName = p.getPerson().getName().equals(this.athleteName);
            boolean isSameCompName = p.getCompetition().equals(sessionCompetition);
            if (isSameAthleteName && isSameCompName) {
                participation = p;
                break;
            }
        }
        return participation;
    }

    /**
     * Compares two participations for sorting
     *
     */
    public static class OverallScoreComparator implements Comparator<Participation> {

        @Override
        public int compare(Participation p1, Participation p2) {
            return p2.getTotalScore() - p1.getTotalScore();

        }
    }

}
