package seedu.moneygowhere.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.moneygowhere.logic.commands.FindCommand;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.CostInRangePredicate;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.DateInRangePredicate;
import seedu.moneygowhere.model.spending.NameContainsKeywordsPredicate;
import seedu.moneygowhere.model.spending.RemarkContainsKeywordsPredicate;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.tag.TagPredicate;

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
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_COST, PREFIX_REMARK, PREFIX_TAG);

        List<Predicate<Spending>> predicates = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String name = argMultimap.getValue(PREFIX_NAME).get();
            String[] nameKeywords = name.split("\\s+");
            predicates.add(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            List<Date> dates = ParserUtil.parseDates(argMultimap.getAllValues(PREFIX_DATE));

            if (dates.size() < 2) {
                throw new ParseException(Date.MESSAGE_CONSTRAINTS);
            }
            predicates.add(new DateInRangePredicate(dates.get(0), dates.get(1)));
        }
        if (argMultimap.getValue(PREFIX_COST).isPresent()) {
            List<Cost> costs = ParserUtil.parseCosts(argMultimap.getAllValues(PREFIX_COST));

            double min = Double.parseDouble(costs.get(0).value);
            double max = Double.parseDouble(costs.get(1).value);

            if (costs.size() != 2 || min > max) {
                throw new ParseException(Cost.MESSAGE_CONSTRAINTS);
            }

            predicates.add(new CostInRangePredicate(costs.get(0), costs.get(1)));
        }
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            String name = argMultimap.getValue(PREFIX_REMARK).get();
            String[] nameKeywords = name.split("\\s+");
            predicates.add(new RemarkContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String tag = argMultimap.getValue(PREFIX_TAG).get();
            predicates.add(new TagPredicate(tag));
        }

        if (predicates.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(predicates);
    }
}
