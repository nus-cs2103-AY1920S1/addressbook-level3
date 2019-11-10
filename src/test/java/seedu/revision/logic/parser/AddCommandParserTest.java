package seedu.revision.logic.parser;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.commands.CommandTestUtil.CATEGORY_DESC_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.CATEGORY_DESC_UML;
import static seedu.revision.logic.commands.CommandTestUtil.CORRECT_ANSWER_DESC_BROWNFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.DIFFICULTY_DESC_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.DIFFICULTY_DESC_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.INVALID_DIFFICULTY_DESC;
import static seedu.revision.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.revision.logic.commands.CommandTestUtil.MCQ_WRONG_ANSWER_DESC;
import static seedu.revision.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.revision.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.revision.logic.commands.CommandTestUtil.QUESTION_DESC_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.QUESTION_DESC_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.QUESTION_TYPE_MCQ;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_UML;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_QUESTION_BETA;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.revision.testutil.TypicalMcqs.MCQ_B;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.main.AddCommand;
import seedu.revision.logic.parser.main.AddCommandParser;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Question;
import seedu.revision.testutil.McqBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Answerable expectedAnswerable = new McqBuilder(MCQ_B).withCategories(VALID_CATEGORY_UML).build();

        // whitespace only preamble
        String userInput = (PREAMBLE_WHITESPACE + QUESTION_TYPE_MCQ + QUESTION_DESC_BETA + DIFFICULTY_DESC_BETA
                + CORRECT_ANSWER_DESC_BROWNFIELD + MCQ_WRONG_ANSWER_DESC + CATEGORY_DESC_UML);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + QUESTION_TYPE_MCQ + QUESTION_DESC_BETA
                + DIFFICULTY_DESC_BETA + CORRECT_ANSWER_DESC_BROWNFIELD + MCQ_WRONG_ANSWER_DESC + CATEGORY_DESC_UML,
                new AddCommand(expectedAnswerable));

        // multiple question - last question accepted
        assertParseSuccess(parser, QUESTION_TYPE_MCQ + QUESTION_DESC_ALPHA + QUESTION_DESC_BETA
                + DIFFICULTY_DESC_BETA + CORRECT_ANSWER_DESC_BROWNFIELD + MCQ_WRONG_ANSWER_DESC + CATEGORY_DESC_UML,
                new AddCommand(expectedAnswerable));

        // multiple difficulty - last difficulty accepted
        assertParseSuccess(parser, QUESTION_TYPE_MCQ + QUESTION_DESC_BETA + DIFFICULTY_DESC_ALPHA
                + DIFFICULTY_DESC_BETA + CORRECT_ANSWER_DESC_BROWNFIELD + MCQ_WRONG_ANSWER_DESC + CATEGORY_DESC_UML,
                new AddCommand(expectedAnswerable));

        // multiple categories - all accepted
        Answerable expectedAnswerableMultipleTags = new McqBuilder(MCQ_B).withCategories(VALID_CATEGORY_UML,
                VALID_CATEGORY_GREENFIELD).build();
        assertParseSuccess(parser, QUESTION_TYPE_MCQ + QUESTION_DESC_BETA + DIFFICULTY_DESC_BETA
                + CORRECT_ANSWER_DESC_BROWNFIELD + MCQ_WRONG_ANSWER_DESC + CATEGORY_DESC_GREENFIELD + CATEGORY_DESC_UML,
                new AddCommand(expectedAnswerableMultipleTags));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing question prefix
        assertParseFailure(parser, VALID_QUESTION_BETA + DIFFICULTY_DESC_BETA, expectedMessage);

        // missing difficulty prefix
        assertParseFailure(parser, QUESTION_DESC_BETA + VALID_DIFFICULTY_BETA, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_QUESTION_BETA + VALID_DIFFICULTY_BETA, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, QUESTION_TYPE_MCQ + INVALID_QUESTION_DESC + CORRECT_ANSWER_DESC_BROWNFIELD
                + MCQ_WRONG_ANSWER_DESC + DIFFICULTY_DESC_BETA + CATEGORY_DESC_GREENFIELD + CATEGORY_DESC_UML,
                Question.MESSAGE_CONSTRAINTS);

        // invalid difficulty
        assertParseFailure(parser, QUESTION_TYPE_MCQ + QUESTION_DESC_BETA + CORRECT_ANSWER_DESC_BROWNFIELD
                + MCQ_WRONG_ANSWER_DESC + INVALID_DIFFICULTY_DESC + CATEGORY_DESC_GREENFIELD
                + CATEGORY_DESC_UML, Difficulty.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + QUESTION_TYPE_MCQ + QUESTION_DESC_BETA
                + DIFFICULTY_DESC_BETA + CORRECT_ANSWER_DESC_BROWNFIELD + MCQ_WRONG_ANSWER_DESC
                + CATEGORY_DESC_GREENFIELD + CATEGORY_DESC_UML, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_USAGE));
    }
}
