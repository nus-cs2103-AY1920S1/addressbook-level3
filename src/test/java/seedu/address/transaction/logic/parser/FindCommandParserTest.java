package seedu.address.transaction.logic.parser;

import static seedu.address.transaction.logic.parser.CommandParserTestUtil.assertCommandParseFailure;
import static seedu.address.transaction.logic.parser.CommandParserTestUtil.assertCommandParseSuccess;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_INVALID_FIND_COMMAND_FORMAT;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.transaction.logic.commands.FindCommand;
import seedu.address.transaction.model.transaction.TransactionContainsKeywordsPredicate;

class FindCommandParserTest {
    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertCommandParseFailure(parser, "     ", MESSAGE_INVALID_FIND_COMMAND_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new TransactionContainsKeywordsPredicate(Arrays.asList("Alice", "Benson")));
        assertCommandParseSuccess(parser, "Alice Benson", expectedFindCommand);

        // multiple whitespaces between keywords
        assertCommandParseSuccess(parser, " \n Alice \n \t Benson  \t", expectedFindCommand);
    }
}
