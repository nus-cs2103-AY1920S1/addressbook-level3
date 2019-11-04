package seedu.address.logic.commands.findcommand;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.Predicates;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.findcommandparser.FindCommandUtilEnum;
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

    private String nameNorm;
    private String projectNameNorm;
    private String nameExclude;
    private String projectNameExclude;
    private Predicate<Team> findPredicate;

    public FindTeamCommand(
            FindCommandUtilEnum type,
            Optional<String> nameNorm,
            Optional<String> projectNameNorm,
            Optional<String> nameExclude,
            Optional<String> projectNameExclude
    ) {
        List<Predicate<Team>> filteredTeams = new ArrayList<>();
        if (nameNorm.isPresent()) {
            filteredTeams.add(
                    Predicates.getPredicateFindTeamByName(nameNorm.get(), false));
        }

        if (projectNameNorm.isPresent()) {
            filteredTeams.add(
                    Predicates.getPredicateFindTeamByProjectName(projectNameNorm.get(), false));
        }

        if (nameExclude.isPresent()) {
            filteredTeams.add(
                    Predicates.getPredicateFindTeamByName(nameExclude.get(), true));
        }

        if (projectNameExclude.isPresent()) {
            filteredTeams.add(
                    Predicates.getPredicateFindTeamByProjectName(projectNameExclude.get(), true));
        }

        this.findPredicate = type == FindCommandUtilEnum.AND
                ? Predicates.predicateReducer(filteredTeams)
                : Predicates.predicateReducerOr(filteredTeams);

        this.nameNorm = nameNorm.orElse("");
        this.projectNameNorm = projectNameNorm.orElse("");
        this.nameExclude = nameExclude.orElse("");
        this.projectNameExclude = projectNameExclude.orElse("");
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Team> results = model.findTeam(this.findPredicate);
        listResults(results, PrefixType.P);
        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(MESSAGE_SUCCESS, CommandType.T);
    }

    @Override
    public boolean equals(Object otherFindCommand) {
        if (otherFindCommand == this) {
            return true;
        }

        if (!(otherFindCommand instanceof FindTeamCommand)) {
            return false;
        }

        return nameNorm.equals(((FindTeamCommand) otherFindCommand).nameNorm)
                && projectNameNorm.equals(((FindTeamCommand) otherFindCommand).projectNameNorm)
                && nameExclude.equals(((FindTeamCommand) otherFindCommand).nameExclude)
                && projectNameExclude.equals(((FindTeamCommand) otherFindCommand).projectNameExclude);
    }
}
