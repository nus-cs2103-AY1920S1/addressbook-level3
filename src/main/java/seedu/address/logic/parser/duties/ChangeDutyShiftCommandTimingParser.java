//@@author woon17
package seedu.address.logic.parser.duties;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_STAFFLIST;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.duties.ChangeDutyShiftCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.model.events.parameters.Status;
import seedu.address.model.events.parameters.Timing;


/**
 * Parses input arguments and creates a new ChangeDutyShiftCommand object
 */
public class ChangeDutyShiftCommandTimingParser implements Parser<ReversibleActionPairCommand> {
    private Model model;
    private List<Event> lastShownList;

    public ChangeDutyShiftCommandTimingParser(Model model) {
        this.lastShownList = model.getFilteredDutyShiftList();
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ENTRY, PREFIX_START, PREFIX_END);

        if (!model.isListingAppointmentsOfSingleStaff()) {
            throw new ParseException(String.format(MESSAGE_NOT_STAFFLIST,
                    ChangeDutyShiftCommand.COMMAND_WORD));
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_ENTRY, PREFIX_START, PREFIX_END)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeDutyShiftCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ENTRY).get());
        int idx = index.getZeroBased();

        if (idx >= lastShownList.size()) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        String startString = argMultimap.getValue(PREFIX_START).get();
        Timing timing = ParserUtil.getTiming(argMultimap, startString);
        Event eventToEdit = lastShownList.get(idx);

        Event editedEvent = new Event(eventToEdit.getPersonId(),
                eventToEdit.getPersonName(),
                timing, new Status());

        return new ReversibleActionPairCommand(
                new ChangeDutyShiftCommand(eventToEdit, editedEvent),
                new ChangeDutyShiftCommand(editedEvent, eventToEdit));
    }
}
