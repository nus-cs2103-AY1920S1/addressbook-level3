package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.commands.CommandTestUtil.FOLDER_DESC_CS2101;
import static seedu.mark.logic.commands.CommandTestUtil.FOLDER_DESC_CS2103T;
import static seedu.mark.logic.commands.CommandTestUtil.INVALID_FOLDER_DESC;
import static seedu.mark.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.mark.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.mark.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.mark.logic.commands.CommandTestUtil.INVALID_URL_DESC;
import static seedu.mark.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.mark.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.mark.logic.commands.CommandTestUtil.URL_DESC_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.URL_DESC_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_FOLDER_CS2101;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_FOLDER_CS2103T;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;
import static seedu.mark.testutil.TypicalIndexes.INDEX_SECOND_BOOKMARK;
import static seedu.mark.testutil.TypicalIndexes.INDEX_THIRD_BOOKMARK;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.EditCommand;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Remark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.tag.Tag;
import seedu.mark.testutil.EditBookmarkDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

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
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_URL_DESC, Url.MESSAGE_CONSTRAINTS); // invalid url
        assertParseFailure(parser, "1" + INVALID_REMARK_DESC, Remark.MESSAGE_CONSTRAINTS); // invalid remark
        assertParseFailure(parser, "1" + INVALID_FOLDER_DESC, Folder.MESSAGE_CONSTRAINTS); // invalid remark
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid name followed by valid url
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + URL_DESC_AMY, Name.MESSAGE_CONSTRAINTS);

        // valid name followed by invalid name. The test case for invalid name followed by valid name
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + NAME_DESC_BOB + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Bookmark} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_URL_DESC + VALID_REMARK_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_BOOKMARK;
        String userInput = targetIndex.getOneBased() + TAG_DESC_HUSBAND
                + URL_DESC_AMY + REMARK_DESC_AMY + NAME_DESC_AMY + FOLDER_DESC_CS2103T + TAG_DESC_FRIEND;

        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder().withName(VALID_NAME_AMY)
                .withUrl(VALID_URL_AMY).withRemark(VALID_REMARK_AMY).withFolder(VALID_FOLDER_CS2103T)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_BOOKMARK;
        String userInput = targetIndex.getOneBased() + URL_DESC_AMY + TAG_DESC_FRIEND;

        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder()
                .withUrl(VALID_URL_AMY).withTags(VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_BOOKMARK;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder()
                .withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // url
        userInput = targetIndex.getOneBased() + URL_DESC_AMY;
        descriptor = new EditBookmarkDescriptorBuilder().withUrl(VALID_URL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remark
        userInput = targetIndex.getOneBased() + REMARK_DESC_AMY;
        descriptor = new EditBookmarkDescriptorBuilder().withRemark(VALID_REMARK_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // folder
        userInput = targetIndex.getOneBased() + FOLDER_DESC_CS2103T;
        descriptor = new EditBookmarkDescriptorBuilder().withFolder(VALID_FOLDER_CS2103T).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditBookmarkDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_BOOKMARK;
        String userInput = targetIndex.getOneBased() + REMARK_DESC_AMY + URL_DESC_AMY
                + TAG_DESC_FRIEND + REMARK_DESC_AMY + URL_DESC_AMY + TAG_DESC_FRIEND
                + REMARK_DESC_BOB + URL_DESC_BOB + TAG_DESC_HUSBAND + FOLDER_DESC_CS2101;

        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder()
                .withUrl(VALID_URL_BOB).withRemark(VALID_REMARK_BOB).withFolder(VALID_FOLDER_CS2101)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_BOOKMARK;
        String userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + NAME_DESC_BOB;
        EditCommand.EditBookmarkDescriptor descriptor =
                new EditBookmarkDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + URL_DESC_BOB + INVALID_NAME_DESC + REMARK_DESC_BOB
                + NAME_DESC_BOB;
        descriptor = new EditBookmarkDescriptorBuilder().withName(VALID_NAME_BOB)
                .withUrl(VALID_URL_BOB).withRemark(VALID_REMARK_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_BOOKMARK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder()
                .withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
