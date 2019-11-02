package budgetbuddy.logic.parser.commandparsers.rulecommandparsers;

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
        Index index = CommandParserUtil.parseIndex(args);
        return new RuleDeleteCommand(index);
    }
}
