package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.entity.Team;

/**
 * Adds a {@link Team} to Alfred.
 */
public class AddTeamCommand extends AddCommand {

    public static final String COMMAND_WORD = "addTeam";
    public static final String MESSAGE_SUCCESS = "New team added: %s";
    public static final String MESSAGE_DUPLICATE_TEAM = "This team already exists in this Hackathon";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a team to Alfred. "
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME "
            + CliSyntax.PREFIX_SUBJECT_NAME + "SUBJECT NAME"
            + CliSyntax.PREFIX_PROJECT_NAME + "PROJECT NAME"
            + CliSyntax.PREFIX_PROJECT_TYPE + "PROJECT TYPE"
            + CliSyntax.PREFIX_LOCATION + "LOCATION ROOM NUMBER"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Winning Team "
            + CliSyntax.PREFIX_SUBJECT_NAME + "Social"
            + CliSyntax.PREFIX_PROJECT_NAME + "Winning Project"
            + CliSyntax.PREFIX_PROJECT_TYPE + "AI"
            + CliSyntax.PREFIX_LOCATION + "14";

    private Team team;

    public AddTeamCommand(Team team) {
        requireNonNull(team);
        this.team = team;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.addTeam(this.team);
            model.updateHistory();
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TEAM);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.team.toString()));
    }

}
