package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SettleAppCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.events.Appointment;
import seedu.address.model.events.Event;
import seedu.address.model.events.Status;


/**
 * Parses input arguments and creates a new SettleAppCommand object
 */
public class SettleAppCommandParser implements Parser<ReversibleActionPairCommand> {


    private Model model;
    private List<Event> lastShownList;

    public SettleAppCommandParser(Model model) {
        this.lastShownList = model.getFilteredEventList();
        this.model = model;
    }
    /**
     * Parses the given {@code String} of arguments in the context of the ReversibleActionPairCommand
     * and returns a ReversibleActionPairCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        if (!model.isMissedList()) {
            throw new ParseException(Messages.MESSAGE_NOT_MISSEDLIST);
        }

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            int idx = index.getZeroBased();
            if (idx >= lastShownList.size()) {
                throw new ParseException(Messages.MESSAGE_INVALID_INDEX);
            }
            Event source = lastShownList.get(idx);
            Event dest = new Appointment(source.getPersonId(), source.getEventTiming(),
                    new Status(Status.AppointmentStatuses.SETTLED));

            return new ReversibleActionPairCommand(new SettleAppCommand(source, dest),
                    new SettleAppCommand(dest, source));

        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleAppCommand.MESSAGE_USAGE), e);
        }
    }
}
