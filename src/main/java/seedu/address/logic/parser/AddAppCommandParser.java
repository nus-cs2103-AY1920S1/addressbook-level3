package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_REFERENCEID;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TIMING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddAppCommand;
import seedu.address.logic.commands.CancelAppCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.events.Appointment;
import seedu.address.model.events.Status;
import seedu.address.model.events.Timing;


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
                ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_START, PREFIX_END);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_START, PREFIX_END)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppCommand.MESSAGE_USAGE));
        }

        ReferenceId referenceId = ParserUtil.parsePatientReferenceId(argMultimap.getValue(PREFIX_ID).get());
        if (!model.hasPerson(referenceId)) {
            throw new ParseException(String.format(MESSAGE_INVALID_REFERENCEID, AddAppCommand.MESSAGE_USAGE));
        }

        String startString = argMultimap.getValue(PREFIX_START).get();
        String endString = argMultimap.getValue(PREFIX_END).get();

        Timing timing = ParserUtil.parseTiming(startString, endString);

        if (!timing.isValidTimingFromCurrentTime(timing.getStartTime(), timing.getEndTime())) {
            throw new ParseException(String.format(MESSAGE_INVALID_TIMING, AddAppCommand.MESSAGE_USAGE));
        }

        Appointment event = new Appointment(referenceId, timing, new Status());
        return new ReversibleActionPairCommand(new AddAppCommand(event),
                new CancelAppCommand(event));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
