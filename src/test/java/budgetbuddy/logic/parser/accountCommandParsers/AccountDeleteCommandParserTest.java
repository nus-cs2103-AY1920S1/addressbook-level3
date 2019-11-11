package budgetbuddy.logic.parser.accountCommandParsers;

import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static budgetbuddy.logic.parser.CommandParserUtil.MESSAGE_INVALID_INDEX;
import static budgetbuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import budgetbuddy.logic.commands.accountcommands.AccountDeleteCommand;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.parser.commandparsers.accountcommandparsers.AccountDeleteCommandParser;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.testutil.TypicalIndexes;

public class AccountDeleteCommandParserTest {

    private AccountDeleteCommandParser parser = new AccountDeleteCommandParser();

    @Test
    public void parse_validArgs_success() throws CommandException {
        assertParseSuccess(parser, "1", new AccountDeleteCommand(TypicalIndexes.INDEX_FIRST_ITEM));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_INDEX);
        assertThrows(ParseException.class, () -> parser.parse("a"));
    }
}
