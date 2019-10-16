package budgetbuddy.logic.parser.commandparsers;

import budgetbuddy.logic.commands.ListCommand;
import budgetbuddy.logic.parser.CommandParser;

/**
 * Parses the <code>list</code> command.
 */
public class ListCommandParser implements CommandParser<ListCommand> {
    @Override
    public String name() {
        return ListCommand.COMMAND_WORD;
    }

    @Override
    public ListCommand parse(String userInput) {
        return new ListCommand();
    }
}
