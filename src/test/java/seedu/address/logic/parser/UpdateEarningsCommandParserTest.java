package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_CS2100;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_CS1231;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASSID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_CS1231;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_EARNINGS_CS2100_A01;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EARNINGS_CS1231_T05;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_EARNINGS_CS1231_T05;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateEarningsCommand;
import seedu.address.logic.commands.UpdateEarningsCommand.EditEarningsDescriptor;
import seedu.address.model.classid.ClassId;
import seedu.address.model.earnings.Amount;
import seedu.address.model.earnings.Date;
import seedu.address.model.earnings.Type;
import seedu.address.testutil.UpdateEarningsDescriptorBuilder;

public class UpdateEarningsCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateEarningsCommand.MESSAGE_USAGE);

    private UpdateEarningsCommandParser parser = new UpdateEarningsCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DATE_EARNINGS_CS1231_T05, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", UpdateEarningsCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DATE_DESC_CS1231, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DATE_DESC_CS1231, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_TYPE_DESC, Type.MESSAGE_CONSTRAINTS); // invalid type
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS); // invalid amount
        assertParseFailure(parser, "1" + INVALID_CLASSID_DESC, ClassId.MESSAGE_CONSTRAINTS); // invalid classid

    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + DATE_DESC_CS1231 + AMOUNT_DESC_CS2100;

        EditEarningsDescriptor descriptor = new UpdateEarningsDescriptorBuilder()
                .withDate(VALID_DATE_EARNINGS_CS1231_T05)
                .withAmount(VALID_AMOUNT_EARNINGS_CS2100_A01).build();
        UpdateEarningsCommand expectedCommand = new UpdateEarningsCommand(targetIndex, descriptor);

        //assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // date
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + DATE_DESC_CS1231;
        EditEarningsDescriptor descriptor =
                new UpdateEarningsDescriptorBuilder().withDate(VALID_DATE_EARNINGS_CS1231_T05).build();
        UpdateEarningsCommand expectedCommand = new UpdateEarningsCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // class part
        userInput = targetIndex.getOneBased() + TYPE_DESC_CS1231;
        descriptor = new UpdateEarningsDescriptorBuilder().withType(VALID_TYPE_EARNINGS_CS1231_T05).build();
        expectedCommand = new UpdateEarningsCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
