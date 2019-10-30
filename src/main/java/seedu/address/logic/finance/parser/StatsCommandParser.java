package seedu.address.logic.finance.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_BETWEEN;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_GROUP_BY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_SUMMARISE;

import java.util.Date;
import java.util.stream.Stream;

import seedu.address.logic.finance.commands.StatsCommand;
import seedu.address.logic.finance.parser.exceptions.ParseException;
import seedu.address.model.finance.attributes.GroupByAttr;

/**
 * Parses input arguments and creates a new StatsCommand object
 */
public class StatsCommandParser implements Parser<StatsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SpendCommand
     * and returns an SpendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_GROUP_BY, PREFIX_SUMMARISE, PREFIX_BETWEEN);

        // If compulsory fields are empty
        if (!arePrefixesPresent(argMultimap,
                PREFIX_GROUP_BY, PREFIX_SUMMARISE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        }

        assert argMultimap.getValue(PREFIX_GROUP_BY).isPresent();
        assert argMultimap.getValue(PREFIX_SUMMARISE).isPresent();

        GroupByAttr groupByAttr = ParserUtil.parseGroupByAttr(argMultimap.getValue(PREFIX_GROUP_BY).get());
        String summariseAttr = ParserUtil.parseSummariseAttr(argMultimap.getValue(PREFIX_SUMMARISE).get());
        Date[] betweenDates = null;
        if (argMultimap.getValue(PREFIX_BETWEEN).isPresent()) {
            betweenDates = ParserUtil.parseBetweenDates(
                    argMultimap.getValue(PREFIX_BETWEEN).get().trim().split("\\s+"));
        }

        return new StatsCommand(groupByAttr, summariseAttr, betweenDates);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
