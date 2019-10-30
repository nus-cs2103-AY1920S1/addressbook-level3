package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Entry;
import seedu.address.model.person.predicates.AmountContainsValuePredicate;
import seedu.address.model.person.predicates.DescriptionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_DESC, PREFIX_DATE, PREFIX_AMOUNT, PREFIX_TAG);
        List<Predicate<Entry>> predicateList = new ArrayList<Predicate<Entry>>();
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            String trimmedArgs = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get()).fullDesc.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

            String[] nameKeywords = trimmedArgs.split("\\s+");
            predicateList.add(new DescriptionContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            double trimmedDouble = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()).value;
            predicateList.add(new AmountContainsValuePredicate(trimmedDouble));
        }

        if (predicateList.size() == 0) {
            throw new ParseException(FindCommand.INSUFFICENT_ARGUMENTS);
        }

        return new FindCommand(predicateList);
    }

}
