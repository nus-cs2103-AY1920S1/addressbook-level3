package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.logic.commands.CommandTestUtil.AMOUNT_DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.AMOUNT_DESC_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.DESCRIPTION_DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.billboard.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.billboard.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.billboard.logic.commands.CommandTestUtil.NAME_DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.NAME_DESC_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.TAG_DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.TAG_DESC_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_AMOUNT_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_AMOUNT_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_NAME_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_NAME_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_TAXES;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.index.Index;
import seedu.billboard.logic.commands.EditCommand;
import seedu.billboard.logic.commands.EditCommand.EditExpenseDescriptor;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Name;
import seedu.billboard.model.tag.Tag;
import seedu.billboard.testutil.EditExpenseDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_DINNER, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_DINNER, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_DINNER, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS); // invalid amount
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid amount followed by valid name
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC + NAME_DESC_DINNER, Amount.MESSAGE_CONSTRAINTS);

        // valid amount followed by invalid amount. The test case for invalid amount followed by valid amount
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + AMOUNT_DESC_TAXES + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Expense} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_DINNER + TAG_DESC_TAXES + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_DINNER + TAG_EMPTY + TAG_DESC_TAXES, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_DINNER + TAG_DESC_TAXES, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_AMOUNT_DESC + VALID_AMOUNT_DINNER
                        + VALID_DESCRIPTION_DINNER, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;

        String userInput = targetIndex.getOneBased() + NAME_DESC_DINNER + DESCRIPTION_DESC_TAXES + TAG_DESC_TAXES
                + AMOUNT_DESC_DINNER + TAG_DESC_DINNER;

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withName(VALID_NAME_DINNER)
                .withDescription(VALID_DESCRIPTION_TAXES).withAmount(VALID_AMOUNT_DINNER)
                .withTags(VALID_TAG_DINNER, VALID_TAG_TAXES).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_TAXES + AMOUNT_DESC_TAXES;

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withDescription(VALID_DESCRIPTION_TAXES)
                .withAmount(VALID_AMOUNT_TAXES).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_DINNER;
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withName(VALID_NAME_DINNER).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_DINNER;
        descriptor = new EditExpenseDescriptorBuilder().withDescription(VALID_DESCRIPTION_DINNER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_DINNER;
        descriptor = new EditExpenseDescriptorBuilder().withAmount(VALID_AMOUNT_DINNER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_DINNER;
        descriptor = new EditExpenseDescriptorBuilder().withTags(VALID_TAG_DINNER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_DINNER + AMOUNT_DESC_DINNER
                + TAG_DESC_DINNER + DESCRIPTION_DESC_DINNER + AMOUNT_DESC_DINNER + TAG_DESC_DINNER
                + DESCRIPTION_DESC_TAXES + AMOUNT_DESC_TAXES + TAG_DESC_TAXES;

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withDescription(VALID_DESCRIPTION_TAXES)
                .withAmount(VALID_AMOUNT_TAXES).withTags(VALID_TAG_TAXES, VALID_TAG_DINNER)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_TAXES + DESCRIPTION_DESC_TAXES;
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withDescription(VALID_DESCRIPTION_TAXES)
                .withAmount(VALID_AMOUNT_TAXES).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_TAXES + INVALID_NAME_DESC + AMOUNT_DESC_TAXES
                + NAME_DESC_TAXES;
        descriptor = new EditExpenseDescriptorBuilder()
                .withAmount(VALID_AMOUNT_TAXES)
                .withName(VALID_NAME_TAXES)
                .build();
        System.out.println(userInput);
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
