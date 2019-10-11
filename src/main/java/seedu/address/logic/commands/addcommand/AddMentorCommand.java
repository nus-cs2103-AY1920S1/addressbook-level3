package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.entity.Mentor;

/**
 * Adds a {@link Mentor} to Alfred.
 */
public class AddMentorCommand extends AddCommand {

    public static final String COMMAND_WORD = "addMentor";
    public static final String MESSAGE_SUCCESS = "New mentor added: %s";
    public static final String MESSAGE_DUPLICATE_MENTOR = "This mentor already exists in this Hackathon";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a mentor to Alfred. "
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME "
            + CliSyntax.PREFIX_PHONE + "PHONE "
            + CliSyntax.PREFIX_EMAIL + "EMAIL "
            + CliSyntax.PREFIX_ORGANISATION + "ORGANIZATION\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "John Doe "
            + CliSyntax.PREFIX_PHONE + "98765432 "
            + CliSyntax.PREFIX_EMAIL + "johnd@example.com "
            + CliSyntax.PREFIX_ORGANISATION + "Fill it up for me idk what this is...";

    private Mentor mentor;
    // private Name mentorName;
    // private Name teamName;

    public AddMentorCommand(Mentor mentor) {
        requireNonNull(mentor);
        this.mentor = mentor;
    }

    /*
     * public AddMentorCommand(Name mentorName, Name teamName) {
     *     CollectionUtil.requireAllNonNull(mentorName, teamName);
     *     this.mentorName = mentorName;
     *     this.teamName = teamName;
     * }
     */

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // if (this.teamName != null) {
        //     find mentor (or throw Exception) and retrieve ID
        //     find team (or throw Exception)
        //     add Mentor to team
        //     return CommandResult
        // }

        try {
            model.addMentor(this.mentor);
        } catch (AlfredException e) {
            // Should I return new CommandResult(MESSAGE_DUPLICATE_MENTOR) instead?
            throw new CommandException(MESSAGE_DUPLICATE_MENTOR);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.mentor.toString()));
    }

}
