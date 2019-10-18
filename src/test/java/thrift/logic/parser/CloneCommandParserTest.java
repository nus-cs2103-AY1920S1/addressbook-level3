package thrift.logic.parser;

import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static thrift.logic.parser.CommandParserTestUtil.assertParseFailure;
import static thrift.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.CloneCommand;
import thrift.logic.commands.CommandTestUtil;
import thrift.testutil.TypicalIndexes;

public class CloneCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloneCommand.MESSAGE_USAGE);

    private CloneCommandParser parser = new CloneCommandParser();

    @Test
    public void parse_missingParts_failure() {
        //no index specified
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsCloneCommand() {
        assertParseSuccess(parser, CommandTestUtil.INDEX_TOKEN + "2", new CloneCommand(
                TypicalIndexes.INDEX_SECOND_TRANSACTION));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "clon", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "transaction number two",
                MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "1 i/", MESSAGE_INVALID_FORMAT);
    }
}
