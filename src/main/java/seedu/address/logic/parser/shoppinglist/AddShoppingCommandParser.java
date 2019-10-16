package seedu.address.logic.parser.shoppinglist;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.shoppinglist.AddShoppingCommand;
import seedu.address.logic.parser.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.food.*;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddShoppingCommandParser implements Parser<AddShoppingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     * @return
     */
    public AddShoppingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT);

        //TO BE CHANGED LATER SINCE SHOULD BE ABLE TO EDIT ONLY AMOUNT
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        ShoppingItem food = new ShoppingItem(name, amount);

        return new AddShoppingCommand(food);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
