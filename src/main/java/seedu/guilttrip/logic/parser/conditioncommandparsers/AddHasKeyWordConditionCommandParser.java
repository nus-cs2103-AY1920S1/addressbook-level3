package seedu.guilttrip.logic.parser.conditioncommandparsers;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.guilttrip.logic.commands.conditioncommands.AddClassConditionCommand;
import seedu.guilttrip.logic.commands.conditioncommands.AddHasKeyWordConditionCommand;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a newAddKeyWordsConditionCommand object
 */
public class AddHasKeyWordConditionCommandParser implements Parser<AddHasKeyWordConditionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddClassConditionCommand
     * and returns an AddClassConditionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddHasKeyWordConditionCommand parse(String args) throws ParseException {
        if (args == null || args.equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassConditionCommand.MESSAGE_USAGE));
        }
        List<String> keywords = ParserUtil.parseKeyWords(args.trim());
        return new AddHasKeyWordConditionCommand(keywords);
    }
}
