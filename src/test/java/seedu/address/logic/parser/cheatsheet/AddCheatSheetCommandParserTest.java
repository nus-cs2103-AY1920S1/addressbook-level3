package seedu.address.logic.parser.cheatsheet;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHEATSHEET;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_CS1;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_CS2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHEATSHEET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_GEM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_MATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCheatSheets.CS6;
import static seedu.address.testutil.TypicalCheatSheets.CS7;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cheatsheet.AddCheatSheetCommand;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.cheatsheet.Title;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CheatSheetBuilder;

class AddCheatSheetCommandParserTest {
    private AddCheatSheetCommandParser parser = new AddCheatSheetCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        CheatSheet expectedCheatSheet = new CheatSheetBuilder(CS6)
                .withTitle(VALID_TITLE_MATH)
                .withTags(VALID_TAG_CHEATSHEET).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_CS1
                + TAG_DESC_CHEATSHEET, new AddCheatSheetCommand(expectedCheatSheet));

        // multiple title - last title accepted
        assertParseSuccess(parser, TITLE_DESC_CS2 + TITLE_DESC_CS1 + TAG_DESC_CHEATSHEET,
                new AddCheatSheetCommand(expectedCheatSheet));

        // multiple tags - all accepted
        CheatSheet expectedCheatSheetMultipleTags = new CheatSheetBuilder(CS7)
                .withTitle(VALID_TITLE_GEM)
                .withTags(VALID_TAG_CHEATSHEET, VALID_TAG_IMPORTANT).build();

        assertParseSuccess(parser, TITLE_DESC_CS2 + TAG_DESC_CHEATSHEET + TAG_DESC_IMPORTANT,
                new AddCheatSheetCommand(expectedCheatSheetMultipleTags));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage1 = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCheatSheetCommand.MESSAGE_USAGE);
        String expectedMessage2 = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCheatSheetCommand.MESSAGE_TAG_RESTRICTION);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_MATH + TAG_DESC_CHEATSHEET, expectedMessage1);

        // missing tag prefix
        assertParseFailure(parser, TITLE_DESC_CS1, expectedMessage2);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_MATH + VALID_TAG_CHEATSHEET, expectedMessage1);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + TAG_DESC_CHEATSHEET, Title.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TITLE_DESC_CS1 + INVALID_TAG_DESC + TAG_DESC_CHEATSHEET,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + INVALID_TAG_DESC,
                Title.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_CS1 + TAG_DESC_CHEATSHEET,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCheatSheetCommand.MESSAGE_USAGE));
    }
}
