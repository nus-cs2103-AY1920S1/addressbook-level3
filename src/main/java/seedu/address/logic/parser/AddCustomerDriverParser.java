package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteIdCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;

public class AddDriverCustomerParser implements Parser<AddCustomerDriverCommand> {

    public AddDriverCustomerParser parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CUSTOMER, PREFIX_DRIVER, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        //check all prefix are present
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty() || getNoOfPrefixesPresent(argMultimap, PREFIX_CUSTOMER, PREFIX_DRIVER) != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteIdCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Prefix foundPrefix = getPrefixPresent(argMultimap, PREFIX_CUSTOMER, PREFIX_DRIVER);

        if (foundPrefix.getPrefix().equals(PREFIX_CUSTOMER.getPrefix())) {
            Customer person = new Customer(name, phone, email, address, tagList);
        } else {
            Driver person = new Driver(name, phone, email, address, tagList);
        }

        return new AddCustomerDriverCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static int getNoOfPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return (int) Stream.of(prefixes).filter(prefix -> argumentMultimap.getValue(prefix).isPresent()).count();
    }

    private static Prefix getPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream
                .of(prefixes)
                .filter(prefix -> argumentMultimap.getValue(prefix).isPresent())
                .findFirst()
                .get();
    }
}