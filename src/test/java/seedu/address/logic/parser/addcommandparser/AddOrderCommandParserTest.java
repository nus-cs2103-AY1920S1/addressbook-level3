package seedu.address.logic.parser.addcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_INDEX_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CUSTOMER_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_INDEX_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_IPHONEXR;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_VIPORDER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NEW;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_REGULAR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_USED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_S_CUSTOMER_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_S_PHONE_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.addcommand.AddOrderCommand;
import seedu.address.model.tag.Tag;

public class AddOrderCommandParserTest {

    private AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Used"));

        // whitespace only preamble
        assertParseSuccess(parser, CUSTOMER_INDEX_DESC + PHONE_INDEX_DESC + PRICE_DESC_IPHONEXR + TAG_DESC_USED,
                new AddOrderCommand(VALID_CUSTOMER_INDEX, VALID_PHONE_INDEX, PRICE_DESC_VIPORDER, tags));

        // multiple customer index - last customer index accepted
        assertParseSuccess(parser, CUSTOMER_INDEX_DESC_2 + PHONE_INDEX_DESC
                        + CUSTOMER_INDEX_DESC + PRICE_DESC_IPHONEXR + TAG_DESC_USED,
                new AddOrderCommand(VALID_CUSTOMER_INDEX, VALID_PHONE_INDEX, PRICE_DESC_VIPORDER, tags));

        // multiple phone index - last phone index accepted
        assertParseSuccess(parser, PHONE_INDEX_DESC_2 + CUSTOMER_INDEX_DESC
                        + PHONE_INDEX_DESC + PRICE_DESC_IPHONEXR + TAG_DESC_USED,
                new AddOrderCommand(VALID_CUSTOMER_INDEX, VALID_PHONE_INDEX, PRICE_DESC_VIPORDER, tags));


        // multiple price - last price accepted
        assertParseSuccess(parser, CUSTOMER_INDEX_DESC + PHONE_INDEX_DESC
                        + PRICE_DESC_IPHONE + PRICE_DESC_IPHONEXR + TAG_DESC_USED,
                new AddOrderCommand(VALID_CUSTOMER_INDEX, VALID_PHONE_INDEX, PRICE_DESC_VIPORDER, tags));

        // multiple tags - all accepted
        tags.add(new Tag("New"));
        tags.add(new Tag("Regular"));
        assertParseSuccess(parser, CUSTOMER_INDEX_DESC + PHONE_INDEX_DESC + PRICE_DESC_IPHONEXR
                        + TAG_DESC_USED + TAG_DESC_NEW + TAG_DESC_REGULAR,
                new AddOrderCommand(VALID_CUSTOMER_INDEX, VALID_PHONE_INDEX, PRICE_DESC_VIPORDER, tags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {

        Set<Tag> tags = new HashSet<>();

        // zero tags
        assertParseSuccess(parser, CUSTOMER_INDEX_DESC + PHONE_INDEX_DESC + PRICE_DESC_IPHONEXR,
                new AddOrderCommand(VALID_CUSTOMER_INDEX, VALID_PHONE_INDEX, PRICE_DESC_VIPORDER, tags));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE);

        // missing customer prefix
        assertParseFailure(parser, VALID_S_CUSTOMER_INDEX + PHONE_INDEX_DESC + PRICE_DESC_IPHONEXR + TAG_DESC_USED,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, CUSTOMER_INDEX_DESC + VALID_S_PHONE_INDEX + PRICE_DESC_IPHONEXR + TAG_DESC_USED,
                expectedMessage);

        // missing price prefix
        assertParseFailure(parser, CUSTOMER_INDEX_DESC + PHONE_INDEX_DESC + VALID_ORDER_PRICE + TAG_DESC_USED,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_S_CUSTOMER_INDEX + VALID_S_PHONE_INDEX + VALID_ORDER_PRICE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid customer index
        assertParseFailure(parser, INVALID_CUSTOMER_INDEX_DESC + PHONE_INDEX_DESC + PRICE_DESC_VIPORDER + TAG_DESC_USED,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        // invalid phone index
        assertParseFailure(parser, CUSTOMER_INDEX_DESC + INVALID_PHONE_INDEX_DESC + PRICE_DESC_VIPORDER + TAG_DESC_USED,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        // invalid price
        assertParseFailure(parser, CUSTOMER_INDEX_DESC + INVALID_PHONE_INDEX_DESC + INVALID_PRICE_DESC + TAG_DESC_USED,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        // invalid tags
        assertParseFailure(parser, CUSTOMER_INDEX_DESC + INVALID_PHONE_INDEX_DESC + PRICE_DESC_VIPORDER
                        + INVALID_TAG_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));


        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_CUSTOMER_INDEX_DESC + INVALID_PHONE_INDEX_DESC + PRICE_DESC_VIPORDER
                        + INVALID_TAG_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + CUSTOMER_INDEX_DESC + PHONE_INDEX_DESC + PRICE_DESC_IPHONEXR
                        + TAG_DESC_USED, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
    }

}
