package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CheckCommand;

/**
 * Tests the CheckCommandParser
 */
public class CheckCommandParserTest {

    private CheckCommandParser parser = new CheckCommandParser();

    @Test
    public void parse_validArgs_returnsCheckCommand() {
        Index index = Index.fromOneBased(1);
        assertParseSuccess(parser, "1", new CheckCommand(index));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "checks 111", "Invalid command format! \n"
                + "Invalid Index Provided");
    }
}
