package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import seedu.address.model.events.Event;

/**
 * Acknowledge a person to the address book.
 */
public class AckAppCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "ackappt";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Ack a appointment to the address book. "
            + "the specified keywords (case-insensitive).\n"
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD + " 001A";

    public static final String MESSAGE_SUCCESS = "this appointmeent has been acked: %1$s";
    public static final String MESSAGE_DUPLICATE_ACKED = "the upcoming appointment has been acked already.";

    private final Event source;
    private final Event dest;


    /**
     * Creates an AckAppCommand to add the specified {@code Person}
     */
    public AckAppCommand(Event source, Event dest) {
        requireNonNull(source);
        requireNonNull(dest);
        this.source = source;
        this.dest = dest;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.deleteEvent(source);

        if (model.hasEvent(dest)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACKED);
        }

        model.addEvent(dest);
        model.displayApprovedAndAckedPatientEvent(dest.getPersonId());
        return new CommandResult(String.format(MESSAGE_SUCCESS, dest));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AckAppCommand // instanceof handles nulls
                && dest.equals(((AckAppCommand) other).dest));
    }

}
