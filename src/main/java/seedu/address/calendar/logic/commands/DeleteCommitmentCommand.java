package seedu.address.calendar.logic.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.event.Commitment;
import seedu.address.calendar.logic.parser.CliSyntax;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import java.util.NoSuchElementException;

public class DeleteCommitmentCommand extends DeleteCommand {
    public static final String COMMAND_WORD = "commitment";
    public static final String MESSAGE_USAGE = DeleteCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the specified commitment that happens on the specified date(s)"
            + CliSyntax.PREFIX_START_DAY + "START DAY "
            + "[" + CliSyntax.PREFIX_START_MONTH + "START MONTH] "
            + "[" + CliSyntax.PREFIX_START_YEAR + "START YEAR] "
            + "[" + CliSyntax.PREFIX_END_DAY + "END DAY] "
            + "[" + CliSyntax.PREFIX_END_MONTH + "END MONTH] "
            + "[" + CliSyntax.PREFIX_END_YEAR + "END YEAR] "
            + CliSyntax.PREFIX_NAME + "NAME " + "\n"
            + "Example: " + DeleteCommand.COMMAND_WORD + " " + COMMAND_WORD + " " + CliSyntax.PREFIX_START_DAY + "29 "
            + CliSyntax.PREFIX_START_MONTH + "Nov " + CliSyntax.PREFIX_NAME + "CS2103 exam";

    private Commitment commitment;

    public DeleteCommitmentCommand(Commitment commitment) {
        this.commitment = commitment;
    }

    @Override
    public CommandResult execute(Calendar calendar) throws CommandException, NoSuchElementException {
        calendar.deleteEvent(commitment);
        String formattedFeedback = String.format(MESSAGE_DELETE_SUCCESS, commitment.toString());
        return new CommandResult(formattedFeedback);
    }
}
