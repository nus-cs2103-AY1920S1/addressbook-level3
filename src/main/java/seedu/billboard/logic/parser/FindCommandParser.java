package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_AMOUNT_LIMIT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Arrays;

import seedu.billboard.logic.commands.FindCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.MultiArgPredicate;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_DESCRIPTION, PREFIX_AMOUNT, PREFIX_AMOUNT_LIMIT, PREFIX_START_DATE, PREFIX_END_DATE);

        if (isAllFieldsEmpty(argMultimap) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        MultiArgPredicate predicate = new MultiArgPredicate();

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_DESCRIPTION).get().trim().split("\\s+");
            predicate.setKeywords(Arrays.asList(keywords));
        }

        Amount lowerAmtLimit = null;
        Amount upperAmtLimit = null;

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            lowerAmtLimit = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        }

        if (argMultimap.getValue(PREFIX_AMOUNT_LIMIT).isPresent()) {
            upperAmtLimit = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT_LIMIT).get());
        }

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent() || argMultimap.getValue(PREFIX_AMOUNT_LIMIT).isPresent()) {
            predicate.setAmtRange(lowerAmtLimit, upperAmtLimit);
        }

        CreatedDateTime startDate = null;
        CreatedDateTime endDate = null;

        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            startDate = ParserUtil.parseCreatedDateTime(argMultimap.getValue(PREFIX_START_DATE).get());
        }

        if (argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            endDate = ParserUtil.parseCreatedDateTime(argMultimap.getValue(PREFIX_END_DATE).get());
        }

        if (argMultimap.getValue(PREFIX_START_DATE).isPresent() || argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            predicate.setDateRange(startDate, endDate);
        }

        return new FindCommand(predicate);
    }

    /**
     * Returns true if all of the prefixes contain empty {@code Optional} values in the given
     * {@code ArgumentMultiMap}
     */
    private boolean isAllFieldsEmpty(ArgumentMultimap argMultimap) {
        return !argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()
                && !argMultimap.getValue(PREFIX_AMOUNT).isPresent()
                && !argMultimap.getValue(PREFIX_START_DATE).isPresent()
                && !argMultimap.getValue(PREFIX_END_DATE).isPresent();
    }
}
