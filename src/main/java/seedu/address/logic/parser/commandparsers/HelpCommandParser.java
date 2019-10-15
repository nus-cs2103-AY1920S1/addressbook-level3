package seedu.address.logic.parser.commandparsers;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.CommandParser;

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
