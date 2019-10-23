package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;

/**
 * Shows detailed view of the {@link Participant} at specified ID.
 */
public class ViewParticipantCommand extends ViewCommand {

    public static final String COMMAND_WORD = "view participant";
    public static final String MESSAGE_SUCCESS = "Showed specified participant";
    public static final String MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX =
            "The participant index provided is invalid";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " participant"
            + ": shows details of the participant with specified ID. \n"
            + "Format: view participant [participant ID]\n"
            + "For example: " + COMMAND_WORD + " participant P-1";

    // Eventually change to take in Name (or add a new constructor)
    public ViewParticipantCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Participant participantToView;
        try {
            participantToView = model.getParticipant(this.id);
            model.updateHistory(this);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX);
        }
        viewEntity(participantToView);

        return new CommandResult(MESSAGE_SUCCESS, PrefixType.P);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewParticipantCommand // instanceof handles nulls
                && id.equals(((ViewParticipantCommand) other).id));
    }
}
