package seedu.moolah.logic.parser.budget;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_TIMESTAMP;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.budget.SwitchPeriodCommand;
import seedu.moolah.model.expense.Timestamp;

public class SwitchPeriodCommandParserTest {
    private static final String VALID_TIMESTAMP = "05-01-2019";
    private static final String VALID_TIMESTAMP_DESC = " " + PREFIX_TIMESTAMP + "05-01-2019";
    private static final String INVALID_TIMESTAMP_DESC = " " + PREFIX_TIMESTAMP + "asdf";

    private SwitchPeriodCommandParser parser = new SwitchPeriodCommandParser();

    @Test
    void parse_nullArgument_nullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    void parse_blankArgument_parseException() {
        assertParseFailure(
                parser,
                "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchPeriodCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchPeriodCommand.MESSAGE_USAGE);

        // missing timestamp prefix
        assertParseFailure(parser, VALID_TIMESTAMP, expectedMessage);
    }

    @Test
    public void parse_repeatedPrefix_failure() {
        assertParseFailure(parser, VALID_TIMESTAMP_DESC + VALID_TIMESTAMP_DESC,
                MESSAGE_REPEATED_PREFIX_COMMAND);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid timestamp
        assertParseFailure(
                parser, INVALID_TIMESTAMP_DESC, Timestamp.MESSAGE_CONSTRAINTS_GENERAL);
    }

    @Test
    void parse_allFieldsPresent_success() {
        // valid timestamp
        Timestamp validInput = Timestamp.createTimestampIfValid(VALID_TIMESTAMP).get().toStartOfDay();
        assertParseSuccess(parser, VALID_TIMESTAMP_DESC,
                new SwitchPeriodCommand(validInput));
    }
}
