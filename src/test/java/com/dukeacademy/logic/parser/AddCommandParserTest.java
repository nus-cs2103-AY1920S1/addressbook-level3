package com.dukeacademy.logic.parser;

import static com.dukeacademy.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static com.dukeacademy.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static com.dukeacademy.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static com.dukeacademy.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static com.dukeacademy.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static com.dukeacademy.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static com.dukeacademy.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static com.dukeacademy.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static com.dukeacademy.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static com.dukeacademy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static com.dukeacademy.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import com.dukeacademy.commons.core.Messages;
import com.dukeacademy.logic.commands.AddCommand;
import com.dukeacademy.model.question.Difficulty;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.Status;
import com.dukeacademy.model.question.Title;
import com.dukeacademy.model.question.Topic;
import com.dukeacademy.model.tag.Tag;
import com.dukeacademy.testutil.QuestionBuilder;
import com.dukeacademy.testutil.TypicalQuestions;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Question expectedQuestion = new QuestionBuilder(TypicalQuestions.BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(
            expectedQuestion));

        // multiple names - last title accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(
            expectedQuestion));

        // multiple phones - last topic accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(
            expectedQuestion));

        // multiple emails - last status accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(
            expectedQuestion));

        // multiple addresses - last difficulty accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(
            expectedQuestion));

        // multiple tags - all accepted
        Question
            expectedQuestionMultipleTags = new QuestionBuilder(
            TypicalQuestions.BOB).withTags(VALID_TAG_FRIEND,
                VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(
            expectedQuestionMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Question
            expectedQuestion = new QuestionBuilder(TypicalQuestions.AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedQuestion));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing topic prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing status prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing difficulty prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Title.MESSAGE_CONSTRAINTS);

        // invalid topic
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Topic.MESSAGE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Status.MESSAGE_CONSTRAINTS);

        // invalid difficulty
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Difficulty.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Title.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
