package seedu.address.logic.commands.session;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.competition.Competition;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Name;
import seedu.address.model.session.exceptions.OngoingSessionException;

/**
 * Loads a new session for a specific competition with its participants.
 */
public class SessionCommand extends Command {

    public static final String COMMAND_WORD = "session";
    public static final String MESSAGE_SUCCESS = " has started!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " Competition Name";
    public static final String MESSAGE_COMPETITION_NOT_FOUND = "The competition with the given name does not exist : ";

    private final Name competitionName;

    public SessionCommand(Name competition) {
        requireNonNull(competition);
        this.competitionName = competition;
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

        try {
            Competition finalCompetition = competition;
            Predicate<Participation> filterByCompetition = p -> p.getCompetition().isSameElement(finalCompetition);
            model.updateFilteredParticipationList(filterByCompetition);
            ObservableList<Participation> partForThisComp = model.getFilteredParticipationList();
            model.startSession(partForThisComp);
        } catch (OngoingSessionException e) {
            return new CommandResult(e.getMessage());
        }
        return new CommandResult(competitionName + MESSAGE_SUCCESS);
    }
}
