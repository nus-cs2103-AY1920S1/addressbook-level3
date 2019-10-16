package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_REFERENCEID;


import javafx.collections.ObservableList;
import seedu.address.logic.commands.AckAppCommand;
import seedu.address.logic.commands.AddAppCommand;
import seedu.address.logic.commands.CancelAppCommand;
import seedu.address.logic.commands.UnAckApptCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.events.Appointment;
import seedu.address.model.events.ContainsKeywordsPredicate;
import seedu.address.model.events.Event;
import seedu.address.model.events.Status;
import seedu.address.model.events.Timing;

import java.util.Arrays;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AckAppCommandParser implements Parser<ReversibleActionPairCommand> {
    private Model model;
    private ObservableList<Event> filterEventList;
    public static final String MESSAGE_NOTING_ACK = "there is no appointment under this patient.";


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

            if (!model.hasPerson(referenceId)) {
                throw new ParseException(MESSAGE_INVALID_REFERENCEID);
//                throw new ParseException("something is wrong here");
            }

            updateToPatientList(referenceId);
            if (filterEventList.size() == 0) {
                throw new ParseException(MESSAGE_NOTING_ACK);
            } else if (filterEventList.get(0).getStatus().isAcked()) {
                throw new ParseException(AckAppCommand.MESSAGE_DUPLICATE_ACKED);
            }else{
                Event unAck = filterEventList.get(0);
//                model.deleteEvent(unAck);

                Timing timing = unAck.getEventTiming();
                Status status = new Status(Status.AppointmentStatuses.ACKNOWLEDGED);
                Event toAck = new Appointment(referenceId, timing, status);

                return new ReversibleActionPairCommand(new AckAppCommand(unAck, toAck),
                        new AckAppCommand(toAck, unAck));
            }
        }
    }

    private void updateToPatientList(ReferenceId referenceId) {
        String[] nameKeywords = referenceId.toString().split("\\s+");
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        model.updateFilteredEventList(predicate);
        filterEventList = model.getFilteredEventList();
    }
}
