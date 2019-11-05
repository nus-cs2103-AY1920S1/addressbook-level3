package seedu.deliverymans.logic.parser.customer;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.customer.CustomerEditCommand;
import seedu.deliverymans.logic.commands.customer.CustomerEditCommand.EditCustomerDescriptor;
import seedu.deliverymans.logic.parser.ArgumentMultimap;
import seedu.deliverymans.logic.parser.ArgumentTokenizer;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<CustomerEditCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CustomerEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomerEditCommand.MESSAGE_USAGE),
                    pe);
        }

        EditCustomerDescriptor editCustomerDescriptor = new EditCustomerDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editCustomerDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editCustomerDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editCustomerDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (!editCustomerDescriptor.isAnyFieldEdited()) {
            throw new ParseException(CustomerEditCommand.MESSAGE_NOT_EDITED);
        }

        return new CustomerEditCommand(index, editCustomerDescriptor);
    }
}
