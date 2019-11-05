package seedu.address.logic.parser.deletecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deletecommand.DeleteCustomerCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCustomerCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCustomerCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCustomerCommandParserTest {

    private DeleteCustomerCommandParser parser = new DeleteCustomerCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCustomerCommand.MESSAGE_USAGE));
    }
}
