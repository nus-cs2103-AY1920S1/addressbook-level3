package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALLER_NUMBER;

import java.util.stream.Stream;

import seedu.address.logic.commands.FillCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Incident;

/**
 * Parses input arguments and creates a new FillCommand object
 */
public class FillCommandParser implements Parser<FillCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FillCommand
     * and returns a FillCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FillCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CALLER_NUMBER);

        if (!arePrefixesPresent(argMultimap, PREFIX_CALLER_NUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FillCommand.MESSAGE_USAGE));
        }

        CallerNumber callerNumber = ParserUtil.parseCallerNumber(argMultimap.getValue(PREFIX_CALLER_NUMBER).get());

        // create new incident
        Incident incident = new Incident(callerNumber);

        return new FillCommand(incident);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
