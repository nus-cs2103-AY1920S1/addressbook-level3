package seedu.address.logic.parser.conditioncommandparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.conditioncommands.AddClassConditionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddClassConditionCommand object
 */
public class AddClassConditionCommandParser implements Parser<AddClassConditionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddClassConditionCommand
     * and returns an AddClassConditionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddClassConditionCommand parse(String args) throws ParseException {
        String classType = args.trim().toLowerCase();
        if (!(classType.equals("expense") || classType.equals("income") || classType.equals("wish")
                || classType.equals("autoexpense") || classType.equals("budget"))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassConditionCommand.MESSAGE_USAGE));
        }
        return new AddClassConditionCommand(classType);
    }
}
