package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.AutoSortCommand;

public class AutoSortCommandParserTest {
    private AutoSortCommandParser parser;
    private String onFlag;
    private String offFlag;
    private String invalidFlag;

    @BeforeEach
    public void setUp() {
        parser = new AutoSortCommandParser();
        onFlag = "ON";
        offFlag = "OFF";
        invalidFlag = "invalid";
    }

    @Test
    public void parse_invalidFlag_failure() {
        assertParseFailure(parser, invalidFlag,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AutoSortCommand.MESSAGE_ERROR));
    }

    @Test
    public void parse_validFlag_success() {
        assertParseSuccess(parser, onFlag, new AutoSortCommand(true));
        assertParseSuccess(parser, offFlag, new AutoSortCommand(false));
    }
}
