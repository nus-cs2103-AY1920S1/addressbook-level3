package seedu.address.logic.parser.editcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_NUMBER_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_NUMBER_DESC_BEN;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BEN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTACT_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CUSTOMER_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_REGULAR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_RICH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_NUMBER_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_NUMBER_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_REGULAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_RICH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CUSTOMER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CUSTOMER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.editcommand.EditCustomerCommand;
import seedu.address.logic.commands.editcommand.EditCustomerCommand.EditCustomerDescriptor;
import seedu.address.model.customer.ContactNumber;
import seedu.address.model.customer.CustomerName;
import seedu.address.model.customer.Email;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditCustomerDescriptorBuilder;

public class EditCustomerCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCustomerCommand.MESSAGE_USAGE);

    private EditCustomerCommandParser parser = new EditCustomerCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_ALICE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCustomerCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_ALICE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_ALICE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_CUSTOMER_NAME_DESC,
                CustomerName.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_CONTACT_NUMBER_DESC,
                ContactNumber.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid contact followed by valid email
        assertParseFailure(parser, "1" + INVALID_CONTACT_NUMBER_DESC + EMAIL_DESC_ALICE,
                ContactNumber.MESSAGE_CONSTRAINTS);

        // valid contact number followed by invalid contact number.
        // The test case for invalid contact number followed by valid contact number
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + CONTACT_NUMBER_DESC_ALICE + INVALID_CONTACT_NUMBER_DESC,
                ContactNumber.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Customer} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_REGULAR + TAG_DESC_RICH + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_REGULAR + TAG_EMPTY + TAG_DESC_RICH,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_REGULAR + TAG_DESC_RICH,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_CUSTOMER_NAME_DESC + INVALID_EMAIL_DESC
                        + VALID_CONTACT_NUMBER_ALICE, CustomerName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_CUSTOMER;
        String userInput = targetIndex.getOneBased() + CONTACT_NUMBER_DESC_ALICE + TAG_DESC_REGULAR
                + EMAIL_DESC_ALICE + NAME_DESC_ALICE + TAG_DESC_RICH;

        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder().withCustomerName(VALID_NAME_ALICE)
                .withContactNumber(VALID_CONTACT_NUMBER_ALICE).withEmail(VALID_EMAIL_ALICE)
                .withTags(VALID_TAG_RICH, VALID_TAG_REGULAR).build();
        EditCustomerCommand expectedCommand = new EditCustomerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_CUSTOMER;
        String userInput = targetIndex.getOneBased() + CONTACT_NUMBER_DESC_BEN + EMAIL_DESC_ALICE;

        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder()
                .withContactNumber(VALID_CONTACT_NUMBER_BEN)
                .withEmail(VALID_EMAIL_ALICE).build();
        EditCustomerCommand expectedCommand = new EditCustomerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_CUSTOMER;
        String userInput = targetIndex.getOneBased() + NAME_DESC_ALICE;
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder()
                .withCustomerName(VALID_NAME_ALICE).build();
        EditCustomerCommand expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // contact number
        userInput = targetIndex.getOneBased() + CONTACT_NUMBER_DESC_ALICE;
        descriptor = new EditCustomerDescriptorBuilder().withContactNumber(VALID_CONTACT_NUMBER_ALICE).build();
        expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_ALICE;
        descriptor = new EditCustomerDescriptorBuilder().withEmail(VALID_EMAIL_ALICE).build();
        expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_REGULAR;
        descriptor = new EditCustomerDescriptorBuilder().withTags(VALID_TAG_REGULAR).build();
        expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_CUSTOMER;
        String userInput = targetIndex.getOneBased() + CONTACT_NUMBER_DESC_ALICE + EMAIL_DESC_ALICE
                + TAG_DESC_REGULAR + CONTACT_NUMBER_DESC_BEN + EMAIL_DESC_BEN + TAG_DESC_REGULAR
                + CONTACT_NUMBER_DESC_BEN + EMAIL_DESC_BEN + TAG_DESC_RICH;

        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder()
                .withContactNumber(VALID_CONTACT_NUMBER_BEN)
                .withEmail(VALID_EMAIL_BEN).withTags(VALID_TAG_REGULAR, VALID_TAG_RICH)
                .build();
        EditCustomerCommand expectedCommand = new EditCustomerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_CUSTOMER;
        String userInput = targetIndex.getOneBased() + INVALID_CONTACT_NUMBER_DESC + CONTACT_NUMBER_DESC_BEN;
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder()
                .withContactNumber(VALID_CONTACT_NUMBER_BEN).build();
        EditCustomerCommand expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BEN + INVALID_CONTACT_NUMBER_DESC
                + CONTACT_NUMBER_DESC_BEN;
        descriptor = new EditCustomerDescriptorBuilder().withContactNumber(VALID_CONTACT_NUMBER_BEN)
                .withEmail(VALID_EMAIL_BEN).build();
        expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_CUSTOMER;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder().withTags().build();
        EditCustomerCommand expectedCommand = new EditCustomerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

