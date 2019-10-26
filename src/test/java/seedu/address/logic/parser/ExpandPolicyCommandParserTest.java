package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExpandPolicyCommand;

public class ExpandPolicyCommandParserTest {

    private ExpandPolicyCommandParser parser = new ExpandPolicyCommandParser();

    @Test
    public void parse_validArgs_returnsExpandPolicyCommand() {
        assertParseSuccess(parser, "1", new ExpandPolicyCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExpandPolicyCommand.MESSAGE_USAGE));
    }
}
