package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BORROWER_ID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ServeCommand;
import seedu.address.model.borrower.BorrowerId;

public class ServeCommandParserTest {
    private ServeCommandParser parser = new ServeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        BorrowerId expectedBorrowerId = new BorrowerId(VALID_ID_BOB);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + BORROWER_ID_DESC_BOB,
                new ServeCommand(expectedBorrowerId));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ServeCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);
    }
}
