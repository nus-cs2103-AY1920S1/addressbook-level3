package seedu.address.logic.quiz.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.quiz.commands.CommandTestUtil.ANSWER_DESC_AMY;
import static seedu.address.logic.quiz.commands.CommandTestUtil.ANSWER_DESC_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.CATEGORY_DESC_AMY;
import static seedu.address.logic.quiz.commands.CommandTestUtil.CATEGORY_DESC_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.address.logic.quiz.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.quiz.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.quiz.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.quiz.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.quiz.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.quiz.commands.CommandTestUtil.TAG_DESC_LECTURE;
import static seedu.address.logic.quiz.commands.CommandTestUtil.TAG_DESC_TUTORIAL;
import static seedu.address.logic.quiz.commands.CommandTestUtil.TYPE_DESC_AMY;
import static seedu.address.logic.quiz.commands.CommandTestUtil.TYPE_DESC_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_ANSWER_AMY;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_CATEGORY_AMY;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_CATEGORY_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_TAG_LECTURE;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_TAG_TUTORIAL;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_TYPE_AMY;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_TYPE_BOB;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.quiz.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.quiz.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.quiz.commands.EditCommand;
import seedu.address.logic.quiz.commands.EditCommand.EditQuestionDescriptor;
import seedu.address.model.quiz.person.Answer;
import seedu.address.model.quiz.person.Category;
import seedu.address.model.quiz.person.Name;
import seedu.address.model.quiz.person.Type;
import seedu.address.model.quiz.tag.Tag;
import seedu.address.testutil.EditQuestionDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_ANSWER_DESC, Answer.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_TYPE_DESC, Type.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_ANSWER_DESC + CATEGORY_DESC_AMY, Answer.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + ANSWER_DESC_BOB + INVALID_ANSWER_DESC, Answer.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_LECTURE + TAG_DESC_TUTORIAL + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_LECTURE + TAG_EMPTY + TAG_DESC_TUTORIAL, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_LECTURE + TAG_DESC_TUTORIAL, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_CATEGORY_DESC + VALID_TYPE_AMY + VALID_ANSWER_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + ANSWER_DESC_BOB + TAG_DESC_TUTORIAL
                + CATEGORY_DESC_AMY + TYPE_DESC_AMY + NAME_DESC_AMY + TAG_DESC_LECTURE;

        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withName(VALID_NAME_AMY)
                .withAnswer(VALID_ANSWER_BOB).withCategory(VALID_CATEGORY_AMY).withType(VALID_TYPE_AMY)
                .withTags(VALID_TAG_TUTORIAL, VALID_TAG_LECTURE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + ANSWER_DESC_BOB + CATEGORY_DESC_AMY;

        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withAnswer(VALID_ANSWER_BOB)
                .withCategory(VALID_CATEGORY_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + ANSWER_DESC_AMY;
        descriptor = new EditQuestionDescriptorBuilder().withAnswer(VALID_ANSWER_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_AMY;
        descriptor = new EditQuestionDescriptorBuilder().withCategory(VALID_CATEGORY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + TYPE_DESC_AMY;
        descriptor = new EditQuestionDescriptorBuilder().withType(VALID_TYPE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_LECTURE;
        descriptor = new EditQuestionDescriptorBuilder().withTags(VALID_TAG_LECTURE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + ANSWER_DESC_AMY + TYPE_DESC_AMY + CATEGORY_DESC_AMY
                + TAG_DESC_LECTURE + ANSWER_DESC_AMY + TYPE_DESC_AMY + CATEGORY_DESC_AMY + TAG_DESC_LECTURE
                + ANSWER_DESC_BOB + TYPE_DESC_BOB + CATEGORY_DESC_BOB + TAG_DESC_TUTORIAL;

        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withAnswer(VALID_ANSWER_BOB)
                .withCategory(VALID_CATEGORY_BOB).withType(VALID_TYPE_BOB)
                .withTags(VALID_TAG_LECTURE, VALID_TAG_TUTORIAL).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_ANSWER_DESC + ANSWER_DESC_BOB;
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withAnswer(VALID_ANSWER_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_BOB + INVALID_ANSWER_DESC + TYPE_DESC_BOB
                + ANSWER_DESC_BOB;
        descriptor = new EditQuestionDescriptorBuilder().withAnswer(VALID_ANSWER_BOB).withCategory(VALID_CATEGORY_BOB)
                .withType(VALID_TYPE_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
