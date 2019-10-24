package seedu.address.calendar.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.event.Commitment;
import seedu.address.calendar.parser.CliSyntax;
import seedu.address.logic.commands.CommandResult;

public class AddCommitmentCommand extends Command {
    public static final String COMMAND_WORD = "addCommitment";
    public static final String MESSAGE_ADD_SUCCESS = "Added: %s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a commitment to the specified date"
            + "Parameters: "
            + CliSyntax.PREFIX_DAY + " DAY "
            + "[" + CliSyntax.PREFIX_MONTH + " MONTH] "
            + "[" + CliSyntax.PREFIX_YEAR + "YEAR] "
            + CliSyntax.PREFIX_NAME + " NAME "
            + "[" + CliSyntax.PREFIX_INFO + " INFO]" + "\n"
            + "Example: " + COMMAND_WORD + " " + CliSyntax.PREFIX_DAY + " 29 "
            + CliSyntax.PREFIX_MONTH + " Nov " + CliSyntax.PREFIX_NAME + " CS2103 exam";

    private Commitment commitment;

    public AddCommitmentCommand(Commitment commitment) {
        this.commitment = commitment;
    }

    public CommandResult execute(Calendar calendar) {
        calendar.addEvent(commitment);
        String formattedFeedback = String.format(MESSAGE_ADD_SUCCESS, commitment.toString());
        return new CommandResult(formattedFeedback);
    }
}
