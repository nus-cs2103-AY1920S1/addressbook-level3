package seedu.address.logic.parser.allocate;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_NUMBER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.allocate.ManualAllocateCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.employee.EmployeeId;

class ManualAllocateCommandParserTest {

    private static final String ID_000 = " " + PREFIX_EMPLOYEE_ID + "000";
    private static final String EMPLOYEE_INDEX_ONE = " " + PREFIX_EMPLOYEE_NUMBER + "1";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ManualAllocateCommand.MESSAGE_USAGE);

    private ManualAllocateCommandParser parser = new ManualAllocateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, CommandTestUtil.MANPOWER_COUNT_DESC, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // only id specified
        assertParseFailure(parser, ID_000, MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_idSpecified_success() throws CommandException {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + ID_000;
        ManualAllocateCommand expectedCommand = new ManualAllocateCommand(targetIndex, null,
                new EmployeeId("000"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_indexSpecified_success() throws CommandException {
        Index targetIndex = INDEX_FIRST_EVENT;
        Index targetEmployeeIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + EMPLOYEE_INDEX_ONE;
        ManualAllocateCommand expectedCommand = new ManualAllocateCommand(targetIndex, targetEmployeeIndex,
                null);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ManualAllocateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs2_throwsParseException() {
        assertParseFailure(parser, "n/5 id/000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ManualAllocateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs3_throwsParseException() {
        assertParseFailure(parser, "  id/0  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ManualAllocateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs4_throwsParseException() {
        assertParseFailure(parser, " 1  id/0  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ManualAllocateCommand.MESSAGE_USAGE));
    }
}
