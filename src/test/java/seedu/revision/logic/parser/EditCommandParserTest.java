package seedu.revision.logic.parser;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.commands.CommandTestUtil.CORRECT_ANSWER_DESC;
import static seedu.revision.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.revision.logic.commands.CommandTestUtil.INVALID_DIFFICULTY_DESC;
import static seedu.revision.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.revision.logic.commands.CommandTestUtil.QUESTION_DESC_AMY;
import static seedu.revision.logic.commands.CommandTestUtil.DIFFICULTY_DESC_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.DIFFICULTY_DESC_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.QUESTION_TYPE_MCQ;
import static seedu.revision.logic.commands.CommandTestUtil.CATEGORY_DESC_UML;
import static seedu.revision.logic.commands.CommandTestUtil.CATEGORY_DESC_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_QUESTION_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_UML;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.WRONG_ANSWER_DESC;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.revision.testutil.TypicalIndexes.INDEX_FIRST_ANSWERABLE;
import static seedu.revision.testutil.TypicalIndexes.INDEX_SECOND_ANSWERABLE;
import static seedu.revision.testutil.TypicalIndexes.INDEX_THIRD_ANSWERABLE;

import org.junit.jupiter.api.Test;

import seedu.revision.commons.core.index.Index;
import seedu.revision.logic.commands.EditCommand;
import seedu.revision.logic.commands.EditCommand.EditAnswerableDescriptor;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.category.Category;
import seedu.revision.testutil.EditAnswerableDescriptorBuilder;

public class EditCommandParserTest {

    private static final String CATEGORY_EMPTY = " " + PREFIX_CATEGORY;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_QUESTION_ALPHA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + QUESTION_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + QUESTION_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_QUESTION_DESC, Question.MESSAGE_CONSTRAINTS); // invalid question
        assertParseFailure(parser, "1" + INVALID_DIFFICULTY_DESC, Difficulty.MESSAGE_CONSTRAINTS); // invalid difficulty
        assertParseFailure(parser, "1" + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS); // invalid category

        // invalid difficultty
        assertParseFailure(parser, "1" + INVALID_DIFFICULTY_DESC, Difficulty.MESSAGE_CONSTRAINTS);

        // valid difficulty followed by invalid difficulty. The test case for invalid difficulty followed by valid difficulty
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DIFFICULTY_DESC_BETA + INVALID_DIFFICULTY_DESC, Difficulty.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_CATEGORY} alone will reset the categories of the {@code Answerable} being edited,
        // parsing it together with a valid category results in error
        assertParseFailure(parser, "1" + CATEGORY_DESC_UML + CATEGORY_DESC_GREENFIELD + CATEGORY_EMPTY, Category.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + CATEGORY_DESC_UML + CATEGORY_EMPTY + CATEGORY_DESC_GREENFIELD, Category.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + CATEGORY_EMPTY + CATEGORY_DESC_UML + CATEGORY_DESC_GREENFIELD, Category.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_QUESTION_DESC + QUESTION_TYPE_MCQ + CORRECT_ANSWER_DESC
                + WRONG_ANSWER_DESC + VALID_CATEGORY_ALPHA + VALID_DIFFICULTY_ALPHA, Question.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ANSWERABLE;
        String userInput = targetIndex.getOneBased() + DIFFICULTY_DESC_BETA + CATEGORY_DESC_GREENFIELD
                + QUESTION_DESC_AMY + CATEGORY_DESC_UML;

        EditCommand.EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder().withQuestion(VALID_QUESTION_ALPHA)
                .withDifficulty(VALID_DIFFICULTY_BETA).withCategories(VALID_CATEGORY_GREENFIELD, VALID_CATEGORY_UML).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ANSWERABLE;
        String userInput = targetIndex.getOneBased() + DIFFICULTY_DESC_BETA;

        EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder().withDifficulty(VALID_DIFFICULTY_BETA).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_ANSWERABLE;
        String userInput = targetIndex.getOneBased() + QUESTION_DESC_AMY;
        EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder().withQuestion(VALID_QUESTION_ALPHA).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // difficulty
        userInput = targetIndex.getOneBased() + DIFFICULTY_DESC_ALPHA;
        descriptor = new EditAnswerableDescriptorBuilder().withDifficulty(VALID_DIFFICULTY_ALPHA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_UML;
        descriptor = new EditAnswerableDescriptorBuilder().withCategories(VALID_CATEGORY_UML).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ANSWERABLE;
        String userInput = targetIndex.getOneBased() + DIFFICULTY_DESC_ALPHA + CATEGORY_DESC_UML
                + DIFFICULTY_DESC_ALPHA + CATEGORY_DESC_UML + DIFFICULTY_DESC_BETA
                + CATEGORY_DESC_GREENFIELD;

        EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder().withDifficulty(VALID_DIFFICULTY_BETA)
                .withCategories(VALID_CATEGORY_UML, VALID_CATEGORY_GREENFIELD).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ANSWERABLE;
        String userInput = targetIndex.getOneBased() + INVALID_DIFFICULTY_DESC + DIFFICULTY_DESC_BETA;
        EditCommand.EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder().withDifficulty(VALID_DIFFICULTY_BETA).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_DIFFICULTY_DESC + DIFFICULTY_DESC_BETA;
        descriptor = new EditAnswerableDescriptorBuilder().withDifficulty(VALID_DIFFICULTY_BETA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
