package seedu.system.logic.commands.insession;

import static java.util.Objects.requireNonNull;
import static seedu.system.logic.parser.CliSyntax.PREFIX_RANK_METHOD;
import static seedu.system.model.Model.PREDICATE_SHOW_ALL_PARTICIPATIONS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.RankMethod;
import seedu.system.logic.commands.comparators.ParticipationRankMethodComparator;
import seedu.system.logic.commands.exceptions.OutOfSessionCommandException;
import seedu.system.model.Model;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Person;

/**
 * Lists the athletes in order by some stipulated ranking method for a specific competition.
 */
public class RanklistCommand extends Command {

    public static final String COMMAND_WORD = "ranklist";

    public static final CommandType COMMAND_TYPE = CommandType.PARTICIPATION;

    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": ranks all athletes in a competition by the chosen ranking method.\n"
        + "Parameters: " + PREFIX_RANK_METHOD + "RANKING METHOD \n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_RANK_METHOD + " overall \n";

    public static final String MESSAGE_RANK_METHOD_NOT_FOUND =
        "The ranking method with the given name does not exist : ";

    public static final String MESSAGE_SUCCESS = "Ranking of athletes by %1$s score in competition %2$s:";

    private final RankMethod rankMethod;

    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     */
    public RanklistCommand(RankMethod rankMethod) {
        requireNonNull(rankMethod);
        this.rankMethod = rankMethod;
    }

    @Override
    public CommandResult execute(Model model) throws OutOfSessionCommandException {
        requireNonNull(model);

        if (!model.hasOngoingSession()) {
            throw new OutOfSessionCommandException();
        }

        Competition onGoingCompetition = model.getOngoingCompetition();

        model.updateFilteredParticipationList(PREDICATE_SHOW_ALL_PARTICIPATIONS);

        Predicate<Participation> filterByCompetition = p -> p.getCompetition().equals(onGoingCompetition);
        model.updateFilteredParticipationList(filterByCompetition);

        ArrayList<Participation> participationList = new ArrayList<>();
        for (Participation participation : model.getFilteredParticipationList()) {
            participationList.add(participation);
        }
        Comparator<Participation> comparator = new ParticipationRankMethodComparator(rankMethod);
        participationList.sort(comparator);

        String message = MESSAGE_SUCCESS + "\n";
        for (int i = 0; i < participationList.size(); i++) {
            Participation participation = participationList.get(i);
            int score = participation.getScore(rankMethod);
            Person personParticipating = participation.getPerson();
            message += (i + 1) + ". " + personParticipating.getName() + " (Score: " + score + ")\n";
        }
        return new CommandResult(String.format(message, rankMethod, onGoingCompetition), COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RanklistCommand // instanceof handles nulls
            && rankMethod.equals(((RanklistCommand) other).rankMethod));
    }
}
