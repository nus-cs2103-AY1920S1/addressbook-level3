package budgetbuddy.logic.parser.commandparsers.scriptcommandparsers;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import budgetbuddy.logic.commands.scriptcommands.ScriptAddCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CliSyntax;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.script.ScriptName;

/**
 * Parses input arguments and creates a new {@link ScriptAddCommand}.
 */
public class ScriptAddCommandParser implements CommandParser<ScriptAddCommand> {
    @Override
    public String name() {
        return ScriptAddCommand.COMMAND_WORD;
    }

    @Override
    public ScriptAddCommand parse(String userInput) throws ParseException {
        if (userInput == null || userInput.isBlank()) {
            throw new ParseException(ScriptAddCommand.MESSAGE_USAGE);
        }

        ArgumentMultimap args = ArgumentTokenizer.tokenize(userInput,
                CliSyntax.PREFIX_SCRIPT_DESCRIPTION, CliSyntax.PREFIX_SCRIPT_PATH,
                CliSyntax.PREFIX_SCRIPT_SOURCE);

        if (args.getValueCount(CliSyntax.PREFIX_SCRIPT_DESCRIPTION) > 1
                || args.getValueCount(CliSyntax.PREFIX_SCRIPT_PATH) > 1
                || args.getValueCount(CliSyntax.PREFIX_SCRIPT_SOURCE) > 1
                || args.getValueCount(CliSyntax.PREFIX_SCRIPT_PATH) > 0
                && args.getValueCount(CliSyntax.PREFIX_SCRIPT_SOURCE) > 0) {
            throw new ParseException(ScriptAddCommand.MESSAGE_USAGE);
        }

        ScriptName scriptName = CommandParserUtil.parseScriptName(args.getPreamble());
        Description desc = new Description(args.getValue(CliSyntax.PREFIX_SCRIPT_DESCRIPTION)
                .orElse(""));
        String scriptSource = args.getValue(CliSyntax.PREFIX_SCRIPT_SOURCE).orElse(null);
        if (scriptSource != null) {
            return new ScriptAddCommand(scriptName, desc, scriptSource);
        }

        Path scriptPath;
        try {
            scriptPath = args.getValue(CliSyntax.PREFIX_SCRIPT_PATH).map(str -> Path.of(str)).orElse(null);
        } catch (InvalidPathException ipe) {
            throw new ParseException(ScriptAddCommand.MESSAGE_INVALID_PATH, ipe);
        }
        return new ScriptAddCommand(scriptName, desc, scriptPath);
    }
}
