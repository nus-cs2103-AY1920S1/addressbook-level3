package budgetbuddy.logic.parser.commandparsers.accountcommandparsers;

import budgetbuddy.logic.commands.accountcommands.AccountListCommand;
import budgetbuddy.logic.parser.CommandParser;

/**
 * Parses the <code>list</code> command.
 */
public class AccountListCommandParser implements CommandParser<AccountListCommand> {
    @Override
    public String name() {
        return AccountListCommand.COMMAND_WORD;
    }

    @Override
    public AccountListCommand parse(String args) {
        return new AccountListCommand();
    }
}
