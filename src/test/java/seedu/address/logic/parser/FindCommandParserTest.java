package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand("Alice Bob".split("\\s+"), "Alice Bob");
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);
    }

    @Test
    public void parse_validArgsWithWhiteSpace_trimsWhiteSpace() {
        String userInput = " \n Alice \n \t Bob  \t";
        FindCommand expectedFindCommand =
                new FindCommand(new String[] {"Alice", "Bob"}, "Alice Bob");
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);
    }

}
