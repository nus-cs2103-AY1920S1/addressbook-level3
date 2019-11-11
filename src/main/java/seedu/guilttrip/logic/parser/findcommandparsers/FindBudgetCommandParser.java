package seedu.guilttrip.logic.parser.findcommandparsers;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.guilttrip.logic.commands.findcommands.FindBudgetCommand;
import seedu.guilttrip.logic.commands.findcommands.FindExpenseCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.predicates.EntryContainsAmountPredicate;
import seedu.guilttrip.model.entry.predicates.EntryContainsCategoryPredicate;
import seedu.guilttrip.model.entry.predicates.EntryContainsDatePredicate;
import seedu.guilttrip.model.entry.predicates.EntryContainsDescriptionPredicate;
import seedu.guilttrip.model.entry.predicates.EntryContainsTagsPredicate;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.CategoryType;

/**
 * Parses input arguments and creates a new FindBudgetCommand object
 */
public class FindBudgetCommandParser implements Parser<FindBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindBudgetCommand
     * and returns a FindBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindBudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_AMOUNT, PREFIX_DESC, PREFIX_DATE, PREFIX_TAG);

        List<Predicate<Entry>> predicateList = new ArrayList<Predicate<Entry>>();
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            String trimmedArgs = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get()).fullDesc.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindExpenseCommand.MESSAGE_USAGE));
            }

            String[] nameKeywords = trimmedArgs.split("\\s+");
            predicateList.add(new EntryContainsDescriptionPredicate(Arrays.asList(nameKeywords)));
        }

        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            String name = argMultimap.getValue(PREFIX_CATEGORY).get().trim();
            predicateList.add(new EntryContainsCategoryPredicate(new Category(name, CategoryType.EXPENSE)));
        }

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            double trimmedDouble = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get().trim()).value;
            predicateList.add(new EntryContainsAmountPredicate(trimmedDouble));
        }

        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            String name = argMultimap.getValue(PREFIX_CATEGORY).get().trim();
            predicateList.add(new EntryContainsCategoryPredicate(new Category(name, CategoryType.EXPENSE)));
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
            throw new ParseException(FindBudgetCommand.INSUFFICENT_ARGUMENTS);
        }

        return new FindBudgetCommand(predicateList);
    }

}
