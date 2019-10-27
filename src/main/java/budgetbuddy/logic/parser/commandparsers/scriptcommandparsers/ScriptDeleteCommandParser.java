package budgetbuddy.logic.parser.commandparsers.scriptcommandparsers;

import budgetbuddy.logic.commands.scriptcommands.ScriptDeleteCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link ScriptDeleteCommand}.
 */
public class ScriptDeleteCommandParser implements CommandParser<ScriptDeleteCommand> {
    @Override
    public String name() {
        return ScriptDeleteCommand.COMMAND_WORD;
    }

    @Override
    public ScriptDeleteCommand parse(String userInput) throws ParseException {
        if (userInput == null || userInput.isBlank()) {
            throw new ParseException(ScriptDeleteCommand.MESSAGE_USAGE);
        }

        return new ScriptDeleteCommand(CommandParserUtil.parseScriptName(userInput));
    }
}
