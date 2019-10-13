package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.DESCRIPTION_DESC_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.FILEPATH_DESC_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_FILEPATH_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.TAG_DESC_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.TAG_DESC_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_FILEPATH_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_JOKER;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST_MEME;
import static seedu.weme.testutil.TypicalIndexes.INDEX_SECOND_MEME;
import static seedu.weme.testutil.TypicalIndexes.INDEX_THIRD_MEME;

import org.junit.jupiter.api.Test;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.MemeEditCommand;
import seedu.weme.logic.commands.MemeEditCommand.EditMemeDescriptor;
import seedu.weme.model.meme.ImagePath;
import seedu.weme.model.tag.Tag;
import seedu.weme.testutil.EditMemeDescriptorBuilder;

public class MemeEditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeEditCommand.MESSAGE_USAGE);

    private MemeEditCommandParser parser = new MemeEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_FILEPATH_CHARMANDER, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MemeEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + FILEPATH_DESC_CHARMANDER, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + FILEPATH_DESC_CHARMANDER, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Meme} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_CHARMANDER + TAG_DESC_JOKER + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_CHARMANDER + TAG_EMPTY + TAG_DESC_JOKER, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_CHARMANDER + TAG_DESC_JOKER, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_FILEPATH_DESC + VALID_DESCRIPTION_CHARMANDER,
                ImagePath.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_MEME;
        String userInput = targetIndex.getOneBased() + TAG_DESC_JOKER + FILEPATH_DESC_CHARMANDER
                + DESCRIPTION_DESC_CHARMANDER + TAG_DESC_CHARMANDER;

        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withFilePath(VALID_FILEPATH_CHARMANDER)
                .withDescription(VALID_DESCRIPTION_CHARMANDER)
                .withTags(VALID_TAG_CHARMANDER, VALID_TAG_JOKER).build();
        MemeEditCommand expectedCommand = new MemeEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_MEME;
        String userInput = targetIndex.getOneBased() + FILEPATH_DESC_CHARMANDER + DESCRIPTION_DESC_JOKER;

        MemeEditCommand.EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder()
                .withFilePath(VALID_FILEPATH_CHARMANDER).withDescription(VALID_DESCRIPTION_JOKER).build();
        MemeEditCommand expectedCommand = new MemeEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // path
        Index targetIndex = INDEX_THIRD_MEME;
        String userInput = targetIndex.getOneBased() + FILEPATH_DESC_CHARMANDER;
        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withFilePath(VALID_FILEPATH_CHARMANDER).build();
        MemeEditCommand expectedCommand = new MemeEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // weme
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_CHARMANDER;
        descriptor = new EditMemeDescriptorBuilder().withDescription(VALID_DESCRIPTION_CHARMANDER).build();
        expectedCommand = new MemeEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_CHARMANDER;
        descriptor = new EditMemeDescriptorBuilder().withTags(VALID_TAG_CHARMANDER).build();
        expectedCommand = new MemeEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_MEME;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_CHARMANDER
                + TAG_DESC_CHARMANDER + DESCRIPTION_DESC_CHARMANDER + TAG_DESC_CHARMANDER
                + DESCRIPTION_DESC_JOKER + TAG_DESC_JOKER;

        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_JOKER).withTags(VALID_TAG_JOKER, VALID_TAG_CHARMANDER)
                .build();
        MemeEditCommand expectedCommand = new MemeEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_MEME;
        String userInput = targetIndex.getOneBased() + INVALID_FILEPATH_DESC + FILEPATH_DESC_CHARMANDER;
        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withFilePath(VALID_FILEPATH_CHARMANDER).build();
        MemeEditCommand expectedCommand = new MemeEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_FILEPATH_DESC + DESCRIPTION_DESC_JOKER
                + FILEPATH_DESC_CHARMANDER;
        descriptor = new EditMemeDescriptorBuilder().withDescription(VALID_DESCRIPTION_JOKER)
                .withFilePath(VALID_FILEPATH_CHARMANDER).build();
        expectedCommand = new MemeEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_MEME;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withTags().build();
        MemeEditCommand expectedCommand = new MemeEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
