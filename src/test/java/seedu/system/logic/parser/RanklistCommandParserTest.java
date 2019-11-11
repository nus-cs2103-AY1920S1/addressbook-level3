package seedu.system.logic.parser;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.logic.commands.CommandTestUtil.DEADLIFT_RANK_METHOD_DESC;
import static seedu.system.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.system.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.system.logic.commands.RankMethod;
import seedu.system.logic.commands.insession.RanklistCommand;
import seedu.system.logic.parser.insession.RanklistCommandParser;

public class RanklistCommandParserTest {

    private RanklistCommandParser parser = new RanklistCommandParser();

    @Test
    public void parse_validArgs_returnsRanklistCommand() {
        assertParseSuccess(parser, DEADLIFT_RANK_METHOD_DESC, new RanklistCommand(RankMethod.DEADLIFT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "cannot/ deadlift", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RanklistCommand.MESSAGE_USAGE));
    }

}
