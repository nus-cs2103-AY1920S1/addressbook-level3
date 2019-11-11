package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.verification.CheckCommand;
import seedu.address.logic.parser.verification.CheckCommandParser;

public class CheckCommandParserTest {

    private CheckCommandParser parser = new CheckCommandParser();

    @Test
    public void parse_emptyArg_success() {
        CheckCommand expectedCheckCommand = new CheckCommand("ALL");
        assertParseSuccess(parser, "    ", expectedCheckCommand);
    }

    @Test
    public void parse_mcs_success() {
        CheckCommand expectedCheckCommand = new CheckCommand("MCS");
        assertParseSuccess(parser, "mcs", expectedCheckCommand);
    }

    @Test
    public void parse_core_success() {
        CheckCommand expectedCheckCommand = new CheckCommand("CORE");
        assertParseSuccess(parser, " core ", expectedCheckCommand);
    }

    @Test
    public void parse_focus_success() {
        CheckCommand expectedCheckCommand = new CheckCommand("FOCUS");
        assertParseSuccess(parser, " fOcUs  ", expectedCheckCommand);
    }

    @Test
    public void parse_gibberish_throwsParseException() {
        assertParseFailure(parser, "mcsmcs", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CheckCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "hmm", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CheckCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "CS1101S", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CheckCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "core focus", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CheckCommand.MESSAGE_USAGE));
    }
}
