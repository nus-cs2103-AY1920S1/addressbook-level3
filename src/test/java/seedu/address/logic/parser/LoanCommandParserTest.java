package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SERIAL_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LoanCommand;
import seedu.address.model.book.SerialNumber;

class LoanCommandParserTest {
    private static final String EMPTY_STRING = "";

    private LoanCommandParser loanCommandParser = new LoanCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        SerialNumber bookSn = new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1);
        assertParseSuccess(loanCommandParser, SERIAL_NUMBER_DESC_BOOK_1, new LoanCommand(bookSn));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        assertParseFailure(loanCommandParser, EMPTY_STRING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(loanCommandParser, INVALID_SERIAL_NUMBER_DESC, SerialNumber.MESSAGE_CONSTRAINTS);
    }
}
