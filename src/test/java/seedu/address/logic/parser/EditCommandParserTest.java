package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditBorrowerCommand;
import seedu.address.logic.commands.EditBorrowerCommand.EditBorrowerDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.borrower.Email;
import seedu.address.model.borrower.Phone;
import seedu.address.testutil.EditBorrowerDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_GENRE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBorrowerCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {

        // no field specified
        assertParseFailure(parser, PREAMBLE_WHITESPACE, EditBorrowerCommand.MESSAGE_NOT_EDITED);
        assertParseFailure(parser, "", EditBorrowerCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_notInServeMode_failure() {
        assertParseFailure(parser, PREAMBLE_WHITESPACE, EditBorrowerCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
        //invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid email
        assertParseFailure(parser, INVALID_PHONE_DESC + VALID_EMAIL_AMY,
                Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, INVALID_PHONE_DESC + VALID_PHONE_AMY,
                Phone.MESSAGE_CONSTRAINTS);


        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_PHONE_DESC + INVALID_EMAIL_DESC,
                Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() throws CommandException {
        String userInput = NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY;

        EditBorrowerDescriptor descriptor = new EditBorrowerDescriptorBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build();
        EditBorrowerCommand expectedCommand = new EditBorrowerCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() throws CommandException {
        String userInput = NAME_DESC_AMY + EMAIL_DESC_AMY;
        EditBorrowerDescriptor descriptor = new EditBorrowerDescriptorBuilder().withName(VALID_NAME_AMY)
                .withEmail(VALID_EMAIL_AMY).build();
        EditBorrowerCommand expectedCommand = new EditBorrowerCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws CommandException {
        // name
        String userInput = NAME_DESC_AMY;
        EditBorrowerDescriptor descriptor = new EditBorrowerDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditBorrowerCommand expectedCommand = new EditBorrowerCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = PHONE_DESC_AMY;
        descriptor = new EditBorrowerDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditBorrowerCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = EMAIL_DESC_AMY;
        descriptor = new EditBorrowerDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditBorrowerCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
