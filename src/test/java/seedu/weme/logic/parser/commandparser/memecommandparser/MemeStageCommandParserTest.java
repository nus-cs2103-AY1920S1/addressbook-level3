package seedu.weme.logic.parser.commandparser.memecommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.memecommand.MemeStageCommand;


class MemeStageCommandParserTest {

    private MemeStageCommandParser parser = new MemeStageCommandParser();

    @Test
    public void parse_validArgs_returnsStageCommand() {
        assertParseSuccess(parser, "1", new MemeStageCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeStageCommand.MESSAGE_USAGE));
    }

}
