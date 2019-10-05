package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Participant;

public class AddParticipantCommand extends AddCommand {

     /* Possible Fields */
     private static final String MESSAGE_SUCCESS = "New participant added: %s";
     private static final String MESSAGE_DUPLICATE_PARTICIPANT = "This participant already exists in this Hackathon";
     public static final String COMMAND_WORD = "addParticipant";

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
        } catch (Exception e) {
            // Should Model be checking if there are duplicate persons?
            throw new CommandException(MESSAGE_DUPLICATE_PARTICIPANT);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.participant.toString()));
    }

}
