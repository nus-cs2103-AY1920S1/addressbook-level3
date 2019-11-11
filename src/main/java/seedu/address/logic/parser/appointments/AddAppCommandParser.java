//@@author woon17
package seedu.address.logic.parser.appointments;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REOCCURRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REOCCURRING_TIMES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.appointments.AddAppCommand;
import seedu.address.logic.commands.appointments.CancelAppCommand;
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

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddAppCommandParser implements Parser<ReversibleActionPairCommand> {
    public static final String MESSAGE_INVALID_REFERENCEID = "the reference id is not belong to any patient";
    public static final String MESSAGE_REFERENCEID_BELONGS_TO_STAFF =
            "Appointments should only be scheduled for patients.";

    private Model model;

    public AddAppCommandParser(Model model) {
        this.model = model;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ID,
                        PREFIX_START, PREFIX_END, PREFIX_REOCCURRING, PREFIX_REOCCURRING_TIMES);

        if (!argMultimap.arePrefixesPresent(PREFIX_ID, PREFIX_START)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppCommand.MESSAGE_USAGE));
        }

        ReferenceId referenceId = ParserUtil.lookupPatientReferenceId(
                argMultimap.getValue(PREFIX_ID).get(), MESSAGE_REFERENCEID_BELONGS_TO_STAFF);
        if (!model.hasPatient(referenceId)) {
            throw new ParseException(String.format(MESSAGE_INVALID_REFERENCEID, AddAppCommand.MESSAGE_USAGE));
        }

        String startString = argMultimap.getValue(PREFIX_START).get();
        Timing timing = ParserUtil.getTiming(argMultimap, startString);

        Optional<String> reoccurringStringOptional = argMultimap.getValue(PREFIX_REOCCURRING);
        Optional<String> reoccurringStringTimesOptional = argMultimap.getValue(PREFIX_REOCCURRING_TIMES);

        if (reoccurringStringOptional.isPresent() && reoccurringStringTimesOptional.isPresent()) {
            String reoccurringString = reoccurringStringOptional.get();

            if (!reoccurringString.equals("w") && !reoccurringString.equals("m") && !reoccurringString.equals("y")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddAppCommand.MESSAGE_USAGE));
            }

            Index reoccurringTimes = ParserUtil.parseTimes(reoccurringStringTimesOptional.get());
            int times = reoccurringTimes.getZeroBased() + 1;
            Appointment event = new Appointment(referenceId,
                    model.resolvePatient(referenceId).getName(), timing, new Status());
            List<Event> eventList = ParserUtil.getRecEvents(event, reoccurringString, times);
            return new ReversibleActionPairCommand(new AddAppCommand(eventList),
                    new CancelAppCommand(eventList));
        } else {
            if (ParserUtil.unmatchedReoccurring(reoccurringStringOptional, reoccurringStringTimesOptional)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddAppCommand.MESSAGE_USAGE));
            }
            Appointment event = new Appointment(referenceId,
                    model.resolvePatient(referenceId).getName(), timing, new Status());
            return new ReversibleActionPairCommand(new AddAppCommand(event),
                    new CancelAppCommand(event));
        }
    }







}
