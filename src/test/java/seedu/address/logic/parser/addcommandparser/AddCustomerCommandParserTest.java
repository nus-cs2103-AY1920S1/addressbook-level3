package seedu.address.logic.parser.addcommandparser;

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
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BEN;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_REGULAR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_RICH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_NUMBER_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_REGULAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_RICH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCustomers.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.addcommand.AddCustomerCommand;
import seedu.address.model.customer.ContactNumber;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.CustomerName;
import seedu.address.model.customer.Email;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CustomerBuilder;

public class AddCustomerCommandParserTest {
    private AddCustomerCommandParser parser = new AddCustomerCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Customer expectedCustomer = new CustomerBuilder(ALICE).withTags(VALID_TAG_RICH).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE
                + EMAIL_DESC_ALICE + TAG_DESC_RICH, new AddCustomerCommand(expectedCustomer));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_BEN + NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE
                + EMAIL_DESC_ALICE + TAG_DESC_RICH, new AddCustomerCommand(expectedCustomer));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_ALICE + CONTACT_NUMBER_DESC_BEN
                + CONTACT_NUMBER_DESC_ALICE + EMAIL_DESC_ALICE
                + TAG_DESC_RICH, new AddCustomerCommand(expectedCustomer));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE
                + EMAIL_DESC_BEN + EMAIL_DESC_ALICE
                + TAG_DESC_RICH, new AddCustomerCommand(expectedCustomer));

        // multiple tags - all accepted
        Customer expectedCustomerMultipleTags = new CustomerBuilder(ALICE).withTags(VALID_TAG_RICH, VALID_TAG_REGULAR)
                .build();
        assertParseSuccess(parser, NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE + EMAIL_DESC_ALICE
                + TAG_DESC_RICH + TAG_DESC_REGULAR, new AddCustomerCommand(expectedCustomerMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Customer expectedCustomer = new CustomerBuilder(ALICE).withTags().build();
        assertParseSuccess(parser, NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE + EMAIL_DESC_ALICE,
                new AddCustomerCommand(expectedCustomer));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_ALICE + CONTACT_NUMBER_DESC_ALICE + EMAIL_DESC_ALICE,
                expectedMessage);

        // missing contact number prefix
        assertParseFailure(parser, NAME_DESC_ALICE + VALID_CONTACT_NUMBER_ALICE + EMAIL_DESC_ALICE,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE + VALID_EMAIL_ALICE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_ALICE + VALID_CONTACT_NUMBER_ALICE + VALID_EMAIL_ALICE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_CUSTOMER_NAME_DESC + CONTACT_NUMBER_DESC_ALICE
                + EMAIL_DESC_ALICE + TAG_DESC_RICH, CustomerName.MESSAGE_CONSTRAINTS);

        // invalid contact number
        assertParseFailure(parser, NAME_DESC_ALICE + INVALID_CONTACT_NUMBER_DESC
                + EMAIL_DESC_ALICE + TAG_DESC_RICH, ContactNumber.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE
                + INVALID_EMAIL_DESC + TAG_DESC_RICH, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE
                + EMAIL_DESC_ALICE + INVALID_TAG_DESC + TAG_DESC_RICH, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_CUSTOMER_NAME_DESC + CONTACT_NUMBER_DESC_ALICE
                        + INVALID_EMAIL_DESC ,
                CustomerName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE
                        + EMAIL_DESC_ALICE + TAG_DESC_RICH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerCommand.MESSAGE_USAGE));
    }
}
