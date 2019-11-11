//@@author woon17
package seedu.address.logic.parser.appointments;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_PATIENTLIST;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.appointments.EditAppCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.events.Appointment;
import seedu.address.model.events.Event;
import seedu.address.model.events.parameters.Status;
import seedu.address.model.events.parameters.Timing;


/**
 * Parses input arguments and creates a new ChangeAppCommand object
 */
public class EditAppCommandParser implements Parser<ReversibleActionPairCommand> {
    private Model model;
    private List<Event> lastShownList;

    public EditAppCommandParser(Model model) {
        this.lastShownList = model.getFilteredAppointmentList();
        this.model = model;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditAppCommand
     * and returns a ReversibleActionPairCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ENTRY, PREFIX_START, PREFIX_END);

        if (!model.isListingAppointmentsOfSinglePatient()) {
            throw new ParseException(MESSAGE_NOT_PATIENTLIST);
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_ENTRY, PREFIX_START) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAppCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ENTRY).get());
        int idx = index.getZeroBased();

        if (idx >= lastShownList.size()) {
            throw new ParseException(Messages.MESSAGE_INVALID_INDEX);
        }

        String startString = argMultimap.getValue(PREFIX_START).get();
        Timing timing = ParserUtil.getTiming(argMultimap, startString);
        Event eventToEdit = lastShownList.get(idx);
        Event editedEvent = new Appointment(eventToEdit.getPersonId(),
                eventToEdit.getPersonName(), timing, new Status());

        return new ReversibleActionPairCommand(new EditAppCommand(eventToEdit, editedEvent),
                new EditAppCommand(editedEvent, eventToEdit));

    }
}
