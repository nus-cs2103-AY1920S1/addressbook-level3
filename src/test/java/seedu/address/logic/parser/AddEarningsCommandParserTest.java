package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_CS2100;
import static seedu.address.logic.commands.CommandTestUtil.CLASSID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_CS2100;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASSID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_CS2100;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_EARNINGS_CS2100_A01;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EARNINGS_CS2100_A01;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_EARNINGS_CS2100_A01;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEarningsCommand;
import seedu.address.model.classid.ClassId;
import seedu.address.model.earnings.Date;

public class AddEarningsCommandParserTest {
    private AddEarningsCommandParser parser = new AddEarningsCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEarningsCommand.MESSAGE_USAGE);

        // missing amount prefix
        assertParseFailure(parser, DATE_DESC_CS2100
                        + TYPE_DESC_CS2100 + VALID_AMOUNT_EARNINGS_CS2100_A01 + CLASSID_DESC_AMY,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, VALID_DATE_EARNINGS_CS2100_A01
                        + TYPE_DESC_CS2100 + CLASSID_DESC_AMY + AMOUNT_DESC_CS2100,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_DATE_EARNINGS_CS2100_A01
                        + VALID_TYPE_EARNINGS_CS2100_A01 + VALID_AMOUNT_EARNINGS_CS2100_A01
                + VALID_CLASSID_AMY,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC + TYPE_DESC_CS2100
                + CLASSID_DESC_AMY + AMOUNT_DESC_CS2100, Date.MESSAGE_CONSTRAINTS);

        // invalid classid
        assertParseFailure(parser, DATE_DESC_CS2100 + TYPE_DESC_CS2100
                + INVALID_CLASSID_DESC + AMOUNT_DESC_CS2100, ClassId.MESSAGE_CONSTRAINTS);


    }
}
