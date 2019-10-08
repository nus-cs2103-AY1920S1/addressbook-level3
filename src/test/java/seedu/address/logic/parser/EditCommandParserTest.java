package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.card.Meaning;
import seedu.address.model.card.Word;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditCardDescriptorBuilder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.*;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_WORD_ABRA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + WORD_DESC_ABRA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + WORD_DESC_ABRA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_WORD_DESC, Word.MESSAGE_CONSTRAINTS); // invalid word
        assertParseFailure(parser, "1" + INVALID_MEANING_DESC, Meaning.MESSAGE_CONSTRAINTS); // invalid meaning
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid word followed by valid meaning
        assertParseFailure(parser, "1" + INVALID_WORD_DESC + MEANING_DESC_ABRA, Word.MESSAGE_CONSTRAINTS);

        // valid word followed by invalid meaning.
        assertParseFailure(parser, "1" + WORD_DESC_ABRA + INVALID_MEANING_DESC, Meaning.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_PSYCHIC + TAG_DESC_BUG + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_PSYCHIC + TAG_EMPTY + TAG_DESC_BUG, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_PSYCHIC + TAG_DESC_BUG, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_WORD_DESC + INVALID_MEANING_DESC,
                Word.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + MEANING_DESC_ABRA
                + WORD_DESC_ABRA + TAG_DESC_PSYCHIC;

        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withWord(VALID_WORD_ABRA)
                .withMeaning(VALID_MEANING_ABRA).withTags(VALID_TAG_PSYCHIC).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // word
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + WORD_DESC_ABRA;
        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withWord(VALID_WORD_ABRA).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // meaning
        userInput = targetIndex.getOneBased() + MEANING_DESC_ABRA;
        descriptor = new EditCardDescriptorBuilder().withMeaning(VALID_MEANING_ABRA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_BUG;
        descriptor = new EditCardDescriptorBuilder().withTags(VALID_TAG_BUG).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + MEANING_DESC_ABRA 
                + TAG_DESC_BUG + MEANING_DESC_ABRA + TAG_DESC_BUG
                + MEANING_DESC_BUTTERFREE + TAG_DESC_PSYCHIC;

        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withMeaning(VALID_MEANING_BUTTERFREE)
                .withTags(VALID_TAG_BUG, VALID_TAG_PSYCHIC)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_MEANING_DESC + MEANING_DESC_BUTTERFREE;
        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withMeaning(VALID_MEANING_BUTTERFREE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_MEANING_DESC + WORD_DESC_BUTTERFREE
                + MEANING_DESC_BUTTERFREE;
        descriptor = new EditCardDescriptorBuilder().withMeaning(VALID_MEANING_BUTTERFREE)
                .withWord(VALID_WORD_BUTTERFREE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
