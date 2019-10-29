package seedu.address.logic.parser.editcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_INDEX_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CUSTOMER_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_INDEX_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_IPHONEXR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BESTSELLER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NEW;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_REGULAR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_RICH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_IPHONEXR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BESTSELLER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_REGULAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ORDER;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.editcommand.EditOrderCommand;
import seedu.address.logic.commands.editcommand.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.order.Price;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditOrderDescriptorBuilder;

public class EditOrderCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE);

    private EditOrderCommandParser parser = new EditOrderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PRICE_IPHONE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditOrderCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + CUSTOMER_INDEX_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PRICE_DESC_IPHONE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid phone index
        assertParseFailure(parser, "1" + INVALID_PHONE_INDEX_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE));

        // invalid customer index
        assertParseFailure(parser, "1" + INVALID_CUSTOMER_INDEX_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + INVALID_PRICE_DESC,
                Price.MESSAGE_CONSTRAINTS); // invalid price
        assertParseFailure(parser, "1" + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone index followed by valid price
        assertParseFailure(parser, "1" + INVALID_PHONE_INDEX_DESC + PRICE_DESC_IPHONE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE));

        // valid price followed by invalid customer index
        assertParseFailure(parser, "1" + PRICE_DESC_IPHONE + INVALID_CUSTOMER_INDEX_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE));

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Order} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_REGULAR + TAG_DESC_RICH + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_REGULAR + TAG_EMPTY + TAG_DESC_RICH,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_REGULAR + TAG_DESC_RICH,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_CUSTOMER_INDEX_DESC + INVALID_PRICE_DESC
                + VALID_PHONE_INDEX, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ORDER;
        String userInput = targetIndex.getOneBased() + CUSTOMER_INDEX_DESC + PHONE_INDEX_DESC
                + PRICE_DESC_IPHONE + TAG_DESC_NEW;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPrice(VALID_PRICE_IPHONE)
                .withTags(VALID_TAG_NEW).build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, Optional.of(VALID_CUSTOMER_INDEX),
                Optional.of(VALID_PHONE_INDEX), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + PRICE_DESC_IPHONE + TAG_DESC_NEW;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPrice(VALID_PRICE_IPHONE)
                .withTags(VALID_TAG_NEW)
                .build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, Optional.empty(),
                Optional.empty(), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // customer index
        Index targetIndex = INDEX_THIRD_ORDER;
        String userInput = targetIndex.getOneBased() + CUSTOMER_INDEX_DESC;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, Optional.of(VALID_CUSTOMER_INDEX),
                Optional.empty(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


        // phone index
        userInput = targetIndex.getOneBased() + PHONE_INDEX_DESC;
        descriptor = new EditOrderDescriptorBuilder().build();
        expectedCommand = new EditOrderCommand(targetIndex, Optional.empty(),
                Optional.of(VALID_PHONE_INDEX), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // price
        userInput = targetIndex.getOneBased() + PRICE_DESC_IPHONE;
        descriptor = new EditOrderDescriptorBuilder().withPrice(VALID_PRICE_IPHONE).build();
        expectedCommand = new EditOrderCommand(targetIndex, Optional.empty(), Optional.empty(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_REGULAR;
        descriptor = new EditOrderDescriptorBuilder().withTags(VALID_TAG_REGULAR).build();
        expectedCommand = new EditOrderCommand(targetIndex, Optional.empty(), Optional.empty(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + TAG_DESC_NEW + PRICE_DESC_IPHONE + PRICE_DESC_IPHONEXR
                + TAG_DESC_BESTSELLER + CUSTOMER_INDEX_DESC_2
                + CUSTOMER_INDEX_DESC + PHONE_INDEX_DESC_2 + PHONE_INDEX_DESC + TAG_DESC_BESTSELLER;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPrice(VALID_PRICE_IPHONEXR).withTags(VALID_TAG_NEW, VALID_TAG_BESTSELLER)
                .build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex,
                Optional.of(VALID_CUSTOMER_INDEX), Optional.of(VALID_PHONE_INDEX), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + INVALID_PRICE_DESC + PRICE_DESC_IPHONE;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPrice(VALID_PRICE_IPHONE).build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, Optional.empty(),
                Optional.empty(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_PRICE_DESC + PRICE_DESC_IPHONE
                + TAG_DESC_NEW;
        descriptor = new EditOrderDescriptorBuilder()
                .withPrice(VALID_PRICE_IPHONE)
                .withTags(VALID_TAG_NEW).build();
        expectedCommand = new EditOrderCommand(targetIndex, Optional.empty(),
                Optional.empty(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_ORDER;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withTags().build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, Optional.empty(),
                Optional.empty(), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

