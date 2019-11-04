package seedu.jarvis.logic.parser.cca;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.cca.IncreaseProgressCommand;

public class IncreaseProgressCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, IncreaseProgressCommand.MESSAGE_USAGE);


    private IncreaseProgressCommandParser parser = new IncreaseProgressCommandParser();

    @Test
    public void parse_validInput_success() {
        assertParseSuccess(parser, "1", new IncreaseProgressCommand(Index.fromOneBased(1)));
    }

    @Test
    public void parse_missingParts_failure() {
        // missing index
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // whitespace only
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);

        // symbols only
        assertParseFailure(parser, "@", MESSAGE_INVALID_FORMAT);

        // alphabets only
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);


    }
}
