package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.commands.CommandTestUtil.ANSWER_DESC_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.ANSWER_DESC_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.DEFINITION_DESC_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.DEFINITION_DESC_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_CHOICE_DESC;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_DEFINITION_DESC;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.flashcard.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.flashcard.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.flashcard.logic.commands.CommandTestUtil.QUESTION_DESC_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.QUESTION_DESC_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.TAG_DESC_LONG;
import static seedu.flashcard.logic.commands.CommandTestUtil.TAG_DESC_ROUND;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_ANSWER_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_ANSWER_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_CHOICE_BLUE;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_CHOICE_GREEN;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_DEFINITION_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_DEFINITION_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_QUESTION_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_QUESTION_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_ROUND;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.flashcard.testutil.TypicalFlashcard.BANANA;
import static seedu.flashcard.testutil.TypicalFlashcard.NO_TAG_BANANA;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.commands.AddCommand;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.tag.Tag;



public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Flashcard expectedFlashcard = BANANA;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + QUESTION_DESC_BANANA + ANSWER_DESC_BANANA
                + DEFINITION_DESC_BANANA + TAG_DESC_LONG, new AddCommand(expectedFlashcard));

        // multiple Questions - last name accepted
        assertParseSuccess(parser, QUESTION_DESC_APPLE + QUESTION_DESC_BANANA + ANSWER_DESC_BANANA
                + DEFINITION_DESC_BANANA + TAG_DESC_LONG, new AddCommand(expectedFlashcard));

        // multiple Answers - last phone accepted
        assertParseSuccess(parser, QUESTION_DESC_BANANA + ANSWER_DESC_APPLE + ANSWER_DESC_BANANA
                + DEFINITION_DESC_BANANA + TAG_DESC_LONG, new AddCommand(expectedFlashcard));
    }



    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags and choices
        Flashcard expectedFlashcard = NO_TAG_BANANA;
        assertParseSuccess(parser, QUESTION_DESC_BANANA + ANSWER_DESC_BANANA
                + DEFINITION_DESC_BANANA, new AddCommand(expectedFlashcard));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT + AddCommand.MESSAGE_USAGE);

        // missing Question prefix
        assertParseFailure(parser, VALID_QUESTION_BANANA + ANSWER_DESC_BANANA
                + DEFINITION_DESC_BANANA + TAG_DESC_LONG, expectedMessage);

        // missing Answer prefix
        assertParseFailure(parser, QUESTION_DESC_BANANA + VALID_ANSWER_BANANA
                + DEFINITION_DESC_BANANA + TAG_DESC_LONG, expectedMessage);

        // missing Definition prefix
        assertParseFailure(parser, QUESTION_DESC_BANANA + ANSWER_DESC_BANANA
                + VALID_DEFINITION_BANANA + TAG_DESC_LONG, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_QUESTION_APPLE + VALID_ANSWER_APPLE + VALID_DEFINITION_APPLE
                + VALID_CHOICE_BLUE + VALID_CHOICE_GREEN + VALID_TAG_ROUND, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid Question
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_BANANA
                + DEFINITION_DESC_BANANA + TAG_DESC_LONG, Question.MESSAGE_CONSTRAINTS);

        // invalid Answer
        assertParseFailure(parser, QUESTION_DESC_BANANA + INVALID_ANSWER_DESC
                + DEFINITION_DESC_BANANA + TAG_DESC_LONG, Answer.MESSAGE_CONSTRAINTS);

        // invalid Definition
        assertParseFailure(parser, QUESTION_DESC_BANANA + ANSWER_DESC_BANANA
                + INVALID_DEFINITION_DESC + TAG_DESC_LONG, Definition.MESSAGE_CONSTRAINTS);

        // invalid Choice
        assertParseFailure(parser, QUESTION_DESC_APPLE + DEFINITION_DESC_APPLE + ANSWER_DESC_APPLE
                + TAG_DESC_ROUND + INVALID_CHOICE_DESC, Choice.MESSAGE_CONSTRAINTS);

        // invalid Tag
        assertParseFailure(parser, QUESTION_DESC_BANANA + ANSWER_DESC_BANANA
                + DEFINITION_DESC_BANANA + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_BANANA
                + DEFINITION_DESC_BANANA + INVALID_TAG_DESC, Question.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + INVALID_QUESTION_DESC + ANSWER_DESC_BANANA
                        + DEFINITION_DESC_BANANA + TAG_DESC_LONG,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT + AddCommand.MESSAGE_USAGE));
    }
}
