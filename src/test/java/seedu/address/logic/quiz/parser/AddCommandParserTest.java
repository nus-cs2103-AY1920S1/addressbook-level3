package seedu.address.logic.quiz.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.quiz.commands.CommandTestUtil.*;
import static seedu.address.logic.quiz.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.quiz.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalQuestion.AMY;
import static seedu.address.testutil.TypicalQuestion.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.quiz.commands.AddCommand;

import seedu.address.model.quiz.person.Answer;
import seedu.address.model.quiz.person.Category;
import seedu.address.model.quiz.person.Name;
import seedu.address.model.quiz.person.Question;
import seedu.address.model.quiz.person.Type;
import seedu.address.model.quiz.tag.Tag;
import seedu.address.testutil.QuestionBuilder;



public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Question expectedPerson = new QuestionBuilder(BOB).withTags(VALID_TAG_LECTURE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + ANSWER_DESC_BOB + CATEGORY_DESC_BOB
                + TYPE_DESC_BOB + TAG_DESC_LECTURE, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + ANSWER_DESC_BOB + CATEGORY_DESC_BOB
                + TYPE_DESC_BOB + TAG_DESC_LECTURE, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ANSWER_DESC_AMY + ANSWER_DESC_BOB + CATEGORY_DESC_BOB
                + TYPE_DESC_BOB + TAG_DESC_LECTURE, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ANSWER_DESC_BOB + CATEGORY_DESC_AMY + CATEGORY_DESC_BOB
                + TYPE_DESC_BOB + TAG_DESC_LECTURE, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ANSWER_DESC_BOB + CATEGORY_DESC_BOB + CATEGORY_DESC_AMY
                + TYPE_DESC_BOB + TAG_DESC_LECTURE, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Question expectedPersonMultipleTags = new QuestionBuilder(BOB).withTags(VALID_TAG_LECTURE, VALID_TAG_TUTORIAL)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + ANSWER_DESC_BOB + CATEGORY_DESC_BOB + TYPE_DESC_BOB
                + TAG_DESC_TUTORIAL + TAG_DESC_LECTURE, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Question expectedPerson = new QuestionBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + ANSWER_DESC_AMY + CATEGORY_DESC_AMY + CATEGORY_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + ANSWER_DESC_BOB + CATEGORY_DESC_BOB + TYPE_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_ANSWER_BOB + CATEGORY_DESC_BOB + TYPE_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + ANSWER_DESC_BOB + VALID_CATEGORY_BOB + TYPE_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + ANSWER_DESC_BOB + CATEGORY_DESC_BOB + VALID_TYPE_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_ANSWER_BOB + VALID_CATEGORY_BOB + VALID_TYPE_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + ANSWER_DESC_BOB + CATEGORY_DESC_BOB + TYPE_DESC_BOB
                + TAG_DESC_TUTORIAL + TAG_DESC_LECTURE, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_ANSWER_DESC + CATEGORY_DESC_BOB + TYPE_DESC_BOB
                + TAG_DESC_TUTORIAL + TAG_DESC_LECTURE, Answer.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + ANSWER_DESC_BOB + INVALID_CATEGORY_DESC + TYPE_DESC_BOB
                + TAG_DESC_TUTORIAL + TAG_DESC_LECTURE, Category.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + ANSWER_DESC_BOB + CATEGORY_DESC_BOB + INVALID_TYPE_DESC
                + TAG_DESC_TUTORIAL + TAG_DESC_LECTURE, Type.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + ANSWER_DESC_BOB + CATEGORY_DESC_BOB + TYPE_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_LECTURE, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + ANSWER_DESC_BOB + CATEGORY_DESC_BOB + INVALID_TYPE_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + ANSWER_DESC_BOB + CATEGORY_DESC_BOB
                + TYPE_DESC_BOB + TAG_DESC_TUTORIAL + TAG_DESC_LECTURE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
