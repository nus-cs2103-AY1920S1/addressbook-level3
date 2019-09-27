package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.category.Category;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;
import seedu.address.testutil.EditFlashCardDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_CATEGORY;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_QUESTION_1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + QUESTION_DESC_1, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + QUESTION_DESC_1, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_QUESTION_DESC, Question.MESSAGE_CONSTRAINTS); // invalid question
        assertParseFailure(parser, "1" + INVALID_ANSWER_DESC, Answer.MESSAGE_CONSTRAINTS); // invalid answer
        assertParseFailure(parser, "1" + INVALID_RATING_DESC, Rating.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS); // invalid tag


        // valid answer followed by invalid answer. The test case for invalid answer followed by valid answer
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + ANSWER_DESC_2 + INVALID_ANSWER_DESC, Answer.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the categories of the {@code FlashCard} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser,
                "1" + CATEGORY_DESC_HISTORY + CATEGORY_DESC_LOCATION
                        + TAG_EMPTY,
                Category.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,
                "1" + CATEGORY_DESC_HISTORY + TAG_EMPTY + CATEGORY_DESC_LOCATION,
                Category.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,
                "1" + TAG_EMPTY + CATEGORY_DESC_HISTORY + CATEGORY_DESC_LOCATION,
                Category.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_QUESTION_DESC + RATING_DESC_1
                        + INVALID_ANSWER_DESC,
                Question.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + ANSWER_DESC_2 + CATEGORY_DESC_LOCATION
                 + RATING_DESC_1 + QUESTION_DESC_1 + CATEGORY_DESC_HISTORY;

        EditPersonDescriptor descriptor = new EditFlashCardDescriptorBuilder().withQuestion(VALID_QUESTION_1)
                .withAnswer(VALID_ANSWER_2).withRating(VALID_RATING_1)
                .withCategories(VALID_CATEGORY_HISTORY, VALID_CATEGORY_LOCATION).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + ANSWER_DESC_2;

        EditPersonDescriptor descriptor = new EditFlashCardDescriptorBuilder().withAnswer(VALID_ANSWER_2).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + QUESTION_DESC_1;
        EditPersonDescriptor descriptor = new EditFlashCardDescriptorBuilder().withQuestion(VALID_QUESTION_1).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // answer
        userInput = targetIndex.getOneBased() + ANSWER_DESC_1;
        descriptor = new EditFlashCardDescriptorBuilder().withAnswer(VALID_ANSWER_1).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + RATING_DESC_1;
        descriptor = new EditFlashCardDescriptorBuilder().withRating(VALID_RATING_1).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // categories
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_HISTORY;
        descriptor = new EditFlashCardDescriptorBuilder().withCategories(VALID_CATEGORY_LOCATION).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + ANSWER_DESC_1 + RATING_DESC_1
                + CATEGORY_DESC_HISTORY + ANSWER_DESC_1 + RATING_DESC_1 + CATEGORY_DESC_HISTORY
                + ANSWER_DESC_2 + RATING_DESC_2 + CATEGORY_DESC_LOCATION;

        EditPersonDescriptor descriptor = new EditFlashCardDescriptorBuilder().withAnswer(VALID_ANSWER_2)
                .withRating(VALID_RATING_2).withCategories(VALID_CATEGORY_LOCATION, VALID_CATEGORY_HISTORY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_ANSWER_DESC + ANSWER_DESC_2;
        EditPersonDescriptor descriptor = new EditFlashCardDescriptorBuilder().withAnswer(VALID_ANSWER_2).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_ANSWER_DESC + RATING_DESC_2
                + ANSWER_DESC_2;
        descriptor = new EditFlashCardDescriptorBuilder().withAnswer(VALID_ANSWER_2)
                .withRating(VALID_RATING_2).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditFlashCardDescriptorBuilder().withCategories().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
