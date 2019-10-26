package seedu.address.logic.commands.findcommand;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.Predicates;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Team;

/**
 * Implements the find command for teams.
 */
public class FindTeamCommand extends FindCommand {
    public static final String COMMAND_WORD = "find team";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the team by the name "
            + "given. Parameters: name to search for "
            + "and/or project name to search for"
            + "Example: " + COMMAND_WORD + " n/Team01";
    public static final String MESSAGE_SUCCESS = "Successfully ran the find command.";

    private Predicate<Team> findPredicate;

    public FindTeamCommand(
            Optional<String> name,
            Optional<String> projectName
    ) {
        List<Predicate<Team>> filteredParticipants = new ArrayList<>();
        if (name.isPresent()) {
            filteredParticipants.add(
                    Predicates.getPredicateFindTeamByName(name.get()));
        }

        if (projectName.isPresent()) {
            filteredParticipants.add(
                    Predicates.getPredicateFindTeamByProjectName(projectName.get()));
        }

        this.findPredicate = Predicates.predicateReducer(filteredParticipants);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Team> results = model.findTeam(this.findPredicate);
        listResults(results, PrefixType.P);
        model.updateHistory(this);
        return new CommandResult(MESSAGE_SUCCESS, CommandType.T);
    }
}
