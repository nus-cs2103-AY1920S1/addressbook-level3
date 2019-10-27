package seedu.jarvis.logic.parser.finance;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.finance.FindPurchaseCommand;
import seedu.jarvis.model.finance.PurchaseNameContainsKeywordsPredicate;

public class FindPurchaseCommandParserTest {

    private FindPurchaseCommandParser parser = new FindPurchaseCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindPurchaseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindPurchaseCommand expectedFindPurchaseCommand =
                new FindPurchaseCommand(new PurchaseNameContainsKeywordsPredicate(Arrays.asList(
                        "lunch", "dinner")));
        assertParseSuccess(parser, "lunch dinner", expectedFindPurchaseCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n lunch \n \t dinner  \t", expectedFindPurchaseCommand);
    }
}
