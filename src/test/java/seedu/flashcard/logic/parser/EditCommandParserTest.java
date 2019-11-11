package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.commands.CommandTestUtil.ANSWER_DESC_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.ANSWER_DESC_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.CHOICE_DESC_BLUE;
import static seedu.flashcard.logic.commands.CommandTestUtil.DEFINITION_DESC_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.DEFINITION_DESC_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_DEFINITION_DESC;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.flashcard.logic.commands.CommandTestUtil.QUESTION_DESC_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.QUESTION_DESC_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.TAG_DESC_LONG;
import static seedu.flashcard.logic.commands.CommandTestUtil.TAG_DESC_ROUND;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_ANSWER_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_CHOICE_BLUE;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_DEFINITION_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_QUESTION_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_QUESTION_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_LONG;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_ROUND;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.flashcard.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.flashcard.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static seedu.flashcard.testutil.TypicalIndexes.INDEX_THIRD_FLASHCARD;

import org.junit.jupiter.api.Test;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.commands.EditCommand;
import seedu.flashcard.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.tag.Tag;
import seedu.flashcard.testutil.EditFlashcardDescriptorBuilder;



public class EditCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT + EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_QUESTION_APPLE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + QUESTION_DESC_APPLE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + QUESTION_DESC_APPLE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_QUESTION_DESC, Question.MESSAGE_CONSTRAINTS); // invalid Question
        assertParseFailure(parser, "1" + INVALID_ANSWER_DESC, Answer.MESSAGE_CONSTRAINTS); // invalid Answer
        assertParseFailure(parser, "1" + INVALID_DEFINITION_DESC, Definition.MESSAGE_CONSTRAINTS); // invalid Definition
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag


        // valid Question followed by invalid Question. The test case for invalid question followed by valid question
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + QUESTION_DESC_BANANA + INVALID_QUESTION_DESC, Question.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Flashcard} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_LONG + TAG_DESC_ROUND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_LONG + TAG_EMPTY + TAG_DESC_ROUND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_LONG + TAG_DESC_ROUND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_QUESTION_DESC + INVALID_DEFINITION_DESC + VALID_ANSWER_BANANA,
                Question.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_FLASHCARD;
        String userInput = targetIndex.getOneBased() + QUESTION_DESC_BANANA + ANSWER_DESC_BANANA
                + DEFINITION_DESC_BANANA + TAG_DESC_LONG;

        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withQuestion(VALID_QUESTION_BANANA)
                .withAnswer(VALID_ANSWER_BANANA).withDefinition(VALID_DEFINITION_BANANA).withTag(VALID_TAG_LONG)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + QUESTION_DESC_BANANA + DEFINITION_DESC_BANANA;

        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withQuestion(VALID_QUESTION_BANANA)
                .withDefinition(VALID_DEFINITION_BANANA).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // Question
        Index targetIndex = INDEX_THIRD_FLASHCARD;
        String userInput = targetIndex.getOneBased() + QUESTION_DESC_BANANA;
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withQuestion(VALID_QUESTION_BANANA)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Answer
        userInput = targetIndex.getOneBased() + ANSWER_DESC_BANANA;
        descriptor = new EditFlashcardDescriptorBuilder().withAnswer(VALID_ANSWER_BANANA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Definition
        userInput = targetIndex.getOneBased() + DEFINITION_DESC_BANANA;
        descriptor = new EditFlashcardDescriptorBuilder().withDefinition(VALID_DEFINITION_BANANA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Choice
        userInput = targetIndex.getOneBased() + CHOICE_DESC_BLUE;
        descriptor = new EditFlashcardDescriptorBuilder().withChoices(VALID_CHOICE_BLUE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_ROUND;
        descriptor = new EditFlashcardDescriptorBuilder().withTag(VALID_TAG_ROUND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + QUESTION_DESC_APPLE + ANSWER_DESC_APPLE + DEFINITION_DESC_APPLE
                + TAG_DESC_ROUND + QUESTION_DESC_BANANA + ANSWER_DESC_BANANA + DEFINITION_DESC_BANANA + TAG_DESC_LONG;

        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withQuestion(VALID_QUESTION_BANANA)
                .withAnswer(VALID_ANSWER_BANANA).withDefinition(VALID_DEFINITION_BANANA)
                .withTag(VALID_TAG_ROUND, VALID_TAG_LONG).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + INVALID_QUESTION_DESC + VALID_QUESTION_BANANA;
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withQuestion(VALID_QUESTION_BANANA)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DEFINITION_DESC_BANANA + INVALID_QUESTION_DESC + ANSWER_DESC_BANANA
                + QUESTION_DESC_BANANA;
        descriptor = new EditFlashcardDescriptorBuilder().withQuestion(VALID_QUESTION_BANANA)
                .withDefinition(VALID_DEFINITION_BANANA).withAnswer(VALID_ANSWER_BANANA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_FLASHCARD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withTag().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
