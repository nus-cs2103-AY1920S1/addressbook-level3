package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Participant;

/**
 * Adds a {@link Participant} to Alfred.
 */
public class AddParticipantCommand extends AddCommand {
    public static final String COMMAND_WORD = "add participant";
    public static final String MESSAGE_SUCCESS = "New participant added: %s";
    public static final String MESSAGE_DUPLICATE_PARTICIPANT = "This participant already exists in this Hackathon";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a participant to Alfred.\n"
            + "Format: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "NAME "
            + CliSyntax.PREFIX_PHONE + "PHONE "
            + CliSyntax.PREFIX_EMAIL + "EMAIL\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Arthur Fleck "
            + CliSyntax.PREFIX_EMAIL + "arthurs@joking.com "
            + CliSyntax.PREFIX_PHONE + "+6591239123";

    private Participant participant;

    public AddParticipantCommand(Participant participant) {
        requireNonNull(participant);
        this.participant = participant;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.addParticipant(this.participant);
            model.resetFilteredLists();
            model.updateHistory(this);
            model.recordCommandExecution(this.getCommandInputString());
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PARTICIPANT);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.participant.toString()), CommandType.P);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddParticipantCommand // instanceof handles nulls
                && participant.equals(((AddParticipantCommand) other).participant));
    }
}
