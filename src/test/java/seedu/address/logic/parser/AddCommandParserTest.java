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
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalFlashCards.NUS;
import static seedu.address.testutil.TypicalFlashCards.WWII;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.category.Category;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;
import seedu.address.testutil.FlashCardBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        FlashCard expectedFlashCard = new FlashCardBuilder(NUS).withCatgeories(VALID_CATEGORY_LOCATION).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + QUESTION_DESC_2 + ANSWER_DESC_2
                + RATING_DESC_2 + CATEGORY_DESC_HISTORY, new AddCommand(expectedFlashCard));

        // multiple names - last name accepted
        assertParseSuccess(parser, QUESTION_DESC_1 + QUESTION_DESC_2 + ANSWER_DESC_2
                + RATING_DESC_2 + CATEGORY_DESC_HISTORY, new AddCommand(expectedFlashCard));

        // multiple answers - last answer accepted
        assertParseSuccess(parser, QUESTION_DESC_2 + ANSWER_DESC_1 + ANSWER_DESC_2
                + RATING_DESC_2 + CATEGORY_DESC_HISTORY, new AddCommand(expectedFlashCard));

        // multiple ratings - last rating accepted
        assertParseSuccess(parser, QUESTION_DESC_2 + ANSWER_DESC_2 + RATING_DESC_1
                + RATING_DESC_2 + CATEGORY_DESC_HISTORY, new AddCommand(expectedFlashCard));

        // multiple categories - all accepted
        FlashCard expectedFlashCardMultipleCategories =
                new FlashCardBuilder(NUS)
                        .withCatgeories(VALID_CATEGORY_LOCATION, VALID_CATEGORY_HISTORY)
                        .build();
        assertParseSuccess(parser, QUESTION_DESC_2 + ANSWER_DESC_2 + RATING_DESC_2
                + CATEGORY_DESC_LOCATION + CATEGORY_DESC_HISTORY, new AddCommand(expectedFlashCardMultipleCategories));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero categories
        FlashCard expectedFlashCard = new FlashCardBuilder(WWII).withCatgeories().build();
        assertParseSuccess(parser, QUESTION_DESC_1 + ANSWER_DESC_1 + RATING_DESC_1,
                new AddCommand(expectedFlashCard));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_QUESTION_2 + ANSWER_DESC_2 + RATING_DESC_2,
                expectedMessage);

        // missing answer prefix
        assertParseFailure(parser, QUESTION_DESC_2 + VALID_ANSWER_2 + RATING_DESC_2,
                expectedMessage);

        // missing rating prefix
        assertParseFailure(parser, QUESTION_DESC_2 + ANSWER_DESC_2 + VALID_RATING_2,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_QUESTION_2 + VALID_ANSWER_2 + VALID_RATING_2,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_2 + RATING_DESC_2
                + CATEGORY_DESC_LOCATION + CATEGORY_DESC_HISTORY, Question.MESSAGE_CONSTRAINTS);

        // invalid answer
        assertParseFailure(parser, QUESTION_DESC_2 + INVALID_ANSWER_DESC + RATING_DESC_2
                + CATEGORY_DESC_LOCATION + CATEGORY_DESC_HISTORY, Answer.MESSAGE_CONSTRAINTS);

        // invalid rating
        assertParseFailure(parser, QUESTION_DESC_2 + ANSWER_DESC_2 + INVALID_RATING_DESC
                + CATEGORY_DESC_LOCATION + CATEGORY_DESC_HISTORY, Rating.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, QUESTION_DESC_2 + ANSWER_DESC_2 + RATING_DESC_2
                + INVALID_CATEGORY_DESC + VALID_CATEGORY_LOCATION, Category.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_2 + INVALID_RATING_DESC,
                Question.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + QUESTION_DESC_2 + ANSWER_DESC_2
                + RATING_DESC_2 + CATEGORY_DESC_LOCATION + CATEGORY_DESC_HISTORY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
