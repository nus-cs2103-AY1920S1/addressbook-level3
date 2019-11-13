package budgetbuddy.logic.parser.commandparsers.accountcommandparsers;

import budgetbuddy.logic.commands.accountcommands.AccountOverviewCommand;
import budgetbuddy.logic.parser.CommandParser;

/**
 * Parses the <code>overview</code> command.
 */
public class AccountOverviewCommandParser implements CommandParser<AccountOverviewCommand> {
    @Override
    public String name() {
        return AccountOverviewCommand.COMMAND_WORD;
    }

    @Override
    public AccountOverviewCommand parse(String args) {
        return new AccountOverviewCommand();
    }
}
