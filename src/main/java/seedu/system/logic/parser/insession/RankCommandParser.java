package seedu.system.logic.parser.insession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.system.logic.commands.insession.RankCommand;
import seedu.system.logic.parser.ArgumentMultimap;
import seedu.system.logic.parser.ArgumentTokenizer;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.Prefix;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.person.Name;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RankCommand.MESSAGE_USAGE));
        }

        Name athleteName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        //MethodOfRanking methodOfRanking =
        // ParserUtil.parseMethodOfRanking(argMultimap.getValue(PREFIX_MethodOfRanking).get());

        return new RankCommand(athleteName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
