package unrealunity.visit.logic.parser;

import org.junit.jupiter.api.Test;

import unrealunity.visit.commons.core.index.Index;
import unrealunity.visit.logic.commands.FollowUpCommand;

public class FollowUpCommandParserTest {
    private FollowUpCommandParser parser = new FollowUpCommandParser();

    @Test
    public void parseTest() {

        CommandParserTestUtil.assertParseSuccess(parser, "1 d/7",
                new FollowUpCommand(Index.fromOneBased(1), 7));

        CommandParserTestUtil.assertParseSuccess(parser, "1",
                new FollowUpCommand(Index.fromOneBased(1), 7));

    }
}
