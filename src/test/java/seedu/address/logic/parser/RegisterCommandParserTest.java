package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalBorrowers.AMY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RegisterCommand;
import seedu.address.model.borrower.Borrower;
import seedu.address.testutil.BorrowerBuilder;

public class RegisterCommandParserTest {
    private RegisterCommandParser parser = new RegisterCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Borrower expectedBorrower = new BorrowerBuilder(AMY).build();
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY, new RegisterCommand(expectedBorrower));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, EMAIL_DESC_AMY + PHONE_DESC_AMY, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_AMY + EMAIL_DESC_AMY, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, "", expectedMessage);
    }
}
