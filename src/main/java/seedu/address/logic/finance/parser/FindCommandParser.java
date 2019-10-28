package seedu.address.logic.finance.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_SORT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TYPE;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.finance.commands.FindCommand;
import seedu.address.logic.finance.parser.exceptions.ParseException;
import seedu.address.model.finance.logentry.LogEntryContainsCategoriesPredicate;
import seedu.address.model.finance.logentry.LogEntryContainsKeywordsPredicate;
import seedu.address.model.finance.logentry.LogEntryMatchLogEntryTypesPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORT, PREFIX_TYPE,
                        PREFIX_KEYWORD, PREFIX_CATEGORY);

        String sortAttr = FindCommand.SORT_DEFAULT_ATTR;
        if (argMultimap.getValue(PREFIX_SORT).isPresent()) {
            sortAttr = ParserUtil.parseSort(argMultimap.getValue(PREFIX_SORT).get());
        }

        String[] logEntryTypesToFind = null;
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            logEntryTypesToFind = argMultimap.getValue(PREFIX_TYPE).get().trim().split("\\s+");
        }

        String[] catsToFind = null;
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            catsToFind = argMultimap.getValue(PREFIX_CATEGORY).get().trim().split("\\s+");
        }

        String[] keywords = null;
        if (argMultimap.getValue(PREFIX_KEYWORD).isPresent()) {
            keywords = argMultimap.getValue(PREFIX_KEYWORD).get().trim().split("\\s+");
        }

        // At least one field present
        boolean isSortAttrSpecified = sortAttr != FindCommand.SORT_DEFAULT_ATTR;
        boolean isLogEntryTypeSpecified = logEntryTypesToFind != null;
        boolean isCatsToFindSpecified = catsToFind != null;
        boolean isKeywordFieldSpecified = keywords != null;
        if (!isSortAttrSpecified && !isLogEntryTypeSpecified
            && !isCatsToFindSpecified && !isKeywordFieldSpecified) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(sortAttr,
                new LogEntryMatchLogEntryTypesPredicate(
                        logEntryTypesToFind == null ? new ArrayList<String>() : Arrays.asList(logEntryTypesToFind)),
                new LogEntryContainsKeywordsPredicate(
                        keywords == null ? new ArrayList<String>() : Arrays.asList(keywords)),
                new LogEntryContainsCategoriesPredicate(
                        catsToFind == null ? new ArrayList<String>() : Arrays.asList(catsToFind)));
    }

}
