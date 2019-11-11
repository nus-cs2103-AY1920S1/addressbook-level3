package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import seedu.tarence.logic.commands.SetSemStartCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetSemStartCommand object
 */
public class SetSemStartCommandParser implements Parser<SetSemStartCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetSemStartCommand
     * and returns an SetSemStartCommand object for execution.
     * @throws ParseException if the user input does not match the expected formats for the module code.
     */
    public SetSemStartCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_START_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_START_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetSemStartCommand.MESSAGE_USAGE));
        }

        argMultimap.getValue(PREFIX_START_DATE).get();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(SetSemStartCommand.DATE_FORMAT);
        Date startDate;
        try {
            startDate = dateFormatter.parse(argMultimap.getValue(PREFIX_START_DATE).get());
        } catch (java.text.ParseException e) {
            throw new ParseException(String.format("Format should be %s", SetSemStartCommand.DATE_FORMAT));
        }

        return new SetSemStartCommand(startDate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

