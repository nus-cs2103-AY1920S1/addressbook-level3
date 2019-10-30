package seedu.address.logic.parser.addcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BRAND_DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.BRAND_DESC_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.CAPACITY_DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.CAPACITY_DESC_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.COLOUR_DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.COLOUR_DESC_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.COST_DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.COST_DESC_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.IDENTITY_NUM_DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.IDENTITY_NUM_DESC_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BRAND_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CAPACITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COLOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COST_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_IDENTITY_NUM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SERIAL_NUM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_NAME_DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_NAME_DESC_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SERIAL_NUM_DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.SERIAL_NUM_DESC_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BESTSELLER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NEW;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_REGULAR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_RICH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BRAND_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAPACITY_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOUR_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IDENTITY_NUMBER_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_NAME_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BESTSELLER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_REGULAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_RICH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPhones.IPHONEPRO11;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.addcommand.AddPhoneCommand;
import seedu.address.model.phone.Brand;
import seedu.address.model.phone.Capacity;
import seedu.address.model.phone.Colour;
import seedu.address.model.phone.Cost;
import seedu.address.model.phone.IdentityNumber;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.PhoneName;
import seedu.address.model.phone.SerialNumber;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PhoneBuilder;

public class AddPhoneCommandParserTest {
    private AddPhoneCommandParser parser = new AddPhoneCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        Phone expectedPhone = new PhoneBuilder(IPHONEPRO11).withTags(VALID_TAG_NEW, VALID_TAG_BESTSELLER).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE
                        + COST_DESC_IPHONE + TAG_DESC_NEW + TAG_DESC_BESTSELLER,
                new AddPhoneCommand(expectedPhone));

        // multiple identity num - last identity num accepted
        assertParseSuccess(parser, IDENTITY_NUM_DESC_SAMSUNG + IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE
                        + COLOUR_DESC_IPHONE + COST_DESC_IPHONE + TAG_DESC_NEW + TAG_DESC_BESTSELLER,
                new AddPhoneCommand(expectedPhone));

        // multiple serial num - last serial num accepted
        assertParseSuccess(parser, IDENTITY_NUM_DESC_IPHONE + SERIAL_NUM_DESC_SAMSUNG
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE
                        + COLOUR_DESC_IPHONE + COST_DESC_IPHONE + TAG_DESC_NEW + TAG_DESC_BESTSELLER,
                new AddPhoneCommand(expectedPhone));


        // multiple phone name - last phone name accepted
        assertParseSuccess(parser, IDENTITY_NUM_DESC_IPHONE + SERIAL_NUM_DESC_IPHONE
                        + PHONE_NAME_DESC_SAMSUNG + PHONE_NAME_DESC_IPHONE + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE
                        + COLOUR_DESC_IPHONE + COST_DESC_IPHONE + TAG_DESC_NEW + TAG_DESC_BESTSELLER,
                new AddPhoneCommand(expectedPhone));


        // multiple brand - last brand accepted
        assertParseSuccess(parser, IDENTITY_NUM_DESC_IPHONE + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_SAMSUNG + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE
                        + COLOUR_DESC_IPHONE + COST_DESC_IPHONE + TAG_DESC_NEW + TAG_DESC_BESTSELLER,
                new AddPhoneCommand(expectedPhone));


        // multiple capacity - last capacity accepted
        assertParseSuccess(parser, IDENTITY_NUM_DESC_IPHONE + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_SAMSUNG + CAPACITY_DESC_IPHONE
                        + COLOUR_DESC_IPHONE + COST_DESC_IPHONE + TAG_DESC_NEW + TAG_DESC_BESTSELLER,
                new AddPhoneCommand(expectedPhone));


        // multiple colour - last colour accepted
        assertParseSuccess(parser, IDENTITY_NUM_DESC_IPHONE + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_SAMSUNG + COLOUR_DESC_IPHONE
                        + COST_DESC_IPHONE + TAG_DESC_NEW + TAG_DESC_BESTSELLER,
                new AddPhoneCommand(expectedPhone));


        // multiple cost - last cost accepted
        assertParseSuccess(parser, IDENTITY_NUM_DESC_IPHONE + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_SAMSUNG
                        + COST_DESC_IPHONE + TAG_DESC_NEW + TAG_DESC_BESTSELLER,
                new AddPhoneCommand(expectedPhone));



        // multiple tags - all accepted
        Phone expectedPhoneMultipleTags = new PhoneBuilder(IPHONEPRO11)
                .withTags(VALID_TAG_NEW, VALID_TAG_BESTSELLER, VALID_TAG_RICH, VALID_TAG_REGULAR)
                .build();
        assertParseSuccess(parser, IDENTITY_NUM_DESC_IPHONE + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE
                        + TAG_DESC_NEW + TAG_DESC_BESTSELLER + TAG_DESC_REGULAR + TAG_DESC_RICH,
                new AddPhoneCommand(expectedPhoneMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Phone expectedPhone = new PhoneBuilder(IPHONEPRO11).withTags().build();
        assertParseSuccess(parser, IDENTITY_NUM_DESC_IPHONE + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE,
                new AddPhoneCommand(expectedPhone));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhoneCommand.MESSAGE_USAGE);

        // missing identity number prefix
        assertParseFailure(parser, VALID_IDENTITY_NUMBER_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE,
                expectedMessage);

        // missing serial number prefix
        assertParseFailure(parser, IDENTITY_NUM_DESC_IPHONE
                        + VALID_SERIAL_NUMBER_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE,
                expectedMessage);

        // missing phone name prefix
        assertParseFailure(parser, IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + VALID_PHONE_NAME_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE,
                expectedMessage);

        // missing brand prefix
        assertParseFailure(parser, IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + VALID_BRAND_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE,
                expectedMessage);

        // missing capacity prefix
        assertParseFailure(parser, IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + VALID_CAPACITY_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE,
                expectedMessage);

        // missing colour prefix
        assertParseFailure(parser, IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + VALID_COLOUR_IPHONE + COST_DESC_IPHONE,
                expectedMessage);

        // missing cost prefix
        assertParseFailure(parser, IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + VALID_COST_IPHONE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_IDENTITY_NUMBER_IPHONE
                        + VALID_SERIAL_NUMBER_IPHONE + VALID_PHONE_NAME_IPHONE
                        + VALID_BRAND_IPHONE + VALID_CAPACITY_IPHONE + VALID_COLOUR_IPHONE + VALID_COST_IPHONE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid identity number
        assertParseFailure(parser, INVALID_IDENTITY_NUM_DESC
                + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE,
                IdentityNumber.MESSAGE_CONSTRAINTS);


        // invalid serial number
        assertParseFailure(parser, IDENTITY_NUM_DESC_IPHONE
                        + INVALID_SERIAL_NUM_DESC + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE,
                SerialNumber.MESSAGE_CONSTRAINTS);


        // invalid phone name
        assertParseFailure(parser, IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + INVALID_PHONE_NAME_DESC
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE,
                PhoneName.MESSAGE_CONSTRAINTS);


        // invalid brand
        assertParseFailure(parser, IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + INVALID_BRAND_DESC + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE,
                Brand.MESSAGE_CONSTRAINTS);

        // invalid capacity
        assertParseFailure(parser, IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + INVALID_CAPACITY_DESC + COLOUR_DESC_IPHONE + COST_DESC_IPHONE,
                Capacity.MESSAGE_CONSTRAINTS);


        // invalid colour
        assertParseFailure(parser, IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + INVALID_COLOUR_DESC + COST_DESC_IPHONE,
                Colour.MESSAGE_CONSTRAINTS);


        // invalid cost
        assertParseFailure(parser, IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + INVALID_COST_DESC,
                Cost.MESSAGE_CONSTRAINTS);


        // invalid tags
        assertParseFailure(parser, IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE
                        + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_IDENTITY_NUM_DESC
                        + INVALID_SERIAL_NUM_DESC + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE,
                IdentityNumber.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + IDENTITY_NUM_DESC_IPHONE
                        + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                        + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE + COST_DESC_IPHONE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhoneCommand.MESSAGE_USAGE));
    }
}
