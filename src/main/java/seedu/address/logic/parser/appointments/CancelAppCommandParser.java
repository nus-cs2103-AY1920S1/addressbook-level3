//@@author woon17
package seedu.address.logic.parser.appointments;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_PATIENTLIST;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.appointments.AddAppCommand;
import seedu.address.logic.commands.appointments.CancelAppCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;


/**
 * Parses input arguments and creates a new CancelAppCommand object
 */
public class CancelAppCommandParser implements Parser<ReversibleActionPairCommand> {

    private List<Event> lastShownList;
    private Model model;

    public CancelAppCommandParser(Model model) {
        this.model = model;
        this.lastShownList = model.getFilteredAppointmentList();
    }

    /**
     * Parses the given {@code String} of arguments in the context of the CancelApptCommand
     * and returns an CancelApptCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        Index index;

        if (!model.isListingAppointmentsOfSinglePatient()) {
            throw new ParseException(MESSAGE_NOT_PATIENTLIST);
        }

        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelAppCommand.MESSAGE_USAGE), e);
        }
        Event eventToDelete = ParserUtil.getEntryFromList(lastShownList, index);
        return new ReversibleActionPairCommand(new CancelAppCommand(eventToDelete),
                new AddAppCommand(eventToDelete));
    }
}
