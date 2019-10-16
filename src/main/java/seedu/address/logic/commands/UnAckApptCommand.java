package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;

import static java.util.Objects.requireNonNull;

public class UnAckApptCommand extends ReversibleCommand {
    public static final String MESSAGE_UNACKED_SUCCESS = "the lastest appointment is unAcked: %1$s";

    private final Event unAck;

    public UnAckApptCommand(Event unAck) {
        requireNonNull(unAck);
        this.unAck = unAck;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasExactEvent(unAck)) {
            throw new CommandException(String.format(Messages.MESSAGE_EVENT_NOT_FOUND, unAck));
        }

        model.addEvent(unAck);
        model.displayApprovedAndAckedPatientEvent(unAck.getPersonId());
        return new CommandResult(String.format(MESSAGE_UNACKED_SUCCESS, unAck));
    }
}
