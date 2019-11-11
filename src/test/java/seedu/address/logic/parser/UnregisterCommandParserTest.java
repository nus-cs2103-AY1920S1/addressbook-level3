package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalBorrowers.ID_DESC_FIRST_BORROWER;
import static seedu.address.testutil.TypicalBorrowers.ID_FIRST_BORROWER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnregisterCommand;


public class UnregisterCommandParserTest {
    private UnregisterCommandParser parser = new UnregisterCommandParser();

    @Test
    public void parse_validArgs_returnsUnregisterCommand() {
        assertParseSuccess(parser, ID_DESC_FIRST_BORROWER, new UnregisterCommand(ID_FIRST_BORROWER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1234",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnregisterCommand.MESSAGE_USAGE));
    }

}
