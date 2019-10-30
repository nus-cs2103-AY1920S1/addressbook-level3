package budgetbuddy.logic.parser.commandparsers.rulecommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.rulecommands.RuleDeleteCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RuleDeleteCommand object.
 */
public class RuleDeleteCommandParser implements CommandParser<RuleDeleteCommand> {
    @Override
    public String name() {
        return RuleDeleteCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RuleDeleteCommand
     * and returns an RuleDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RuleDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = CommandParserUtil.parseIndex(args);
            return new RuleDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RuleDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
