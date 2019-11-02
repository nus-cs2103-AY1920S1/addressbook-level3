package seedu.address.logic.parser.cheatsheet;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHEATSHEET;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_CS1;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_CS2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHEATSHEET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_GEM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_MATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CHEATSHEET;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CHEATSHEET;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.cheatsheet.EditCheatSheetCommand;
import seedu.address.logic.commands.cheatsheet.EditCheatSheetCommand.EditCheatSheetDescriptor;
import seedu.address.model.cheatsheet.Title;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditCheatSheetDescriptorBuilder;

public class EditCheatSheetCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCheatSheetCommand.MESSAGE_USAGE);

    private EditCheatSheetCommandParser parser = new EditCheatSheetCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_MATH, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCheatSheetCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_DESC_CS1, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_DESC_CS1, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC, Title.MESSAGE_CONSTRAINTS); // invalid title
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // valid title followed by invalid title. The test case for invalid title followed by valid title
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TITLE_DESC_CS1 + INVALID_TITLE_DESC, Title.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + INVALID_TAG_DESC, Title.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_CHEATSHEET;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_CS1 + TAG_DESC_CHEATSHEET;

        EditCheatSheetDescriptor descriptor = new EditCheatSheetDescriptorBuilder().withTitle(VALID_TITLE_MATH)
                .withTags(VALID_TAG_CHEATSHEET).build();
        EditCheatSheetCommand expectedCommand = new EditCheatSheetCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_CHEATSHEET;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_CS2;

        EditCheatSheetDescriptor descriptor = new EditCheatSheetDescriptorBuilder().withTitle(VALID_TITLE_GEM).build();
        EditCheatSheetCommand expectedCommand = new EditCheatSheetCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_CHEATSHEET;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_CS1 + TAG_DESC_CHEATSHEET + TITLE_DESC_CS1
                + TITLE_DESC_CS2 + TAG_DESC_IMPORTANT + TAG_DESC_CHEATSHEET + TITLE_DESC_CS1 + TAG_DESC_CHEATSHEET;

        EditCheatSheetDescriptor descriptor = new EditCheatSheetDescriptorBuilder().withTitle(VALID_TITLE_MATH)
                 .withTags(VALID_TAG_CHEATSHEET, VALID_TAG_IMPORTANT)
                .build();
        EditCheatSheetCommand expectedCommand = new EditCheatSheetCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        Index targetIndex = INDEX_FIRST_CHEATSHEET;
        String userInput = targetIndex.getOneBased() + INVALID_TITLE_DESC + TITLE_DESC_CS1;

        EditCheatSheetDescriptor descriptor = new EditCheatSheetDescriptorBuilder().withTitle(VALID_TITLE_MATH).build();
        EditCheatSheetCommand expectedCommand = new EditCheatSheetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
