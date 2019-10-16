package budgetbuddy.logic.parser.commandparsers;

import budgetbuddy.logic.commands.ExitCommand;
import budgetbuddy.logic.parser.CommandParser;

/**
 * Parses the <code>exit</code> command.
 */
public class ExitCommandParser implements CommandParser<ExitCommand> {
    @Override
    public String name() {
        return ExitCommand.COMMAND_WORD;
    }

    @Override
    public ExitCommand parse(String userInput) {
        return new ExitCommand();
    }
}
