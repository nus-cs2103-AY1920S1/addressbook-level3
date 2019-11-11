package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SERIAL_NUMBER;
import static seedu.address.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteByIndexCommand;
import seedu.address.logic.commands.DeleteBySerialNumberCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.book.SerialNumber;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteByIndexCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteByIndexCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsIndexDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteByIndexCommand(INDEX_FIRST_BOOK));
    }

    @Test
    public void parse_validArgs_returnsSerialNumberDeleteCommand() {
        SerialNumber sn = new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1);
        assertParseSuccess(parser, SERIAL_NUMBER_DESC_BOOK_1, new DeleteBySerialNumberCommand(sn));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSerialNumber_throwsParseException() {
        assertParseFailure(parser, " sn/B0001",
                String.format(MESSAGE_INVALID_SERIAL_NUMBER, DeleteCommand.MESSAGE_USAGE));
    }
}
