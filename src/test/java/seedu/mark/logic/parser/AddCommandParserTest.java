package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.mark.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.mark.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.mark.logic.commands.CommandTestUtil.INVALID_URL_DESC;
import static seedu.mark.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.mark.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.mark.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.mark.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.mark.logic.commands.CommandTestUtil.URL_DESC_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.URL_DESC_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.mark.testutil.TypicalBookmarks.AMY;
import static seedu.mark.testutil.TypicalBookmarks.BOB;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.AddCommand;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Remark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.tag.Tag;
import seedu.mark.testutil.BookmarkBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Bookmark expectedBookmark = new BookmarkBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + URL_DESC_BOB
                + REMARK_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBookmark));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + URL_DESC_BOB
                + REMARK_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBookmark));

        // multiple urls - last url accepted
        assertParseSuccess(parser, NAME_DESC_BOB + URL_DESC_AMY + URL_DESC_BOB
                + REMARK_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBookmark));

        // multiple remarks - last remark accepted
        assertParseSuccess(parser, NAME_DESC_BOB + URL_DESC_BOB + REMARK_DESC_AMY
                + REMARK_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBookmark));

        // multiple tags - all accepted
        Bookmark expectedBookmarkMultipleTags = new BookmarkBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + URL_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedBookmarkMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no remark
        Bookmark expectedBookmark = new BookmarkBuilder(AMY).withRemark(Remark.DEFAULT_VALUE).build();
        assertParseSuccess(parser, NAME_DESC_AMY + URL_DESC_AMY + TAG_DESC_FRIEND, new AddCommand(expectedBookmark));

        // zero tags
        expectedBookmark = new BookmarkBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + URL_DESC_AMY + REMARK_DESC_AMY,
                new AddCommand(expectedBookmark));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + URL_DESC_BOB + REMARK_DESC_BOB,
                expectedMessage);

        // missing url prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_URL_BOB + REMARK_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_URL_BOB + VALID_REMARK_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + URL_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid url
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_URL_DESC + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Url.MESSAGE_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, NAME_DESC_BOB + URL_DESC_BOB + INVALID_REMARK_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Remark.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + URL_DESC_BOB + REMARK_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_URL_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + URL_DESC_BOB
                + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
