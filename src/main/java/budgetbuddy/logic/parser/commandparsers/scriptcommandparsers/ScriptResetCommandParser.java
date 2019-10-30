package budgetbuddy.logic.parser.commandparsers.scriptcommandparsers;

import budgetbuddy.logic.commands.scriptcommands.ScriptResetCommand;
import budgetbuddy.logic.parser.CommandParser;

/**
 * Parses input arguments and creates a new {@link ScriptResetCommand}.
 */
public class ScriptResetCommandParser implements CommandParser<ScriptResetCommand> {
    @Override
    public String name() {
        return ScriptResetCommand.COMMAND_WORD;
    }

    @Override
    public ScriptResetCommand parse(String userInput) {
        return new ScriptResetCommand();
    }
}
