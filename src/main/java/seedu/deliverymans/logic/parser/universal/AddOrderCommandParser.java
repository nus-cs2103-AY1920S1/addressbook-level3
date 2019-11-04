package seedu.deliverymans.logic.parser.universal;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_RESTAURANT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import seedu.deliverymans.logic.commands.universal.AddOrderCommand;
import seedu.deliverymans.logic.parser.ArgumentMultimap;
import seedu.deliverymans.logic.parser.ArgumentTokenizer;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.Prefix;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.order.Order;

/**
 * Parses input arguments and creates a new {@code OrderCommand} object
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code OrderCommand}
     * and returns a {@code OrderCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CUSTOMER,
                PREFIX_RESTAURANT, PREFIX_FOOD, PREFIX_QUANTITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_CUSTOMER, PREFIX_RESTAURANT, PREFIX_FOOD, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }

        Name customerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_CUSTOMER).get());
        Name restaurantName = ParserUtil.parseName(argMultimap.getValue(PREFIX_RESTAURANT).get());

        ArrayList<Name> foodList = ParserUtil.parseNames(argMultimap.getAllValues(PREFIX_FOOD));
        ArrayList<Integer> quantityList = ParserUtil.parseQuantity(argMultimap.getAllValues(PREFIX_QUANTITY));
        if (foodList.size() != quantityList.size()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }
        HashMap<Name, Integer> lst = new HashMap<>();
        for (int i = 0; i < foodList.size(); ++i) {
            lst.put(foodList.get(i), quantityList.get(i));
        }

        Order orderToAdd = new Order.OrderBuilder().setCustomer(customerName).setRestaurant(restaurantName)
                .setFood(lst).completeOrder();
        return new AddOrderCommand(orderToAdd);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
