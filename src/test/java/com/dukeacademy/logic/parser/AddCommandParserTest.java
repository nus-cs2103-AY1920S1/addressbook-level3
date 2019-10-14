package com.dukeacademy.logic.parser;

import static com.dukeacademy.logic.commands.CommandTestUtil.DIFFICULTY_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.DIFFICULTY_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static com.dukeacademy.logic.commands.CommandTestUtil.STATUS_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static com.dukeacademy.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static com.dukeacademy.logic.commands.CommandTestUtil.TITLE_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.TITLE_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.TOPIC_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.TOPIC_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TOPIC_BOB;
import static com.dukeacademy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static com.dukeacademy.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import com.dukeacademy.commons.core.Messages;
import com.dukeacademy.logic.commands.AddCommand;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.testutil.QuestionBuilder;
import com.dukeacademy.testutil.TypicalQuestions;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Question expectedQuestion = new QuestionBuilder(TypicalQuestions.BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_BOB + TOPIC_DESC_BOB + STATUS_DESC_BOB
                + DIFFICULTY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(
            expectedQuestion));

        // multiple names - last title accepted
        assertParseSuccess(parser, TITLE_DESC_AMY + TITLE_DESC_BOB + TOPIC_DESC_BOB + STATUS_DESC_BOB
                + DIFFICULTY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(
            expectedQuestion));

        // multiple phones - last topic accepted
        assertParseSuccess(parser, TITLE_DESC_BOB + TOPIC_DESC_AMY + TOPIC_DESC_BOB + STATUS_DESC_BOB
                + DIFFICULTY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(
            expectedQuestion));

        // multiple emails - last status accepted
        assertParseSuccess(parser, TITLE_DESC_BOB + TOPIC_DESC_BOB + STATUS_DESC_AMY + STATUS_DESC_BOB
                + DIFFICULTY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(
            expectedQuestion));

        // multiple addresses - last difficulty accepted
        assertParseSuccess(parser, TITLE_DESC_BOB + TOPIC_DESC_BOB + STATUS_DESC_BOB + DIFFICULTY_DESC_AMY
                + DIFFICULTY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(
            expectedQuestion));

        // multiple tags - all accepted
        Question
            expectedQuestionMultipleTags = new QuestionBuilder(
            TypicalQuestions.BOB).withTags(VALID_TAG_FRIEND,
                VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, TITLE_DESC_BOB + TOPIC_DESC_BOB + STATUS_DESC_BOB + DIFFICULTY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(
            expectedQuestionMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Question
            expectedQuestion = new QuestionBuilder(TypicalQuestions.AMY).withTags().build();
        assertParseSuccess(parser, TITLE_DESC_AMY + TOPIC_DESC_AMY + STATUS_DESC_AMY + DIFFICULTY_DESC_AMY,
                new AddCommand(expectedQuestion));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_BOB + TOPIC_DESC_BOB + STATUS_DESC_BOB + DIFFICULTY_DESC_BOB,
                expectedMessage);

        // missing topic prefix
        assertParseFailure(parser, TITLE_DESC_BOB + VALID_TOPIC_BOB + STATUS_DESC_BOB + DIFFICULTY_DESC_BOB,
                expectedMessage);

        // missing status prefix
        assertParseFailure(parser, TITLE_DESC_BOB + TOPIC_DESC_BOB + VALID_STATUS_BOB + DIFFICULTY_DESC_BOB,
                expectedMessage);

        // missing difficulty prefix
        assertParseFailure(parser, TITLE_DESC_BOB + TOPIC_DESC_BOB + STATUS_DESC_BOB + VALID_DIFFICULTY_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_BOB + VALID_TOPIC_BOB + VALID_STATUS_BOB + VALID_DIFFICULTY_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        /*
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + TOPIC_DESC_BOB + STATUS_DESC_BOB + DIFFICULTY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Title.MESSAGE_CONSTRAINTS);

        // invalid topic
        assertParseFailure(parser, TITLE_DESC_BOB + INVALID_TOPIC_DESC + STATUS_DESC_BOB + DIFFICULTY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Topic.MESSAGE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, TITLE_DESC_BOB + TOPIC_DESC_BOB + INVALID_STATUS_DESC + DIFFICULTY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Status.MESSAGE_CONSTRAINTS);

        // invalid difficulty
        assertParseFailure(parser, TITLE_DESC_BOB + TOPIC_DESC_BOB + STATUS_DESC_BOB + INVALID_DIFFICULTY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Difficulty.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TITLE_DESC_BOB + TOPIC_DESC_BOB + STATUS_DESC_BOB + DIFFICULTY_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + TOPIC_DESC_BOB + STATUS_DESC_BOB + INVALID_DIFFICULTY_DESC,
                Title.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_BOB + TOPIC_DESC_BOB + STATUS_DESC_BOB
                + DIFFICULTY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
         */
    }
}
