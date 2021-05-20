package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_SHIRTSALES;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_FUNDRAISING;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_SHIRTSALES;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_FUNDRAISING;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_SHIRTSALES;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_SHIRTSALES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_FUNDRAISING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_SHIRTSALES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_FUNDRAISING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_SHIRTSALES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditIncomeCommand;
import seedu.address.logic.commands.EditIncomeCommand.EditIncomeDescriptor;
import seedu.address.model.claim.Amount;
import seedu.address.model.claim.Description;
import seedu.address.model.commonvariables.Date;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.commonvariables.Phone;
import seedu.address.testutil.EditIncomeDescriptorBuilder;


public class EditIncomeCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditIncomeCommand.MESSAGE_USAGE);

    private EditIncomeCommandParser parser = new EditIncomeCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditIncomeCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid desc
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS);
        //invalid amount
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS);
        //invalid date
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS);
        //invalid name
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid name followed by valid phone
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + PHONE_DESC_AMY, Name.MESSAGE_CONSTRAINTS);

        // valid name followed by invalid phone
        assertParseFailure(parser, "1" + NAME_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC + INVALID_DATE_DESC
                + VALID_PHONE_AMY, Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_SHIRTSALES + AMOUNT_DESC_SHIRTSALES
                + DATE_DESC_FUNDRAISING + NAME_DESC_AMY + PHONE_DESC_BOB;

        EditIncomeDescriptor descriptor = new EditIncomeDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_SHIRTSALES).withAmount(VALID_AMOUNT_SHIRTSALES)
                .withDate(VALID_DATE_FUNDRAISING).withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).build();
        EditIncomeCommand expectedCommand = new EditIncomeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + DATE_DESC_FUNDRAISING;

        EditIncomeDescriptor descriptor = new EditIncomeDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withDate(VALID_DATE_FUNDRAISING).build();
        EditIncomeCommand expectedCommand = new EditIncomeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {

        // description
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_FUNDRAISING;
        EditIncomeDescriptor descriptor = new EditIncomeDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_FUNDRAISING).build();
        EditIncomeCommand expectedCommand = new EditIncomeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_SHIRTSALES;
        descriptor = new EditIncomeDescriptorBuilder().withAmount(VALID_AMOUNT_SHIRTSALES).build();
        expectedCommand = new EditIncomeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_FUNDRAISING;
        descriptor = new EditIncomeDescriptorBuilder().withDate(VALID_DATE_FUNDRAISING).build();
        expectedCommand = new EditIncomeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // name
        userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        descriptor = new EditIncomeDescriptorBuilder().withName(VALID_NAME_AMY).build();
        expectedCommand = new EditIncomeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditIncomeDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditIncomeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditIncomeDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditIncomeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + DATE_DESC_FUNDRAISING
                + DESCRIPTION_DESC_SHIRTSALES + PHONE_DESC_AMY + DATE_DESC_FUNDRAISING + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + DATE_DESC_SHIRTSALES
                + TAG_DESC_HUSBAND;

        EditIncomeDescriptor descriptor = new EditIncomeDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_SHIRTSALES).withPhone(VALID_PHONE_BOB)
                .withDate(VALID_DATE_SHIRTSALES).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditIncomeCommand expectedCommand = new EditIncomeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditIncomeDescriptor descriptor = new EditIncomeDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditIncomeCommand expectedCommand = new EditIncomeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_SHIRTSALES + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        descriptor = new EditIncomeDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withDescription(VALID_DESCRIPTION_SHIRTSALES).build();
        expectedCommand = new EditIncomeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditIncomeDescriptor descriptor = new EditIncomeDescriptorBuilder().withTags().build();
        EditIncomeCommand expectedCommand = new EditIncomeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
