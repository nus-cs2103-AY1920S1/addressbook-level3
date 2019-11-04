//@@author woon17
package seedu.address.logic.parser.duties;

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
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.duties.AddDutyShiftCommand;
import seedu.address.logic.commands.duties.CancelDutyShiftCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReferenceId;
import seedu.address.model.events.Event;
import seedu.address.model.events.parameters.Status;
import seedu.address.model.events.parameters.Timing;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddDutyShiftCommandParser implements Parser<ReversibleActionPairCommand> {
    public static final String MESSAGE_INVALID_REFERENCEID = "the reference id is not belong to any doctor";
    public static final String MESSAGE_REFERENCEID_BELONGS_TO_PATIENT =
            "Patients cannot be scheduled for duty shifts.";

    private Model model;

    public AddDutyShiftCommandParser(Model model) {
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

        if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_START, PREFIX_END)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddDutyShiftCommand.MESSAGE_USAGE));
        }

        ReferenceId referenceId = ParserUtil.lookupStaffReferenceId(
                argMultimap.getValue(PREFIX_ID).get(),
                MESSAGE_REFERENCEID_BELONGS_TO_PATIENT);
        if (!model.hasStaff(referenceId)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_REFERENCEID, AddDutyShiftCommand.MESSAGE_USAGE));
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
                        AddDutyShiftCommand.MESSAGE_USAGE));
            }

            Index recursiveTimes = ParserUtil.parseTimes(recursiveStringTimesOptional.get());
            int times = recursiveTimes.getZeroBased() + 1;
            Event event = new Event(referenceId, timing, new Status());
            List<Event> eventList = getRecEvents(event, recursiveString, times);
            return new ReversibleActionPairCommand(new AddDutyShiftCommand(eventList),
                    new CancelDutyShiftCommand(eventList));
        } else {
            if (!recursiveStringOptional.isPresent() && recursiveStringTimesOptional.isPresent()
                    || recursiveStringOptional.isPresent() && !recursiveStringTimesOptional.isPresent()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddDutyShiftCommand.MESSAGE_USAGE));
            }
            Event event = new Event(referenceId, timing, new Status());
            return new ReversibleActionPairCommand(
                    new AddDutyShiftCommand(event),
                    new CancelDutyShiftCommand(event));
        }
    }

    private List<Event> getRecEvents(Event event, String recursiveString, int times) {
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
            eventList.add(new Event(event.getPersonId(), timing, new Status()));
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
