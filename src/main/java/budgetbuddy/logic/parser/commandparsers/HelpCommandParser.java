package budgetbuddy.logic.parser.commandparsers;

import budgetbuddy.logic.commands.HelpCommand;
import budgetbuddy.logic.parser.CommandParser;

/**
 * Parses the <code>help</code> command.
 */
public class HelpCommandParser implements CommandParser<HelpCommand> {
    @Override
    public String name() {
        return HelpCommand.COMMAND_WORD;
    }

    @Override
    public HelpCommand parse(String userInput) {
        return new HelpCommand();
    }
}
