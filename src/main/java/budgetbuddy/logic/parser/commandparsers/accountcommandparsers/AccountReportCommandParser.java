package budgetbuddy.logic.parser.commandparsers.accountcommandparsers;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.accountcommands.AccountReportCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AccountReportCommand object.
 */
public class AccountReportCommandParser implements CommandParser<AccountReportCommand> {
    @Override
    public String name() {
        return AccountReportCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ReportCommand
     * and returns a ReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AccountReportCommand parse(String args) throws ParseException {
        Index index = CommandParserUtil.parseIndex(args);
        return new AccountReportCommand(index);
    }
}
