package seedu.address.logic.parser.commandparsers.viewcommandparsers;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ACCOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNTIL;

import java.util.Date;
import java.util.Optional;

import seedu.address.logic.commands.view.ViewFilterCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.CommandParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.Account;
import seedu.address.model.attributes.Category;
import seedu.address.model.transaction.TransactionMatchesConditionsPredicate;

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

        Optional<String> optionalAccountString = argMultimap.getValue(PREFIX_ACCOUNT);
        Optional<Account> optionalAccount =
                argMultimap.getValue(PREFIX_ACCOUNT).isPresent()
                        ? Optional.of(CommandParserUtil.parseAccount(optionalAccountString.get()))
                        : Optional.empty();

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

        return new ViewFilterCommand(new TransactionMatchesConditionsPredicate(optionalAccount,
                optionalCategory, optionalFrom, optionalUntil));
    }
}
