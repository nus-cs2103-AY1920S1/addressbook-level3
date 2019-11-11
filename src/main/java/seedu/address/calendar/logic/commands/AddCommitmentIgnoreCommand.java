package seedu.address.calendar.logic.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.event.Commitment;
import seedu.address.calendar.model.event.exceptions.DuplicateEventException;
import seedu.address.calendar.logic.parser.Option;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class AddCommitmentIgnoreCommand extends AddCommitmentCommand implements AlternativeCommand {
    private static final boolean IS_BINARY_OPTION = true;

    public AddCommitmentIgnoreCommand(Commitment commitment) {
        super(commitment);
    }

    public CommandResult execute(Calendar calendar, Option option) throws CommandException {
        AlternativeCommandUtil.isValidUserCommand(option, IS_BINARY_OPTION);
        boolean isExecute = option.getBinaryOption();

        if (!isExecute) {
            return new CommandResult(AlternativeCommandUtil.MESSAGE_COMMAND_NOT_EXECUTED);
        }

        return execute(calendar);
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
