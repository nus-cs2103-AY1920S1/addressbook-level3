package seedu.address.logic.parser.allocate;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseIndexSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseInvalidIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseInvalidPreambleArgsFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNegativeIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNoIndexAndFieldFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseZeroIndexFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.allocate.DeallocateCommand;
import seedu.address.model.employee.EmployeeId;


/**
 * Contains unit tests for {@code DeallocateCommandParserTest}.
 */
class DeallocateCommandParserTest {
    private static final String VALID_EMPLOYEE_ID_INPUT = " " + PREFIX_EMPLOYEE_ID + "000";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeallocateCommand.MESSAGE_USAGE);
    private DeallocateCommandParser parser = new DeallocateCommandParser();

    @Test
    void parse_emptyInvalidFormat_throwsParseException() {
        assertParseNoIndexAndFieldFailure(parser, MESSAGE_INVALID_FORMAT);
        assertParseNegativeIndexFailure(parser, MESSAGE_INVALID_FORMAT);
        assertParseZeroIndexFailure(parser, MESSAGE_INVALID_FORMAT);
        assertParseInvalidIndexFailure(parser, MESSAGE_INVALID_FORMAT);
    }

    @Test
    void parse_validArgs_returnsDeallocateCommand() {
        assertParseIndexSuccess(parser, new DeallocateCommand(INDEX_FIRST_EVENT));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseInvalidPreambleArgsFailure(parser, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 n/1", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 id/1.0", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 id/1000", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_idSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + VALID_EMPLOYEE_ID_INPUT;
        DeallocateCommand expectedCommand = new DeallocateCommand(targetIndex,
                new EmployeeId("000"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_idSpecifiedWithSpaces_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + "   " + VALID_EMPLOYEE_ID_INPUT + "    ";
        DeallocateCommand expectedCommand = new DeallocateCommand(targetIndex,
                new EmployeeId("000"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
