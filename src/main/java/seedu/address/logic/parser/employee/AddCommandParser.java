package seedu.address.logic.parser.employee;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.employee.AddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeAddress;
import seedu.address.model.employee.EmployeeEmail;
import seedu.address.model.employee.EmployeeGender;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.EmployeePay;
import seedu.address.model.employee.EmployeePhone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_PAY,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GENDER, PREFIX_PAY,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        EmployeeId employeeId;
        try {
            employeeId = ParserUtil.parseEmployeeId(new EmployeeId().id);
        } catch (IllegalArgumentException e) {
            throw new ParseException(EmployeeId.MESSAGE_CONSTRAINTS);
        }

        EmployeeName employeeName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        EmployeeGender employeeGender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        EmployeePay employeePay = ParserUtil.parsePay(argMultimap.getValue(PREFIX_PAY).get());
        EmployeePhone employeePhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        EmployeeEmail employeeEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        EmployeeAddress employeeAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));


        Employee employee = new Employee(employeeId, employeeName, employeeGender,
                employeePay, employeePhone, employeeEmail, employeeAddress, tagList);

        return new AddCommand(employee);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
