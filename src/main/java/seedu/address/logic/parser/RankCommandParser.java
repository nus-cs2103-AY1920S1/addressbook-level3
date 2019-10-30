package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.RankCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new RankCommand object
 */
public class RankCommandParser implements Parser<RankCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RankCommand
     * and returns an RankCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public RankCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COMP);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_COMP)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RankCommand.MESSAGE_USAGE));
        }

        Name athleteName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Name competitionName = ParserUtil.parseName(argMultimap.getValue(PREFIX_COMP).get());

        return new RankCommand(athleteName, competitionName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
