package budgetbuddy.logic.parser.commandparsers.scriptcommandparsers;

import budgetbuddy.logic.commands.scriptcommands.ScriptRunCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.script.ScriptName;

/**
 * Parses input arguments and creates a new {@link ScriptRunCommand}.
 */
public class ScriptRunCommandParser implements CommandParser<ScriptRunCommand> {
    @Override
    public String name() {
        return ScriptRunCommand.COMMAND_WORD;
    }

    @Override
    public ScriptRunCommand parse(String userInput) throws ParseException {
        if (userInput == null || userInput.isBlank()) {
            throw new ParseException(ScriptRunCommand.MESSAGE_USAGE);
        }

        String[] nameArgs = userInput.stripLeading().split(" ", 2);
        ScriptName name = CommandParserUtil.parseScriptName(nameArgs[0]);
        String arguments = nameArgs.length >= 2 ? nameArgs[1] : "";

        return new ScriptRunCommand(name, arguments);
    }
}
