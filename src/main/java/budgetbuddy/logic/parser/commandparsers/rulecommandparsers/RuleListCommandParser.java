package budgetbuddy.logic.parser.commandparsers.rulecommandparsers;

import budgetbuddy.logic.commands.rulecommands.RuleListCommand;
import budgetbuddy.logic.parser.CommandParser;

/**
 * Creates a new RuleListCommand object.
 */
public class RuleListCommandParser implements CommandParser<RuleListCommand> {
    @Override
    public String name() {
        return RuleListCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RuleListCommand
     * and returns a RuleListCommand object for execution.
     */
    @Override
    public RuleListCommand parse(String args) {
        return new RuleListCommand();
    }
}
