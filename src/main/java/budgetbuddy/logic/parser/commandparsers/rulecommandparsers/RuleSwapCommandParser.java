package budgetbuddy.logic.parser.commandparsers.rulecommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.rulecommands.RuleSwapCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RuleSwapCommand object.
 */
public class RuleSwapCommandParser implements CommandParser<RuleSwapCommand> {
    @Override
    public String name() {
        return RuleSwapCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RuleSwapCommand
     * and returns an RuleSwapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RuleSwapCommand parse(String args) throws ParseException {
        String[] indexPair = args.trim().split("\\s+");
        if (indexPair.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RuleSwapCommand.MESSAGE_USAGE));
        }

        Index firstIndex = CommandParserUtil.parseIndex(indexPair[0]);
        Index secondIndex = CommandParserUtil.parseIndex(indexPair[1]);

        return new RuleSwapCommand(firstIndex, secondIndex);
    }
}
