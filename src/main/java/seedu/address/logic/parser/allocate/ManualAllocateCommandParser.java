/*
@author calvincxz
*/

package seedu.address.logic.parser.allocate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_NUMBER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.allocate.ManualAllocateCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.EmployeeId;

/**
 * Parses input arguments and creates a new ManualAllocateCommand object
 */
public class ManualAllocateCommandParser implements Parser<ManualAllocateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ManualAllocateCommand
     * and returns an ManualAllocateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ManualAllocateCommand parse(String args) throws ParseException, CommandException {
        requireNonNull(args);


        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMPLOYEE_NUMBER, PREFIX_EMPLOYEE_ID);

        Index eventIndex;
        Index index = null;
        EmployeeId employeeId;

        try {
            eventIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            employeeId = ParserUtil.parseEmployeeId(argMultimap.getValue(PREFIX_EMPLOYEE_ID).orElse(null));

            if (argMultimap.getValue(PREFIX_EMPLOYEE_NUMBER).isPresent()) {
                index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EMPLOYEE_NUMBER).get());
            }

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ManualAllocateCommand.MESSAGE_USAGE), pe);
        }

        return new ManualAllocateCommand(eventIndex, index, employeeId);
    }
}
