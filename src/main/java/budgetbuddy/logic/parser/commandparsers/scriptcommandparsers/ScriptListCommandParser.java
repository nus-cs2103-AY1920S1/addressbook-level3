package budgetbuddy.logic.parser.commandparsers.scriptcommandparsers;

import budgetbuddy.logic.commands.scriptcommands.ScriptListCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link ScriptListCommand}.
 */
public class ScriptListCommandParser implements CommandParser<ScriptListCommand> {
    @Override
    public String name() {
        return ScriptListCommand.COMMAND_WORD;
    }

    @Override
    public ScriptListCommand parse(String userInput) throws ParseException {
        return new ScriptListCommand();
    }
}
