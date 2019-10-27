package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_CHRISTMAS;
import static seedu.address.logic.commands.CommandTestUtil.TIMING_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC_BUTTERFLY;


import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PerformanceCommand;

public class PerformanceCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PerformanceCommand.MESSAGE_USAGE);

    private PerformanceCommandParser parser = new PerformanceCommandParser();

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser,
            "-5" + EVENT_DESC_BUTTERFLY + DATE_DESC_CHRISTMAS + TIMING_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser,
            "0" + EVENT_DESC_BUTTERFLY + DATE_DESC_CHRISTMAS + TIMING_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 u/ string", MESSAGE_INVALID_FORMAT);
    }
}
