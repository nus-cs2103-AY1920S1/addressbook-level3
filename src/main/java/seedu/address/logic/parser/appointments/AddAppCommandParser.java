//@@author woon17
package seedu.address.logic.parser.appointments;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURSIVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURSIVE_TIMES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.appointments.AddAppCommand;
import seedu.address.logic.commands.appointments.CancelAppCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
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
                        PREFIX_START, PREFIX_END, PREFIX_RECURSIVE, PREFIX_RECURSIVE_TIMES);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_START)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppCommand.MESSAGE_USAGE));
        }

        ReferenceId referenceId = ParserUtil.lookupPatientReferenceId(
                argMultimap.getValue(PREFIX_ID).get(), MESSAGE_REFERENCEID_BELONGS_TO_STAFF);
        if (!model.hasPatient(referenceId)) {
            throw new ParseException(String.format(MESSAGE_INVALID_REFERENCEID, AddAppCommand.MESSAGE_USAGE));
        }

        String startString = argMultimap.getValue(PREFIX_START).get();
        Timing timing;
        if (!arePrefixesPresent(argMultimap, PREFIX_END)) {
            timing = ParserUtil.parseTiming(startString, null);
        } else {
            String endString = argMultimap.getValue(PREFIX_END).get();
            timing = ParserUtil.parseTiming(startString, endString);
        }



        Optional<String> recursiveStringOptional = argMultimap.getValue(PREFIX_RECURSIVE);
        Optional<String> recursiveStringTimesOptional = argMultimap.getValue(PREFIX_RECURSIVE_TIMES);

        if (recursiveStringOptional.isPresent() && recursiveStringTimesOptional.isPresent()) {
            String recursiveString = recursiveStringOptional.get();

            if (!recursiveString.equals("w") && !recursiveString.equals("m") && !recursiveString.equals("y")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddAppCommand.MESSAGE_USAGE));
            }

            Index rescursiveTimes = ParserUtil.parseTimes(recursiveStringTimesOptional.get());
            int times = rescursiveTimes.getZeroBased() + 1;
            Appointment event = new Appointment(referenceId, timing, new Status());
            List<Event> eventList = getRecEvents(event, recursiveString, times);
            return new ReversibleActionPairCommand(new AddAppCommand(eventList),
                    new CancelAppCommand(eventList));
        } else {
            if (!recursiveStringOptional.isPresent() && recursiveStringTimesOptional.isPresent()
                    || recursiveStringOptional.isPresent() && !recursiveStringTimesOptional.isPresent()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddAppCommand.MESSAGE_USAGE));
            }
            Appointment event = new Appointment(referenceId, timing, new Status());
            return new ReversibleActionPairCommand(new AddAppCommand(event),
                    new CancelAppCommand(event));
        }
    }


    private List<Event> getRecEvents(Appointment event, String recursiveString, int times) {
        List<Event> eventList = new ArrayList<>();
        Timing timing = event.getEventTiming();
        Function<Timing, Timing> func = null;

        switch (recursiveString) {
        case "d":
            func = Timing::getOneDayLaterTiming;
            break;
        case "w":
            func = Timing::getOneWeekLaterTiming;
            break;
        case "m":
            func = Timing::getOneMonthLaterTiming;
            break;
        default:
            func = Timing::getOneYearLaterTiming;
            break;
        }

        for (int i = 0; i < times; i++) {
            eventList.add(new Appointment(event.getPersonId(), timing, new Status()));
            timing = func.apply(timing);
        }

        return eventList;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
