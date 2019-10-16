package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_Position;
import static seedu.address.logic.parser.CliSyntax.PREFIX_Gender;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JoinDate;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Employee.*;
import seedu.address.model.Employee.EmployeeID;
import seedu.address.model.Employee.Employee;
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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_Gender, PREFIX_Position, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_JoinDate, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_Gender, PREFIX_Position, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_JoinDate, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        EmployeeID employeeID = ParserUtil.parseEmploteeID();
        EmployeeName employeeName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        EmployeeGender employeeGender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_Gender).get());
        EmployeePosition employeePosition = ParserUtil.parsePosition(argMultimap.getValue(PREFIX_Position).get());
        EmployeePhone employeePhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        EmployeeEmail employeeEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        EmployeeAddress employeeAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        EmployeeJoinDate employeeJoinDate = ParserUtil.parseJoinDate(argMultimap.getValue(PREFIX_JoinDate).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Employee employee = new Employee(employeeID, employeeName, employeeGender, employeePosition,
                 employeePhone, employeeEmail, employeeAddress, employeeJoinDate, tagList);

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
