package seedu.address.calendar.logic.commands;

import seedu.address.calendar.logic.parser.CliSyntax;
import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.event.Commitment;
import seedu.address.calendar.model.event.exceptions.ClashException;
import seedu.address.calendar.model.event.exceptions.DuplicateEventException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Adds a commitment to the calendar.
 */
public class AddCommitmentCommand extends AddCommand {
    public static final String COMMAND_WORD = "commitment";
    public static final String MESSAGE_USAGE = AddCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a commitment to the specified date(s)"
            + CliSyntax.PREFIX_START_DAY + "START DAY "
            + "[" + CliSyntax.PREFIX_START_MONTH + "START MONTH] "
            + "[" + CliSyntax.PREFIX_START_YEAR + "START YEAR] "
            + "[" + CliSyntax.PREFIX_END_DAY + "END DAY] "
            + "[" + CliSyntax.PREFIX_END_MONTH + "END MONTH] "
            + "[" + CliSyntax.PREFIX_END_YEAR + "END YEAR] "
            + CliSyntax.PREFIX_NAME + "NAME\n"
            + "Example: " + AddCommand.COMMAND_WORD + " " + COMMAND_WORD + " " + CliSyntax.PREFIX_START_DAY + "29 "
            + CliSyntax.PREFIX_START_MONTH + "Nov " + CliSyntax.PREFIX_NAME + "CS2103 exam";

    protected Commitment commitment;

    /**
     * Creates an add commitment command.
     *
     * @param commitment The commitment to be added.
     */
    public AddCommitmentCommand(Commitment commitment) {
        this.commitment = commitment;
    }

    /**
     * Adds the commitment to the calendar.
     *
     * @param calendar The calendar to which the commitment should be added
     * @return The result of executing this command
     * @throws CommandException if the command is invalid
     * @throws ClashException if adding the event may result in potential schedule conflicts
     */
    public CommandResult execute(Calendar calendar) throws CommandException, ClashException {
        try {
            calendar.addEvent(commitment);
        } catch (DuplicateEventException e) {
            throw new CommandException(e.getMessage());
        } catch (ClashException e) {
            throw e;
        }

        String formattedFeedback = String.format(MESSAGE_ADD_SUCCESS, commitment.toString());
        return new CommandResult(formattedFeedback);
    }
}
