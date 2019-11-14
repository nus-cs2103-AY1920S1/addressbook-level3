/*
@@author calvincxz
 */

package seedu.address.logic.parser.allocate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.allocate.DeallocateCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.EmployeeId;


/**
 * Parses input arguments and creates a new DeallocateCommand object
 */
public class DeallocateCommandParser implements Parser<DeallocateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeallocateCommand
     * and returns a DeallocateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeallocateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EMPLOYEE_ID);

        Index index;
        EmployeeId employeeId;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            employeeId = ParserUtil.parseEmployeeId(argMultimap.getValue(PREFIX_EMPLOYEE_ID).orElse(null));

        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeallocateCommand.MESSAGE_USAGE), pe);
        }

        return new DeallocateCommand(index, employeeId);
    }


}
