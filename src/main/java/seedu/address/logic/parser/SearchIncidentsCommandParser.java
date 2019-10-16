package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import java.util.stream.Stream;

import seedu.address.logic.commands.SearchIncidentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.DescriptionKeywordsPredicate;
import seedu.address.model.incident.IdKeywordsPredicate;
import seedu.address.model.incident.IncidentId;

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
//        String trimmedArgs = args.trim();
//        if (trimmedArgs.isEmpty()) {
//            throw new ParseException(
//                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchIncidentsCommand.MESSAGE_USAGE));
//        }
//
//        String[] nameKeywords = trimmedArgs.split("\\s+");
//
//        return new SearchIncidentsCommand(new IncidentContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        ArgumentMultimap argDescMap = ArgumentTokenizer.tokenize(args, PREFIX_DESC);
        ArgumentMultimap argIdMap = ArgumentTokenizer.tokenize(args, PREFIX_ID);

        if (arePrefixesPresent(argDescMap, PREFIX_DESC)) {
            Description descriptionKeywords = ParserUtil.parseDescription(argDescMap.getValue(PREFIX_DESC).get());
            return new SearchIncidentsCommand(new DescriptionKeywordsPredicate(descriptionKeywords));
        } else if (arePrefixesPresent(argIdMap, PREFIX_ID)) {
            IncidentId idKeywords = ParserUtil.parseId(argIdMap.getValue(PREFIX_ID).get());
            return new SearchIncidentsCommand(new IdKeywordsPredicate(idKeywords));
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
