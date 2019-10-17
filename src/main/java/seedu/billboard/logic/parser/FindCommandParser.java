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
    private static final Pattern FIND_COMMAND_FORMAT = Pattern.compile("(?<findType>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        final Matcher matcher = FIND_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String findType = matcher.group("findType");
        final String arguments = matcher.group("arguments");
        final String trimedArguments = arguments.trim();

        switch (findType) {
        case NameContainsKeywordsPredicate.FINDTYPE:
            String[] nameKeywords = trimedArguments.split("\\s+");

            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        case AllContainsKeywordsPredicate.FINDTYPE:
            String[] keywords = trimedArguments.split("\\s+");

            return new FindCommand(new AllContainsKeywordsPredicate(Arrays.asList(keywords)));

        case AmountWithinRangePredicate.FINDTYPE:
            String[] amountLimits = trimedArguments.split("\\s+");
            try {
                switch (amountLimits.length) {
                case 1:
                    return new FindCommand(new AmountWithinRangePredicate(Float.parseFloat(amountLimits[0])));

                case 2:
                    return new FindCommand(new AmountWithinRangePredicate(
                            Float.parseFloat(amountLimits[0]),
                            Float.parseFloat(amountLimits[1])));

                default:
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
                }
            } catch (NumberFormatException ex) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
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
