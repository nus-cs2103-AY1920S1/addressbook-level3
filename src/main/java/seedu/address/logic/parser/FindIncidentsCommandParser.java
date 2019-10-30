package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_OPERATOR;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_SELF;

import java.util.stream.Stream;

import seedu.address.logic.commands.FindIncidentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.DescriptionKeywordsPredicate;
import seedu.address.model.incident.IdKeywordsPredicate;
import seedu.address.model.incident.IncidentId;
import seedu.address.model.incident.NameKeywordsPredicate;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class FindIncidentsCommandParser implements Parser<FindIncidentsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonsCommand
     * and returns a FindPersonsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindIncidentsCommand parse(String args) throws ParseException {
        ArgumentMultimap argDescMap = ArgumentTokenizer.tokenize(args, SEARCH_PREFIX_DESCRIPTION);
        ArgumentMultimap argIdMap = ArgumentTokenizer.tokenize(args, SEARCH_PREFIX_ID);
        ArgumentMultimap argOpMap = ArgumentTokenizer.tokenize(args, SEARCH_PREFIX_OPERATOR);
        ArgumentMultimap argSelfMap = ArgumentTokenizer.tokenize(args, SEARCH_PREFIX_SELF);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, SEARCH_PREFIX_DESCRIPTION, SEARCH_PREFIX_ID, SEARCH_PREFIX_OPERATOR);

        if ((arePrefixesPresent(argMultimap, SEARCH_PREFIX_OPERATOR))
                && (arePrefixesPresent(argMultimap, SEARCH_PREFIX_DESCRIPTION))) {
            Name nameKeywords = ParserUtil.parseName(argMultimap.getValue(SEARCH_PREFIX_OPERATOR).get());
            Description descriptionKeywords = ParserUtil.parseDescription(argMultimap.getValue(SEARCH_PREFIX_DESCRIPTION).get());
            return new FindIncidentsCommand(new NameKeywordsPredicate(nameKeywords),
                    new DescriptionKeywordsPredicate(descriptionKeywords));
        } else if (arePrefixesPresent(argDescMap, SEARCH_PREFIX_DESCRIPTION)) {
            Description descriptionKeywords = ParserUtil.parseDescription(argDescMap
                    .getValue(SEARCH_PREFIX_DESCRIPTION).get());
            return new FindIncidentsCommand(new DescriptionKeywordsPredicate(descriptionKeywords));
        } else if (arePrefixesPresent(argIdMap, SEARCH_PREFIX_ID)) {
            IncidentId idKeywords = ParserUtil.parseId(argIdMap.getValue(SEARCH_PREFIX_ID).get());
            return new FindIncidentsCommand(new IdKeywordsPredicate(idKeywords));
        } else if (arePrefixesPresent(argOpMap, SEARCH_PREFIX_OPERATOR)) {
            Name nameKeywords = ParserUtil.parseName(argOpMap.getValue(SEARCH_PREFIX_OPERATOR).get());
            return new FindIncidentsCommand(new NameKeywordsPredicate(nameKeywords));
        } else if (arePrefixesPresent(argSelfMap, SEARCH_PREFIX_SELF)) {
            return new FindIncidentsCommand(SEARCH_PREFIX_SELF);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindIncidentsCommand.MESSAGE_USAGE));
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
