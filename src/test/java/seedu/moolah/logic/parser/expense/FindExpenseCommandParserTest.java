package seedu.moolah.logic.parser.expense;

import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.expense.FindExpenseCommand;
import seedu.moolah.model.general.DescriptionContainsKeywordsPredicate;

public class FindExpenseCommandParserTest {

    private FindExpenseCommandParser parser = new FindExpenseCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
                parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindExpenseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindExpenseCommand expectedFindExpenseCommand =
                new FindExpenseCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindExpenseCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindExpenseCommand);
    }

}
