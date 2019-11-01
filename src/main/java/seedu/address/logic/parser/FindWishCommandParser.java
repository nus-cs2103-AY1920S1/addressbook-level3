package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindWishCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Entry;
import seedu.address.model.person.predicates.EntryContainsAmountPredicate;
import seedu.address.model.person.predicates.EntryContainsCategoryPredicate;
import seedu.address.model.person.predicates.EntryContainsDatePredicate;
import seedu.address.model.person.predicates.EntryContainsDescriptionPredicate;
import seedu.address.model.person.predicates.EntryContainsTagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindWishCommandParser implements Parser<FindWishCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindWishCommand
     * and returns a FindWishCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindWishCommand parse(String args) throws ParseException {
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
            predicateList.add(new EntryContainsDescriptionPredicate(Arrays.asList(nameKeywords)));
        }

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            double trimmedDouble = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get().trim()).value;
            predicateList.add(new EntryContainsAmountPredicate(trimmedDouble));
        }

        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            String name = argMultimap.getValue(PREFIX_CATEGORY).get().trim();
            predicateList.add(new EntryContainsCategoryPredicate(name));
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            LocalDate dateToCompare = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get().trim()).getDate();
            predicateList.add(new EntryContainsDatePredicate(dateToCompare));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            predicateList.add(new EntryContainsTagsPredicate(tagList));
        }

        if (predicateList.size() == 0) {
            throw new ParseException(FindWishCommand.INSUFFICENT_ARGUMENTS);
        }

        return new FindWishCommand(predicateList);
    }

}
