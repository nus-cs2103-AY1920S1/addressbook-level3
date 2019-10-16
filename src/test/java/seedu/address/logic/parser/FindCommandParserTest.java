package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;

public class FindCommandParserTest {

    private static final String BODY_FLAG = "b";
    private static final String WORKER_FLAG = "w";

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand1 =
                new FindCommand(Arrays.asList("Alice", "Bob"), BODY_FLAG);
        FindCommand expectedFindCommand2 =
                new FindCommand(Arrays.asList("Alice", "Bob"), WORKER_FLAG);
        assertParseSuccess(parser, " -b Alice Bob", expectedFindCommand1);
        assertParseSuccess(parser, " -w Alice Bob", expectedFindCommand1);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " -b   Alice  Bob  ", expectedFindCommand1);
        assertParseSuccess(parser, " -w   Alice  Bob  ", expectedFindCommand1);
    }

}
