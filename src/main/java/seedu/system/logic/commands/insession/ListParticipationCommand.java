package seedu.system.logic.commands.insession;

import static java.util.Objects.requireNonNull;
import static seedu.system.model.Model.PREDICATE_SHOW_ALL_PARTICIPATIONS;

import java.util.List;
import java.util.function.Predicate;

import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.OutOfSessionCommandException;
import seedu.system.model.Model;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Name;

/**
 * Lists all Participations for a specific competition.
 */
public class ListParticipationCommand extends Command {

    public static final String COMMAND_WORD = "listParticipation";
    public static final CommandType COMMAND_TYPE = CommandType.PARTICIPATION;
    public static final String MESSAGE_SUCCESS_FOR_COMPETITION = "Listed participants for ";
    public static final String MESSAGE_SUCCESS_FOR_ALL = "Listed all participants";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " Competition Name";
    public static final String MESSAGE_COMPETITION_NOT_FOUND = "The competition with the given name does not exist : ";

    private final Name competitionName;

    /**
     * Creates a ListParticipationCommand which will list out all Participations
     * participating in the specified competition.
     * @param competitionName name of competition whose participants we want to list out
     */
    public ListParticipationCommand(Name competitionName) {
        requireNonNull(competitionName);
        this.competitionName = competitionName;
    }

    public ListParticipationCommand() {
        competitionName = null;
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

        if (competitionName == null) { // for the command without filtering competitions
            model.updateFilteredParticipationList(PREDICATE_SHOW_ALL_PARTICIPATIONS);
            return new CommandResult(MESSAGE_SUCCESS_FOR_ALL);
        }

        List<Competition> competitionList = model.getFilteredCompetitionList();
        Competition competition = null;
        for (Competition c : competitionList) {
            if (c.getName().equals(competitionName)) {
                competition = c;
                break;
            }
        }

        if (competition == null) {
            return new CommandResult(MESSAGE_COMPETITION_NOT_FOUND + competitionName);
        }

        Competition finalCompetition = competition;
        Predicate<Participation> filterByCompetition = p -> p.getCompetition().isSameElement(finalCompetition);
        model.updateFilteredParticipationList(filterByCompetition);
        return new CommandResult(MESSAGE_SUCCESS_FOR_COMPETITION + competition.toString(), COMMAND_TYPE);
    }
}
