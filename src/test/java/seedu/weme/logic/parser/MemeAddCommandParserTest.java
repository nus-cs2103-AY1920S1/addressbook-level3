package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.DESCRIPTION_DESC_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.FILEPATH_DESC_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.FILEPATH_DESC_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_FILEPATH_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.weme.logic.commands.CommandTestUtil.TAG_DESC_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.TAG_DESC_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_FILEPATH_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_JOKER;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.weme.testutil.TypicalMemes.CHARMANDER;
import static seedu.weme.testutil.TypicalMemes.JOKER;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.MemeAddCommand;
import seedu.weme.model.meme.ImagePath;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;
import seedu.weme.testutil.MemeBuilder;

public class MemeAddCommandParserTest {
    private MemeAddCommandParser parser = new MemeAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Meme expectedMeme = new MemeBuilder(JOKER).withDescription(VALID_DESCRIPTION_JOKER)
                .withTags(VALID_TAG_JOKER).build();

        // multiple paths - last paths accepted
        assertParseSuccess(parser, FILEPATH_DESC_CHARMANDER + FILEPATH_DESC_JOKER
                + DESCRIPTION_DESC_JOKER + TAG_DESC_JOKER, new MemeAddCommand(expectedMeme));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, FILEPATH_DESC_JOKER + DESCRIPTION_DESC_CHARMANDER
                + DESCRIPTION_DESC_JOKER + TAG_DESC_JOKER, new MemeAddCommand(expectedMeme));

        // multiple tags - all accepted
        Meme expectedMemeMultipleTags = new MemeBuilder(JOKER).withTags(VALID_TAG_JOKER, VALID_TAG_CHARMANDER)
                .build();
        assertParseSuccess(parser, FILEPATH_DESC_JOKER + DESCRIPTION_DESC_JOKER
                + TAG_DESC_JOKER + TAG_DESC_CHARMANDER, new MemeAddCommand(expectedMemeMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Meme expectedMeme = new MemeBuilder(CHARMANDER).withTags().build();
        assertParseSuccess(parser, FILEPATH_DESC_CHARMANDER + DESCRIPTION_DESC_CHARMANDER,
                new MemeAddCommand(expectedMeme));
        // Empty Description
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_FILEPATH_JOKER + DESCRIPTION_DESC_JOKER,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_FILEPATH_JOKER + VALID_DESCRIPTION_JOKER,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid weme
        assertParseFailure(parser, INVALID_FILEPATH_DESC + DESCRIPTION_DESC_JOKER
                + TAG_DESC_JOKER + TAG_DESC_CHARMANDER, ImagePath.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, FILEPATH_DESC_JOKER + DESCRIPTION_DESC_JOKER
                + INVALID_TAG_DESC + VALID_TAG_JOKER, Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + FILEPATH_DESC_JOKER
                + DESCRIPTION_DESC_JOKER + TAG_DESC_JOKER + TAG_DESC_CHARMANDER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeAddCommand.MESSAGE_USAGE));
    }
}
