package seedu.address.logic.parser.conditioncommandparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.logic.commands.conditioncommands.AddClassConditionCommand;
import seedu.address.logic.commands.conditioncommands.AddKeyWordsConditionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a newAddKeyWordsConditionCommand object
 */
public class AddKeyWordsConditionCommandParser implements Parser<AddKeyWordsConditionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddClassConditionCommand
     * and returns an AddClassConditionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddKeyWordsConditionCommand parse(String args) throws ParseException {
        if (args == null || args.equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassConditionCommand.MESSAGE_USAGE));
        }
        List<String> keywords = ParserUtil.parseKeyWords(args.trim());
        return new AddKeyWordsConditionCommand(keywords);
    }
}
