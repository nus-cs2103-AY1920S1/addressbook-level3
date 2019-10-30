package budgetbuddy.logic.parser.commandparsers.scriptcommandparsers;

import budgetbuddy.logic.commands.scriptcommands.ScriptEvalCommand;
import budgetbuddy.logic.parser.CommandParser;

/**
 * Parses input arguments and creates a new {@link ScriptEvalCommand}.
 */
public class ScriptEvalCommandParser implements CommandParser<ScriptEvalCommand> {
    @Override
    public String name() {
        return ScriptEvalCommand.COMMAND_WORD;
    }

    @Override
    public ScriptEvalCommand parse(String userInput) {
        return new ScriptEvalCommand(userInput);
    }
}
