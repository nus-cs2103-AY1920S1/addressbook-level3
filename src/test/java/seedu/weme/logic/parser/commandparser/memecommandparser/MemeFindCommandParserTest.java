package seedu.weme.logic.parser.commandparser.memecommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.memecommand.MemeFindCommand;
import seedu.weme.model.meme.TagContainsKeywordsPredicate;

public class MemeFindCommandParserTest {

    private MemeFindCommandParser parser = new MemeFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        MemeFindCommand expectedMemeFindCommand =
                new MemeFindCommand(new TagContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedMemeFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedMemeFindCommand);
    }

}
