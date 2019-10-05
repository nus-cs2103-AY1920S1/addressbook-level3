package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.weme.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.weme.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.weme.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.weme.testutil.TypicalMemes.AMY;
import static seedu.weme.testutil.TypicalMemes.BOB;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.AddCommand;
import seedu.weme.model.meme.Address;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.Name;
import seedu.weme.model.meme.Phone;
import seedu.weme.model.tag.Tag;
import seedu.weme.testutil.MemeBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Meme expectedMeme = new MemeBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedMeme));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedMeme));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedMeme));

        // multiple addresses - last weme accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedMeme));

        // multiple tags - all accepted
        Meme expectedMemeMultipleTags = new MemeBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedMemeMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Meme expectedMeme = new MemeBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedMeme));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing weme prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid weme
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
