package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddAppCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.common.Tag;
import seedu.address.model.events.Appointment;
import seedu.address.model.events.Event;
import seedu.address.model.events.Status;
import seedu.address.model.events.Timing;
import seedu.address.model.person.Person;
import seedu.address.model.person.parameters.Address;
import seedu.address.model.person.parameters.Email;
import seedu.address.model.person.parameters.Name;
import seedu.address.model.person.parameters.Phone;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddAppCommandParser implements Parser<AddAppCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_START, PREFIX_END);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_START, PREFIX_END)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppCommand.MESSAGE_USAGE));
        }

        ReferenceId referenceId = ParserUtil.parsePatientReferenceId(argMultimap.getValue(PREFIX_ID).get());

        String startString = argMultimap.getValue(PREFIX_START).get();
        String endString = argMultimap.getValue(PREFIX_END).get();

        Timing timing = ParserUtil.parseTiming(startString, endString);

        Appointment event = new Appointment(referenceId, timing, new Status());
        return new AddAppCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
