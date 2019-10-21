package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCompCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.competition.Competition;
import seedu.address.model.person.CustomDate;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AddCompCommand object
 */
public class AddCompCommandParser implements Parser<AddCompCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCompCommand
     * and returns an AddCompCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddCompCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_DATE, PREFIX_END_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_START_DATE, PREFIX_END_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCompCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        CustomDate startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());
        CustomDate endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get());

        Competition competition = new Competition(name, startDate, endDate);

        return new AddCompCommand(competition);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
