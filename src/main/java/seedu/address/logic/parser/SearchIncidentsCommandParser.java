package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.stream.Stream;

import seedu.address.logic.commands.SearchIncidentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.incident.*;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchIncidentsCommandParser implements Parser<SearchIncidentsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchIncidentsCommand parse(String args) throws ParseException {
        ArgumentMultimap argDescMap = ArgumentTokenizer.tokenize(args, SEARCH_PREFIX_DESCRIPTION);
        ArgumentMultimap argIdMap = ArgumentTokenizer.tokenize(args, SEARCH_PREFIX_OPERATOR);

        if (arePrefixesPresent(argDescMap, SEARCH_PREFIX_DESCRIPTION)) {
            Description descriptionKeywords = ParserUtil.parseDescription(argDescMap.getValue(SEARCH_PREFIX_DESCRIPTION).get());
            return new SearchIncidentsCommand(new DescriptionKeywordsPredicate(descriptionKeywords));
        } else if (arePrefixesPresent(argIdMap, SEARCH_PREFIX_ID)) {
            IncidentId idKeywords = ParserUtil.parseId(argIdMap.getValue(SEARCH_PREFIX_ID).get());
            return new SearchIncidentsCommand(new IdKeywordsPredicate(idKeywords));
        } else if (arePrefixesPresent(argIdMap, SEARCH_PREFIX_OPERATOR)) {
            Name nameKeywords = ParserUtil.parseName(argIdMap.getValue(SEARCH_PREFIX_OPERATOR).get());
            return new SearchIncidentsCommand(new NameKeywordsPredicate(nameKeywords));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SearchIncidentsCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
