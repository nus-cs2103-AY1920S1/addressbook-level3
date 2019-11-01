//@@author woon17
package seedu.address.logic.parser.appointments;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.appointments.AckAppCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReferenceId;
import seedu.address.model.events.Appointment;
import seedu.address.model.events.Event;
import seedu.address.model.events.parameters.Status;
import seedu.address.model.events.parameters.Timing;
import seedu.address.model.events.predicates.EventContainsRefIdPredicate;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AckAppCommandParser implements Parser<ReversibleActionPairCommand> {
    public static final String MESSAGE_NOTING_ACK = "there is no appointment under this patient.";
    public static final String MESSAGE_INVALID_REFERENCEID = "the reference id is not belong to any patient";

    private Model model;
    private ObservableList<Event> filterEventList;


    public AckAppCommandParser(Model model) {
        this.model = model;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        if (args.trim().isEmpty()) {
            throw new ParseException(AckAppCommand.MESSAGE_USAGE);
        } else {
            ReferenceId referenceId = ParserUtil.parsePatientReferenceId(argMultimap.getPreamble());

            if (!model.hasPatient(referenceId)) {
                throw new ParseException(MESSAGE_INVALID_REFERENCEID);
            }

            updateToPatientList(referenceId);
            if (filterEventList.size() == 0) {
                throw new ParseException(MESSAGE_NOTING_ACK);
            } else if (filterEventList.get(0).getStatus().isAcked()) {
                throw new ParseException(AckAppCommand.MESSAGE_DUPLICATE_ACKED);
            } else {
                Event unAck = filterEventList.get(0);

                Timing timing = unAck.getEventTiming();
                Status status = new Status(Status.AppointmentStatuses.ACKNOWLEDGED);
                Event toAck = new Appointment(referenceId, timing, status);

                return new ReversibleActionPairCommand(new AckAppCommand(unAck, toAck),
                        new AckAppCommand(toAck, unAck));
            }
        }
    }

    private void updateToPatientList(ReferenceId referenceId) {
        model.updateFilteredAppointmentList(new EventContainsRefIdPredicate(referenceId));
        filterEventList = model.getFilteredAppointmentList();
    }
}
