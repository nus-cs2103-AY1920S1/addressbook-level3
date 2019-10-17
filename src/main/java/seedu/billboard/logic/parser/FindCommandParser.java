package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.billboard.logic.commands.FindCommand;
import seedu.billboard.logic.commands.HelpCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;
import seedu.billboard.model.expense.AllContainsKeywordsPredicate;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.AmountWithinRangePredicate;
import seedu.billboard.model.expense.DateWithinRangePredicate;
import seedu.billboard.model.expense.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern FIND_COMMAND_FORMAT = Pattern.compile("(?<findType>\\S+)(?<arguments>(.|\n)*)");

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        final Matcher matcher = FIND_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        final String findType = matcher.group("findType");
        final String arguments = matcher.group("arguments");
        final String trimmedArguments = arguments.trim();

        switch (findType) {
        case NameContainsKeywordsPredicate.FINDTYPE:
            String[] nameKeywords = trimmedArguments.split("\\s+");

            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        case AllContainsKeywordsPredicate.FINDTYPE:
            String[] keywords = trimmedArguments.split("\\s+");

            return new FindCommand(new AllContainsKeywordsPredicate(Arrays.asList(keywords)));

        case AmountWithinRangePredicate.FINDTYPE:
            String[] amountLimits = trimmedArguments.split("\\s+");
            try {
                Amount lowerBound;
                switch (amountLimits.length) {
                case 1:
                    lowerBound = ParserUtil.parseAmount(amountLimits[0]);
                    return new FindCommand(new AmountWithinRangePredicate(lowerBound));

                case 2:
                    lowerBound = ParserUtil.parseAmount(amountLimits[0]);
                    Amount upperBound = ParserUtil.parseAmount(amountLimits[1]);
                    return new FindCommand(new AmountWithinRangePredicate(
                            lowerBound,
                            upperBound));

                default:
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
            } catch (NumberFormatException ex) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

        case DateWithinRangePredicate.FINDTYPE:
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_START_DATE, PREFIX_END_DATE);

            if (!argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

            LocalDateTime startDate = ParserUtil.parseCreatedDateTime(argMultimap.getValue(PREFIX_START_DATE).get())
                    .dateTime;
            LocalDateTime endDate = argMultimap.getValue(PREFIX_END_DATE).isPresent()
                    ? ParserUtil.parseCreatedDateTime(argMultimap.getValue(PREFIX_END_DATE).get()).dateTime
                    : LocalDateTime.now();
            return new FindCommand(new DateWithinRangePredicate(startDate, endDate));

        default:
            throw new ParseException(String.format(MESSAGE_UNKNOWN_COMMAND, HelpCommand.MESSAGE_USAGE));
        }
    }

}
