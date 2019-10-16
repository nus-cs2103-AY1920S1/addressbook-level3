package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import budgetbuddy.logic.commands.loancommands.ListLoansCommand;
import budgetbuddy.logic.parser.CommandParser;

/**
 * Parses the <code>list</code> command.
 */
public class ListLoansCommandParser implements CommandParser<ListLoansCommand> {
    @Override
    public String name() {
        return ListLoansCommand.COMMAND_WORD;
    }

    @Override
    public ListLoansCommand parse(String args) {
        return new ListLoansCommand();
    }
}
