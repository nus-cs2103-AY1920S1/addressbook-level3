package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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
        final String arguments = matcher.group("arguments").trim();

        switch (findType) {
        case NameContainsKeywordsPredicate.FINDTYPE:
            String[] nameKeywords = arguments.split("\\s+");

            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        case AllContainsKeywordsPredicate.FINDTYPE:
            String[] keywords = arguments.split("\\s+");

            return new FindCommand(new AllContainsKeywordsPredicate(Arrays.asList(keywords)));

        case AmountWithinRangePredicate.FINDTYPE:
            String[] amountLimits = arguments.split("\\s+");
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

        default:
            throw new ParseException(String.format(MESSAGE_UNKNOWN_COMMAND, HelpCommand.MESSAGE_USAGE));
        }
    }

}
