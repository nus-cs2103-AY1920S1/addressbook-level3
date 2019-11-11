package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;
import static seedu.system.logic.parser.CliSyntax.PREFIX_COMP;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.InSessionCommandException;
import seedu.system.model.Model;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Name;
import seedu.system.model.session.exceptions.OngoingSessionException;

/**
 * Loads a new session for a specific competition with its participations.
 */
public class StartSessionCommand extends Command {

    public static final String COMMAND_WORD = "startSession";

    public static final CommandType COMMAND_TYPE = CommandType.GENERAL;

    public static final String MESSAGE_SUCCESS = " session has started.\n"
            + "Enter 'next' to get the next lifter in line.\n"
            + "Enter 'rank' or 'ranklist' to get the ranks of the participation/competition.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_COMP + "Competition_Name";

    public static final String MESSAGE_COMPETITION_NOT_FOUND = "The competition with the given name does not exist : ";

    private final Name competitionName;

    public StartSessionCommand(Name competition) {
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
    public CommandResult execute(Model model) throws InSessionCommandException {
        requireNonNull(model);

        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }

        model.updateFilteredCompetitionList(model.PREDICATE_SHOW_ALL_COMPETITIONS);
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
            Predicate<Competition> filterCompetitionsByCompetition = c -> c.isSameElement(finalCompetition);
            model.updateFilteredCompetitionList(filterCompetitionsByCompetition);
            ObservableList<Participation> partForThisComp = model.getFilteredParticipationList();
            model.startSession(finalCompetition, partForThisComp);
        } catch (OngoingSessionException e) {
            return new CommandResult(e.getMessage());
        }
        return new CommandResult(competitionName + MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof StartSessionCommand; // instanceof handles nulls
    }
}
