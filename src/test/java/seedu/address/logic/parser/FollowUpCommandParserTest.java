package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FollowUpCommand;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class FollowUpCommandParserTest {
    private FollowUpCommandParser parser = new FollowUpCommandParser();

    @Test
    public void parseTest() {

        assertParseSuccess(parser, "1 d/7",
                new FollowUpCommand(Index.fromOneBased(1), 7));

        assertParseSuccess(parser, "1",
                new FollowUpCommand(Index.fromOneBased(1), 7));

    }
}
