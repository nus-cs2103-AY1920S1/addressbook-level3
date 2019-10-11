package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SOURCE;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.algobase.logic.commands.FindCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.problem.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.problem.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.problem.NameContainsKeywordsPredicate;
import seedu.algobase.model.problem.SourceMatchesKeywordPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AUTHOR, PREFIX_DESCRIPTION, PREFIX_SOURCE);

        // According to the command format, no preamble should be present.
        if (!argumentMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        final NameContainsKeywordsPredicate namePredicate;
        if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
            String trimmedNameArg = argumentMultimap.getValue(PREFIX_NAME).get().trim();
            String[] nameKeywords = trimmedNameArg.split("\\s+");
            namePredicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        } else {
            namePredicate = null;
        }

        final AuthorMatchesKeywordPredicate authorPredicate;
        if (argumentMultimap.getValue(PREFIX_AUTHOR).isPresent()) {
            String authorKeyword = argumentMultimap.getValue(PREFIX_AUTHOR).get();
            authorPredicate = new AuthorMatchesKeywordPredicate(authorKeyword);
        } else {
            authorPredicate = null;
        }

        final DescriptionContainsKeywordsPredicate descriptionPredicate;
        if (argumentMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            String trimmedDescriptionArg = argumentMultimap.getValue(PREFIX_DESCRIPTION).get();
            String[] descriptionKeywords = trimmedDescriptionArg.split("\\s+");
            descriptionPredicate = new DescriptionContainsKeywordsPredicate(Arrays.asList(descriptionKeywords));
        } else {
            descriptionPredicate = null;
        }

        final SourceMatchesKeywordPredicate sourcePredicate;
        if (argumentMultimap.getValue(PREFIX_SOURCE).isPresent()) {
            String sourceKeyword = argumentMultimap.getValue(PREFIX_SOURCE).get();
            sourcePredicate = new SourceMatchesKeywordPredicate(sourceKeyword);
        } else {
            sourcePredicate = null;
        }

        Predicate[] predicates = {namePredicate, authorPredicate, descriptionPredicate, sourcePredicate};
        boolean allPredicatesAreNull = true;
        for (Predicate predicate : predicates) {
            if (predicate != null) {
                allPredicatesAreNull = false;
                break;
            }
        }

        if (allPredicatesAreNull) {
            throw new ParseException(FindCommand.MESSAGE_NO_CONSTRAINTS);
        }

        return new FindCommand(namePredicate, authorPredicate, descriptionPredicate, sourcePredicate);
    }
}
