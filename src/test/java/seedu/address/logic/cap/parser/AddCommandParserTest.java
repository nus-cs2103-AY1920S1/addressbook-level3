package seedu.address.logic.cap.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.cap.commands.CommandTestUtil.CREDIT_DESC_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.MODULE_CODE_DESC_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.SEMESTER_DESC_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.TITLE_DESC_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_CREDIT_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_MODULE_CODE_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_SEMESTER_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_TITLE_CS2100;
import static seedu.address.logic.cap.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.cap.commands.AddCommand;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_MODULE_CODE_CS2100 + SEMESTER_DESC_CS2100
            + TITLE_DESC_CS2100 + CREDIT_DESC_CS2100, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, MODULE_CODE_DESC_CS2100 + VALID_SEMESTER_CS2100
            + TITLE_DESC_CS2100 + CREDIT_DESC_CS2100, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, MODULE_CODE_DESC_CS2100 + SEMESTER_DESC_CS2100
            + VALID_TITLE_CS2100 + CREDIT_DESC_CS2100, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, MODULE_CODE_DESC_CS2100 + SEMESTER_DESC_CS2100
            + TITLE_DESC_CS2100 + VALID_CREDIT_CS2100, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MODULE_CODE_CS2100 + VALID_SEMESTER_CS2100
            + VALID_TITLE_CS2100 + VALID_CREDIT_CS2100, expectedMessage);
    }
}
