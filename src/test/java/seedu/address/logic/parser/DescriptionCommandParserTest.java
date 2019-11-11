package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.verification.DescriptionCommand;
import seedu.address.logic.parser.verification.DescriptionCommandParser;

public class DescriptionCommandParserTest {

    private DescriptionCommandParser parser = new DescriptionCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DescriptionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidModule_throwsParseException() {
        assertParseFailure(parser, "cs11111", MESSAGE_INVALID_MODULE);
    }

    @Test
    public void parse_validModule_returnsDescriptionCommand() {
        DescriptionCommand expectedDescriptionCommand = new DescriptionCommand("CS1231S");
        assertParseSuccess(parser, "CS1231S", expectedDescriptionCommand);
        assertParseSuccess(parser, " cs1231s ", expectedDescriptionCommand);
    }
}
