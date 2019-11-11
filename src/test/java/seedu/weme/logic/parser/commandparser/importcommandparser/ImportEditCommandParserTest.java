package seedu.weme.logic.parser.commandparser.importcommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.DESCRIPTION_DESC_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.FILEPATH_DESC_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.TAG_DESC_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.TAG_DESC_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_FILEPATH_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_JOKER;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.weme.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.weme.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.importcommand.ImportEditCommand;
import seedu.weme.logic.commands.memecommand.MemeEditCommand.EditMemeDescriptor;
import seedu.weme.model.tag.Tag;
import seedu.weme.testutil.EditMemeDescriptorBuilder;

public class ImportEditCommandParserTest extends ApplicationTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportEditCommand.MESSAGE_USAGE);

    private ImportEditCommandParser parser = new ImportEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_FILEPATH_CHARMANDER, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", ImportEditCommand.MESSAGE_NOT_EDITED);

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

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + TAG_DESC_JOKER
                + DESCRIPTION_DESC_CHARMANDER + TAG_DESC_CHARMANDER;

        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_CHARMANDER)
                .withTags(VALID_TAG_CHARMANDER, VALID_TAG_JOKER).build();
        ImportEditCommand expectedCommand = new ImportEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_JOKER;

        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_JOKER).build();
        ImportEditCommand expectedCommand = new ImportEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // description
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_CHARMANDER;
        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_CHARMANDER).build();
        ImportEditCommand expectedCommand = new ImportEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_CHARMANDER;
        descriptor = new EditMemeDescriptorBuilder().withTags(VALID_TAG_CHARMANDER).build();
        expectedCommand = new ImportEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_CHARMANDER
                + TAG_DESC_CHARMANDER + DESCRIPTION_DESC_CHARMANDER + TAG_DESC_CHARMANDER
                + DESCRIPTION_DESC_JOKER + TAG_DESC_JOKER;

        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_JOKER).withTags(VALID_TAG_JOKER, VALID_TAG_CHARMANDER)
                .build();
        ImportEditCommand expectedCommand = new ImportEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withTags().build();
        ImportEditCommand expectedCommand = new ImportEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
