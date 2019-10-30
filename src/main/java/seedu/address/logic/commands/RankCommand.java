package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.competition.Competition;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Lists the position of an athlete for a specific competition.
 */
public class RankCommand extends Command {
    public static final String COMMAND_WORD = "rank";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": lists an athlete's position for a competition. "
            + "Parameters: "
            + PREFIX_NAME + "ATHLETE NAME "
            + PREFIX_COMP + "COMPETITION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John "
            + PREFIX_COMP + "IWF 2009 ";
    public static final String MESSAGE_COMPETITION_NOT_FOUND = "The competition with the given name does not exist : ";
    public static final String MESSAGE_ATHLETE_NOT_FOUND = "The athlete with the given name does not exist for "
            + "competition: ";
    public static final String MESSAGE_SUCCESS = " Athlete: %1$s Competition: %2$s \n Rank: %3$d \n Total Score: %4$d "
            + "\n Max Bench Press: %5$s \n Max Squat: %6$s \n Max Deadlift: %7$s ";

    private final Name athleteName;
    private final Name competitionName;

    /**
     * Creates a RankCommand which lists an athlete's position for a competition
     * @param competitionName name of competition
     * @param athleteName name of athlete
     */
    public RankCommand(Name athleteName, Name competitionName) {
        requireNonNull(athleteName);
        requireNonNull(competitionName);
        this.athleteName = athleteName;
        this.competitionName = competitionName;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Competition competition = getSpecificComp(model);
        if (competition == null) {
            return new CommandResult(MESSAGE_COMPETITION_NOT_FOUND + competitionName);
        }

        Person athlete = getSpecificAthlete(model);
        if (athlete == null) {
            return new CommandResult(MESSAGE_ATHLETE_NOT_FOUND + competitionName);
        }
        Predicate<Participation> filterByCompetition = p -> p.getCompetition().isSameElement(competition);
        model.updateFilteredParticipationList(filterByCompetition);
        List<Participation> participationList = model.getFilteredParticipationList();
        List<Participation> pLists = copy(participationList);
        pLists.sort(new ParticipationComparator());
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

        return new CommandResult(String.format(MESSAGE_SUCCESS, athleteName, competitionName, rank, totalScore,
                threeLiftScore[1], threeLiftScore[0], threeLiftScore[2]));
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
     * Filters for competition with competitionName
     *
     * @param model {@code Model} which the command should operate on.
     * @return Competition object if competition with competitionName exists, else null is returned.
     */
    private Competition getSpecificComp(Model model) {
        List<Competition> competitionList = model.getFilteredCompetitionList();
        Competition competition = null;
        for (Competition c : competitionList) {
            if (c.getName().equals(this.competitionName)) {
                competition = c;
                break;
            }
        }
        return competition;

    }

    /**
     * Filters for athlete with athleteName
     *
     * @param model {@code Model} which the command should operate on.
     * @return Person object if a Participation contains person with athleteName and competition with competitionName
     */
    private Person getSpecificAthlete(Model model) {
        List<Participation> participationList = model.getFilteredParticipationList();
        Person athlete = null;
        for (Participation p : participationList) {
            boolean isSameAthleteName = p.getPerson().getName().equals(this.athleteName);
            boolean isSameCompName = p.getCompetition().getName().equals(this.competitionName);
            if (isSameAthleteName && isSameCompName) {
                athlete = p.getPerson();
                break;
            }
        }
        return athlete;
    }

    /**
     * Compares two participations for sorting
     *
     */
    public static class ParticipationComparator implements Comparator<Participation> {

        @Override
        public int compare(Participation p1, Participation p2) {
            return p2.getTotalScore() - p1.getTotalScore();

        }
    }

}
