package seedu.eatme.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;

import seedu.eatme.logic.commands.FindCommand;
import seedu.eatme.logic.parser.exceptions.ParseException;
import seedu.eatme.model.eatery.EateryAttributesContainsKeywordsPredicate;

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
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_CATEGORY, PREFIX_TAG);

        List<String> nameKeywords = new ArrayList<>();
        List<String> addressKeywords = new ArrayList<>();
        List<String> categoryKeywords = new ArrayList<>();
        List<String> tagKeywords = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            addressKeywords = argMultimap.getAllValues(PREFIX_ADDRESS);
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            categoryKeywords = argMultimap.getAllValues(PREFIX_CATEGORY);
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            tagKeywords = argMultimap.getAllValues(PREFIX_TAG);
        }

        if (nameKeywords.isEmpty() && addressKeywords.isEmpty()
                && categoryKeywords.isEmpty() && tagKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new EateryAttributesContainsKeywordsPredicate(
                nameKeywords, addressKeywords, categoryKeywords, tagKeywords));
    }

}
