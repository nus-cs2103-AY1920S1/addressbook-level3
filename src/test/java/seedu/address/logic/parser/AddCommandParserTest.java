package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEANING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEANING_DESC_ABRA;
import static seedu.address.logic.commands.CommandTestUtil.MEANING_DESC_BUTTERFREE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BUG;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FLYING;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PSYCHIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BUG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FLYING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PSYCHIC;
import static seedu.address.logic.commands.CommandTestUtil.WORD_DESC_ABRA;
import static seedu.address.logic.commands.CommandTestUtil.WORD_DESC_BUTTERFREE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.BUTTERFREE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.app.AddCommand;
import seedu.address.model.card.Card;
import seedu.address.model.card.Meaning;
import seedu.address.model.card.Word;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CardBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Card expectedPerson = new CardBuilder(BUTTERFREE).withTags(VALID_TAG_PSYCHIC).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + WORD_DESC_BUTTERFREE + MEANING_DESC_BUTTERFREE
                + TAG_DESC_PSYCHIC, new AddCommand(expectedPerson));

        // multiple words - last word accepted
        assertParseSuccess(parser, WORD_DESC_ABRA + WORD_DESC_BUTTERFREE + MEANING_DESC_BUTTERFREE
                + TAG_DESC_PSYCHIC, new AddCommand(expectedPerson));

        // multiple meanings - last meaning accepted
        assertParseSuccess(parser, WORD_DESC_BUTTERFREE + MEANING_DESC_ABRA + MEANING_DESC_BUTTERFREE
                + TAG_DESC_PSYCHIC, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Card expectedPersonMultipleTags = new CardBuilder(BUTTERFREE).withTags(VALID_TAG_BUG, VALID_TAG_FLYING)
                .build();
        assertParseSuccess(parser, WORD_DESC_BUTTERFREE + MEANING_DESC_BUTTERFREE
                + TAG_DESC_BUG + TAG_DESC_FLYING, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Card expectedPerson = new CardBuilder(ABRA).withTags().build();
        assertParseSuccess(parser, WORD_DESC_ABRA + MEANING_DESC_ABRA, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing word prefix
        assertParseFailure(parser, MEANING_DESC_ABRA + TAG_DESC_PSYCHIC, expectedMessage);

        // missing meaning prefix
        assertParseFailure(parser, WORD_DESC_ABRA + TAG_DESC_PSYCHIC, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TAG_BUG, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid word
        assertParseFailure(parser, INVALID_WORD_DESC + MEANING_DESC_BUTTERFREE
                + TAG_DESC_BUG + TAG_DESC_FLYING, Word.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, WORD_DESC_BUTTERFREE + INVALID_MEANING_DESC
                + TAG_DESC_BUG + TAG_DESC_FLYING, Meaning.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, WORD_DESC_ABRA + MEANING_DESC_ABRA
                + INVALID_TAG_DESC + VALID_TAG_PSYCHIC, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_WORD_DESC + INVALID_MEANING_DESC, Word.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + WORD_DESC_BUTTERFREE + MEANING_DESC_BUTTERFREE
                + TAG_DESC_BUG + TAG_DESC_FLYING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
