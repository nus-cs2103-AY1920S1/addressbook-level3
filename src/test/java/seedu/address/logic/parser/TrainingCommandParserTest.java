package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TrainingCommandAbsent;
import seedu.address.logic.commands.TrainingCommandPresent;

class TrainingCommandParserTest {
    private TrainingCommandParser parser = new TrainingCommandParser();

    @Test
    void parseWithoutFlag_noDate_noIndexPrefix() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrainingCommandPresent.MESSAGE_USAGE);
        assertParseFailure(parser, "1 3 5", expectedMessage);
    }

    @Test
    void parseWithFlag_noDate_noIndexPrefix() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrainingCommandAbsent.MESSAGE_USAGE);
        assertParseFailure(parser, "-a 1 3 5", expectedMessage);
    }

    @Test
    void parseWithoutFlag_noDate_invalidTrailingText() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrainingCommandPresent.MESSAGE_USAGE);
        assertParseFailure(parser, "#/1 3 5 haha", expectedMessage);
    }

    @Test
    void parseWithoutFlag_invalidDate() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrainingCommandPresent.MESSAGE_USAGE);
        assertParseFailure(parser, "d/1234567890 #/1 3 5", expectedMessage);
    }

}
