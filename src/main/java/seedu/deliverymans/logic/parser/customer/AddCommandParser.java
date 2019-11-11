package seedu.deliverymans.logic.parser.customer;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.deliverymans.logic.commands.customer.CustomerAddCommand;
import seedu.deliverymans.logic.parser.ArgumentMultimap;
import seedu.deliverymans.logic.parser.ArgumentTokenizer;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.Prefix;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.customer.Address;
import seedu.deliverymans.model.customer.Customer;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<CustomerAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CustomerAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomerAddCommand.MESSAGE_USAGE));
        }

        Name userName = ParserUtil.parseName(argMultimap.getValue(PREFIX_USERNAME).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Customer customer = new Customer(userName, name, phone, address);

        return new CustomerAddCommand(customer);
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
