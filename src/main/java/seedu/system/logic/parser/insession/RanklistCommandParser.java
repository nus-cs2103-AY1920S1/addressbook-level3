package seedu.system.logic.parser.insession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.logic.parser.CliSyntax.PREFIX_RANK_METHOD;

import seedu.system.logic.commands.RankMethod;
import seedu.system.logic.commands.insession.RanklistCommand;
import seedu.system.logic.parser.ArgumentMultimap;
import seedu.system.logic.parser.ArgumentTokenizer;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RanklistCommand object.
 */
public class RanklistCommandParser implements Parser<RanklistCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RanklistCommand
     * and returns an RanklistCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public RanklistCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_RANK_METHOD);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_RANK_METHOD)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RanklistCommand.MESSAGE_USAGE));
        }

        RankMethod rankMethod = ParserUtil.parseRankMethod(argMultimap.getValue(PREFIX_RANK_METHOD).get());

        return new RanklistCommand(rankMethod);
    }
}
