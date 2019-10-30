package seedu.address.logic.parser.conditioncommandparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.conditioncommands.AddQuotaConditionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a newQuotaConditionCommand object
 */
public class AddQuotaConditionCommandParser implements Parser<AddQuotaConditionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddQuotaConditionCommand
     * and returns an AddQuotaConditionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddQuotaConditionCommand parse(String args) throws ParseException {
        try {
            double quota = ParserUtil.parseAmount(args).value;
            return new AddQuotaConditionCommand(quota);
        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddQuotaConditionCommand.MESSAGE_USAGE));
        }
    }
}
