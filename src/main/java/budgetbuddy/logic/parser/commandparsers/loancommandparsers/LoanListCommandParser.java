package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import budgetbuddy.logic.commands.loancommands.LoanListCommand;
import budgetbuddy.logic.parser.CommandParser;

/**
 * Parses the <code>list</code> command.
 */
public class LoanListCommandParser implements CommandParser<LoanListCommand> {
    @Override
    public String name() {
        return LoanListCommand.COMMAND_WORD;
    }

    @Override
    public LoanListCommand parse(String args) {
        return new LoanListCommand();
    }
}
