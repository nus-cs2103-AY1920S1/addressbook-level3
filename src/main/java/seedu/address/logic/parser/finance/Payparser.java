package seedu.address.logic.parser.finance;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SalaryPaid;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.finance.Pay;
import seedu.address.logic.commands.finance.Pay.EditEmployeeDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new Pay object
 */
public class Payparser implements Parser<Pay> {

    /**
     * Parses the given {@code String} of arguments in the context of the Pay
     * and returns an Pay object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Pay parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SalaryPaid);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(("Pay: Undo the wrongly payment" +
                    " identified by the index number used in the displayed employee list.\n" +
                    "Parameters: INDEX (must be a positive integer)" +
                    " p/PAY" + "Example: pay 1 p/100")));
        }

        EditEmployeeDescriptor editEmployeeDescriptor = new EditEmployeeDescriptor();
        if (argMultimap.getValue(PREFIX_SalaryPaid).isPresent()) {
            editEmployeeDescriptor.setEmployeeSalaryPaid(ParserUtil.parsePay(argMultimap.getValue(PREFIX_SalaryPaid).get()));
        }

        if (!editEmployeeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(Pay.MESSAGE_NOT_EDITED);
        }

        return new Pay(index, editEmployeeDescriptor);
    }
}

