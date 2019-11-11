package seedu.address.logic.parser.editcommandparser;

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
import static seedu.address.logic.commands.CommandTestUtil.SERIAL_NUM_DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.SERIAL_NUM_DESC_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BESTSELLER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BRAND_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BRAND_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAPACITY_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAPACITY_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOUR_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOUR_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IDENTITY_NUMBER_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IDENTITY_NUMBER_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_NAME_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_NAME_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BESTSELLER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PHONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PHONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PHONE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.editcommand.EditPhoneCommand;
import seedu.address.logic.commands.editcommand.EditPhoneCommand.EditPhoneDescriptor;
import seedu.address.model.phone.Brand;
import seedu.address.model.phone.Capacity;
import seedu.address.model.phone.Colour;
import seedu.address.model.phone.Cost;
import seedu.address.model.phone.IdentityNumber;
import seedu.address.model.phone.PhoneName;
import seedu.address.model.phone.SerialNumber;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPhoneDescriptorBuilder;

public class EditPhoneCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPhoneCommand.MESSAGE_USAGE);

    private EditPhoneCommandParser parser = new EditPhoneCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PHONE_NAME_IPHONE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditPhoneCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PHONE_NAME_DESC_IPHONE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PHONE_NAME_DESC_IPHONE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 a/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_SERIAL_NUM_DESC,
                SerialNumber.MESSAGE_CONSTRAINTS); // invalid serial number
        assertParseFailure(parser, "1" + INVALID_IDENTITY_NUM_DESC,
                IdentityNumber.MESSAGE_CONSTRAINTS); // invalid identity number
        assertParseFailure(parser, "1" + INVALID_PHONE_NAME_DESC, PhoneName.MESSAGE_CONSTRAINTS); // invalid phone name
        assertParseFailure(parser, "1" + INVALID_BRAND_DESC, Brand.MESSAGE_CONSTRAINTS); // invalid brand
        assertParseFailure(parser, "1" + INVALID_CAPACITY_DESC, Capacity.MESSAGE_CONSTRAINTS); // invalid capacity
        assertParseFailure(parser, "1" + INVALID_COLOUR_DESC, Colour.MESSAGE_CONSTRAINTS); // invalid colour
        assertParseFailure(parser, "1" + INVALID_COST_DESC, Cost.MESSAGE_CONSTRAINTS); // invalid cost
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone name followed by valid brand
        assertParseFailure(parser, "1" + INVALID_PHONE_NAME_DESC + BRAND_DESC_IPHONE,
                PhoneName.MESSAGE_CONSTRAINTS);

        // valid phone name followed by invalid phone name
        assertParseFailure(parser, "1" + PHONE_NAME_DESC_IPHONE + INVALID_PHONE_NAME_DESC,
                PhoneName.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Phone} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_NEW + TAG_DESC_BESTSELLER + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_NEW + TAG_EMPTY + TAG_DESC_BESTSELLER,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_NEW + TAG_DESC_BESTSELLER,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_SERIAL_NUM_DESC + INVALID_BRAND_DESC + INVALID_CAPACITY_DESC,
                SerialNumber.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PHONE;
        String userInput = targetIndex.getOneBased() + IDENTITY_NUM_DESC_IPHONE
                + SERIAL_NUM_DESC_IPHONE + PHONE_NAME_DESC_IPHONE
                + BRAND_DESC_IPHONE + CAPACITY_DESC_IPHONE + COLOUR_DESC_IPHONE
                + COST_DESC_IPHONE + TAG_DESC_NEW + TAG_DESC_BESTSELLER;

        EditPhoneDescriptor descriptor = new EditPhoneDescriptorBuilder()
                .withSerialNumber(VALID_SERIAL_NUMBER_IPHONE)
                .withIdentityNumber(VALID_IDENTITY_NUMBER_IPHONE)
                .withBrand(VALID_BRAND_IPHONE)
                .withCapacity(VALID_CAPACITY_IPHONE)
                .withColour(VALID_COLOUR_IPHONE)
                .withPhoneName(VALID_PHONE_NAME_IPHONE)
                .withCost(VALID_COST_IPHONE)
                .withTags(VALID_TAG_NEW, VALID_TAG_BESTSELLER).build();
        EditPhoneCommand expectedCommand = new EditPhoneCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PHONE;
        String userInput = targetIndex.getOneBased() + BRAND_DESC_SAMSUNG + CAPACITY_DESC_IPHONE;

        EditPhoneDescriptor descriptor = new EditPhoneDescriptorBuilder()
                .withBrand(VALID_BRAND_SAMSUNG)
                .withCapacity(VALID_CAPACITY_IPHONE).build();
        EditPhoneCommand expectedCommand = new EditPhoneCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PHONE;
        String userInput = targetIndex.getOneBased() + PHONE_NAME_DESC_IPHONE;
        EditPhoneDescriptor descriptor = new EditPhoneDescriptorBuilder()
                .withPhoneName(VALID_PHONE_NAME_IPHONE).build();
        EditPhoneCommand expectedCommand = new EditPhoneCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


        // serial number
        userInput = targetIndex.getOneBased() + SERIAL_NUM_DESC_IPHONE;
        descriptor = new EditPhoneDescriptorBuilder().withSerialNumber(VALID_SERIAL_NUMBER_IPHONE).build();
        expectedCommand = new EditPhoneCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


        // identity number
        userInput = targetIndex.getOneBased() + IDENTITY_NUM_DESC_IPHONE;
        descriptor = new EditPhoneDescriptorBuilder().withIdentityNumber(VALID_IDENTITY_NUMBER_IPHONE).build();
        expectedCommand = new EditPhoneCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


        // brand
        userInput = targetIndex.getOneBased() + BRAND_DESC_IPHONE;
        descriptor = new EditPhoneDescriptorBuilder().withBrand(VALID_BRAND_IPHONE).build();
        expectedCommand = new EditPhoneCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


        // capacity
        userInput = targetIndex.getOneBased() + CAPACITY_DESC_IPHONE;
        descriptor = new EditPhoneDescriptorBuilder().withCapacity(VALID_CAPACITY_IPHONE).build();
        expectedCommand = new EditPhoneCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


        // cost
        userInput = targetIndex.getOneBased() + COST_DESC_IPHONE;
        descriptor = new EditPhoneDescriptorBuilder().withCost(VALID_COST_IPHONE).build();
        expectedCommand = new EditPhoneCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // colour
        userInput = targetIndex.getOneBased() + COLOUR_DESC_IPHONE;
        descriptor = new EditPhoneDescriptorBuilder().withColour(VALID_COLOUR_IPHONE).build();
        expectedCommand = new EditPhoneCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_NEW;
        descriptor = new EditPhoneDescriptorBuilder().withTags(VALID_TAG_NEW).build();
        expectedCommand = new EditPhoneCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PHONE;
        String userInput = targetIndex.getOneBased() + IDENTITY_NUM_DESC_IPHONE + IDENTITY_NUM_DESC_SAMSUNG
                + SERIAL_NUM_DESC_IPHONE + SERIAL_NUM_DESC_SAMSUNG + PHONE_NAME_DESC_IPHONE + PHONE_NAME_DESC_SAMSUNG
                + BRAND_DESC_IPHONE + BRAND_DESC_SAMSUNG + CAPACITY_DESC_IPHONE + CAPACITY_DESC_SAMSUNG
                + COLOUR_DESC_IPHONE + COLOUR_DESC_SAMSUNG
                + COST_DESC_IPHONE + COST_DESC_SAMSUNG + TAG_DESC_NEW + TAG_DESC_NEW + TAG_DESC_BESTSELLER;

        EditPhoneDescriptor descriptor = new EditPhoneDescriptorBuilder()
                .withSerialNumber(VALID_SERIAL_NUMBER_SAMSUNG)
                .withIdentityNumber(VALID_IDENTITY_NUMBER_SAMSUNG)
                .withBrand(VALID_BRAND_SAMSUNG)
                .withPhoneName(VALID_PHONE_NAME_SAMSUNG)
                .withColour(VALID_COLOUR_SAMSUNG)
                .withCost(VALID_COST_SAMSUNG)
                .withCapacity(VALID_CAPACITY_SAMSUNG)
                .withTags(VALID_TAG_NEW, VALID_TAG_BESTSELLER)
                .build();
        EditPhoneCommand expectedCommand = new EditPhoneCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PHONE;
        String userInput = targetIndex.getOneBased() + INVALID_BRAND_DESC + BRAND_DESC_IPHONE;
        EditPhoneDescriptor descriptor = new EditPhoneDescriptorBuilder().withBrand(VALID_BRAND_IPHONE).build();
        EditPhoneCommand expectedCommand = new EditPhoneCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + SERIAL_NUM_DESC_IPHONE + INVALID_BRAND_DESC + BRAND_DESC_IPHONE;
        descriptor = new EditPhoneDescriptorBuilder().withSerialNumber(VALID_SERIAL_NUMBER_IPHONE)
                .withBrand(VALID_BRAND_IPHONE).build();
        expectedCommand = new EditPhoneCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PHONE;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPhoneDescriptor descriptor = new EditPhoneDescriptorBuilder().withTags().build();
        EditPhoneCommand expectedCommand = new EditPhoneCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

