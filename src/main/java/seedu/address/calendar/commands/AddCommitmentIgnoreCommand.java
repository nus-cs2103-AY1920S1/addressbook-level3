package seedu.address.calendar.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.event.Commitment;
import seedu.address.calendar.model.event.exceptions.DuplicateEventException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class AddCommitmentIgnoreCommand extends AddCommitmentCommand implements AlternativeCommand {
    public AddCommitmentIgnoreCommand(Commitment commitment) {
        super(commitment);
    }

    @Override
    public CommandResult execute(Calendar calendar) throws CommandException {
        try {
            calendar.addIgnoreClash(commitment);
        } catch (DuplicateEventException e) {
            throw new CommandException(e.getMessage());
        }

        String formattedFeedback = String.format(MESSAGE_ADD_SUCCESS, commitment.toString());
        return new CommandResult(formattedFeedback);
    }
}
