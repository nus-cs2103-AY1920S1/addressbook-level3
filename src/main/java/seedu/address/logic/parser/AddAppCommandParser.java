package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TIMING;
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

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppCommand;
import seedu.address.logic.commands.CancelAppCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
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
                ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_RECURSIVE, PREFIX_RECURSIVE_TIMES,
                        PREFIX_START, PREFIX_END);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_START, PREFIX_END)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppCommand.MESSAGE_USAGE_RECURSIVELY));
        }

        ReferenceId referenceId = ParserUtil.parsePatientReferenceId(argMultimap.getValue(PREFIX_ID).get());
        if (!model.hasPatient(referenceId)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVAILD_REFERENCE_ID, referenceId.toString()));
        }

        String startString = argMultimap.getValue(PREFIX_START).get();
        String endString = argMultimap.getValue(PREFIX_END).get();

        Timing timing = ParserUtil.parseTiming(startString, endString);

        if (!timing.isValidTimingFromCurrentTime(timing.getStartTime(), timing.getEndTime())) {
            throw new ParseException(String.format(MESSAGE_INVALID_TIMING, AddAppCommand.MESSAGE_USAGE));
        }

        Optional<String> recursiveStringOptional = argMultimap.getValue(PREFIX_RECURSIVE);
        Optional<String> recursiveStringTimesOptional = argMultimap.getValue(PREFIX_RECURSIVE_TIMES);

        if (recursiveStringOptional.isPresent() && recursiveStringTimesOptional.isPresent()) {
            String recursiveString = recursiveStringOptional.get();

            if (!recursiveString.equals("w") && !recursiveString.equals("m") && !recursiveString.equals("y")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddAppCommand.MESSAGE_USAGE_RECURSIVELY));
            }

            Index rescursiveTimes = ParserUtil.parseTimes(recursiveStringTimesOptional.get());
            int times = rescursiveTimes.getZeroBased() + 1;
            Appointment event = new Appointment(referenceId, timing, new Status());
            List<Event> eventList = getRecEvents(event, recursiveString, times);
            return new ReversibleActionPairCommand(new AddAppCommand(eventList),
                    new CancelAppCommand(eventList));
        } else {
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
        case "w":
            func = Timing::getOneWeekLaterTiming;
            break;
        case "m":
            func = Timing::getOneMonthLaterTiming;
            break;
        case "d":
            func = Timing::getOneDayLaterTiming;
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
