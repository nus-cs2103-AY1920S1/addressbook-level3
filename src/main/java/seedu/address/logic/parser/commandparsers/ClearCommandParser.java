package seedu.address.logic.parser.commandparsers;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.CommandParser;

/**
 * Parses the <code>clear</code> command.
 */
public class ClearCommandParser implements CommandParser<ClearCommand> {
    @Override
    public String name() {
        return ClearCommand.COMMAND_WORD;
    }

    @Override
    public ClearCommand parse(String userInput) {
        return new ClearCommand();
    }
}
