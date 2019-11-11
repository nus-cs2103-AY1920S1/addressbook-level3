//@@author woon17
package seedu.address.logic.parser.duties;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REOCCURRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REOCCURRING_TIMES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.duties.AddDutyShiftCommand;
import seedu.address.logic.commands.duties.CancelDutyShiftCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
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
                        PREFIX_START, PREFIX_END, PREFIX_REOCCURRING, PREFIX_REOCCURRING_TIMES);

        if (!argMultimap.arePrefixesPresent(PREFIX_ID, PREFIX_START, PREFIX_END)) {
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
        Timing timing = ParserUtil.getTiming(argMultimap, startString);

        Optional<String> reoccurringStringOptional = argMultimap.getValue(PREFIX_REOCCURRING);
        Optional<String> reoccurringStringTimesOptional = argMultimap.getValue(PREFIX_REOCCURRING_TIMES);

        if (reoccurringStringOptional.isPresent() && reoccurringStringTimesOptional.isPresent()) {
            return getCommandWithReoccurring(referenceId, timing, reoccurringStringOptional,
                    reoccurringStringTimesOptional);
        } else {
            return getCommand(referenceId, timing, reoccurringStringOptional, reoccurringStringTimesOptional);
        }
    }

    /**
     * get the reversibleActionPairCommand for on duty shift.
     *
     * @param referenceId                    ReferenceId of the doctor
     * @param timing                         Timing of the duty
     * @param reoccurringStringOptional      Reoccurring intervals
     * @param reoccurringStringTimesOptional Reoccurring times
     * @return reversibleActionPairCommand for add one duty shift
     * @throws ParseException if the user input does not conform the expected format
     */
    private ReversibleActionPairCommand getCommand(
            ReferenceId referenceId, Timing timing,
            Optional<String> reoccurringStringOptional,
            Optional<String> reoccurringStringTimesOptional) throws ParseException {

        if (ParserUtil.unmatchedReoccurring(reoccurringStringOptional, reoccurringStringTimesOptional)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddDutyShiftCommand.MESSAGE_USAGE));
        }

        Event event = new Event(referenceId, model.resolveStaff(referenceId).getName(), timing, new Status());
        return new ReversibleActionPairCommand(
                new AddDutyShiftCommand(event),
                new CancelDutyShiftCommand(event));
    }

    /**
     * get the reversibleActionPairCommand for reoccurring duty shifts.
     *
     * @param referenceId                    ReferenceId of the doctor
     * @param timing                         Timing of the duty
     * @param reoccurringStringOptional      Reoccurring intervals
     * @param reoccurringStringTimesOptional Reoccurring times
     * @return reversibleActionPairCommand for add reoccurring duty shifts
     * @throws ParseException if the user input does not conform the expected format
     */
    private ReversibleActionPairCommand getCommandWithReoccurring(
            ReferenceId referenceId, Timing timing,
            Optional<String> reoccurringStringOptional,
            Optional<String> reoccurringStringTimesOptional) throws ParseException {

        String reoccurring = reoccurringStringOptional.get();

        if (!reoccurring.equals("w") && !reoccurring.equals("m") && !reoccurring.equals("y")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddDutyShiftCommand.MESSAGE_USAGE));
        }

        Index reoccurringTimes = ParserUtil.parseTimes(reoccurringStringTimesOptional.get());
        int times = reoccurringTimes.getZeroBased() + 1;

        Event event = new Event(referenceId, model.resolveStaff(referenceId).getName(), timing, new Status());
        List<Event> eventList = ParserUtil.getRecEvents(event, reoccurring, times);
        return new ReversibleActionPairCommand(new AddDutyShiftCommand(eventList),
                new CancelDutyShiftCommand(eventList));
    }
}
