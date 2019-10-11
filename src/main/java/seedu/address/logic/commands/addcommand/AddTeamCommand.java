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
            + "[" + CliSyntax.PREFIX_SUBJECT_NAME + "SUBJECT NAME] "
            + "[" + CliSyntax.PREFIX_SCORE + "SCORE] "
            + CliSyntax.PREFIX_ORGANISATION + "ORGANIZATION\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "John Doe "
            + CliSyntax.PREFIX_PHONE + "98765432 "
            + CliSyntax.PREFIX_EMAIL + "johnd@example.com "
            + CliSyntax.PREFIX_ORGANISATION + "Fill it up for me idk what this is...";

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
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TEAM);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.team.toString()));
    }

}
