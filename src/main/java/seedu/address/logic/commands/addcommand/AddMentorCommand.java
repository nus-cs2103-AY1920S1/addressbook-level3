package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Mentor;

/**
 * Adds a {@link Mentor} to Alfred.
 */
public class AddMentorCommand extends AddCommand {

    public static final String COMMAND_WORD = "add mentor";
    public static final String MESSAGE_SUCCESS = "New mentor added: %s";
    public static final String MESSAGE_DUPLICATE_MENTOR = "This mentor already exists in this Hackathon";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a mentor to Alfred.\n"
            + "Format: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "NAME "
            + CliSyntax.PREFIX_PHONE + "PHONE "
            + CliSyntax.PREFIX_EMAIL + "EMAIL "
            + CliSyntax.PREFIX_SUBJECT_NAME + "SUBJECT_NAME "
            + CliSyntax.PREFIX_ORGANISATION + "ORGANIZATION\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Bruce Wayne "
            + CliSyntax.PREFIX_PHONE + "+6591239123 "
            + CliSyntax.PREFIX_EMAIL + "customercare@batmail.com "
            + CliSyntax.PREFIX_SUBJECT_NAME + "Social "
            + CliSyntax.PREFIX_ORGANISATION + "Wayne Enterprise, Inc";

    private Mentor mentor;

    public AddMentorCommand(Mentor mentor) {
        requireNonNull(mentor);
        this.mentor = mentor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.addMentor(this.mentor);
            model.resetFilteredLists();
            model.updateHistory(this);
            model.recordCommandExecution(this.getCommandInputString());
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_DUPLICATE_MENTOR);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.mentor.toString()), CommandType.M);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AddMentorCommand)) {
            return false;
        }
        AddMentorCommand addMentorCommand = (AddMentorCommand) other;
        return this.mentor.equals(addMentorCommand.mentor);
    }
}
