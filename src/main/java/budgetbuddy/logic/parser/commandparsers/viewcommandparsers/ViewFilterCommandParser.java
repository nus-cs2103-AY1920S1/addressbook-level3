package budgetbuddy.logic.parser.commandparsers.viewcommandparsers;

import static budgetbuddy.logic.parser.CliSyntax.PREFIX_ACCOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_FROM;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_UNTIL;

import java.util.Date;
import java.util.Optional;

import budgetbuddy.logic.commands.view.ViewFilterCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.transaction.TransactionMatchesConditionsPredicate;

/**
 * Parses input arguments and creates a new <code>ViewFilterCommand</code> object.
 */
public class ViewFilterCommandParser implements CommandParser<ViewFilterCommand> {
    @Override
    public String name() {
        return ViewFilterCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ViewFilterCommand
     * and returns a ViewFilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewFilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ACCOUNT, PREFIX_CATEGORY, PREFIX_FROM, PREFIX_UNTIL);

        //if arguments are not present, return empty Optionals

        // TODO handle accounts

        Optional<String> optionalCategoryString = argMultimap.getValue(PREFIX_CATEGORY);
        Optional<Category> optionalCategory =
                argMultimap.getValue(PREFIX_CATEGORY).isPresent()
                        ? Optional.of(CommandParserUtil.parseCategory(optionalCategoryString.get()))
                        : Optional.empty();

        Optional<String> optionalFromString = argMultimap.getValue(PREFIX_FROM);
        Optional<Date> optionalFrom =
                argMultimap.getValue(PREFIX_FROM).isPresent()
                        ? Optional.of(CommandParserUtil.parseDate(optionalFromString.get()))
                        : Optional.empty();

        Optional<String> optionalUntilString = argMultimap.getValue(PREFIX_UNTIL);
        Optional<Date> optionalUntil =
                argMultimap.getValue(PREFIX_UNTIL).isPresent()
                        ? Optional.of(CommandParserUtil.parseDate(optionalUntilString.get()))
                        : Optional.empty();

        return new ViewFilterCommand(new TransactionMatchesConditionsPredicate(optionalCategory,
                optionalFrom, optionalUntil));
    }
}
