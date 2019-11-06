package seedu.moolah.logic.parser.budget;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.logic.commands.CommandTestUtil.BUDGET_AMOUNT_DESC_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.BUDGET_DESCRIPTION_DESC_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.BUDGET_PERIOD_DESC_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.BUDGET_START_DATE_DESC_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_BUDGET_AMOUNT_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_BUDGET_DESCRIPTION_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_BUDGET_PERIOD_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_BUDGET_START_DATE_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_AMOUNT_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_DESCRIPTION_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_PERIOD_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_START_DATE_SCHOOL;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.budget.EditBudgetCommand;
import seedu.moolah.logic.commands.budget.EditBudgetCommand.EditBudgetDescriptor;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.testutil.EditBudgetDescriptorBuilder;

public class EditBudgetCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBudgetCommand.MESSAGE_USAGE);

    private EditBudgetCommandParser parser = new EditBudgetCommandParser();

    @Test
    void parse_nullArgument_nullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    void parse_blankArgument_parseException() {
        assertParseFailure(
                parser,
                "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBudgetCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_BUDGET_DESCRIPTION_SCHOOL, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditBudgetCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + BUDGET_DESCRIPTION_DESC_SCHOOL, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + BUDGET_DESCRIPTION_DESC_SCHOOL, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 xadsfa@", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 c/ food", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(
                parser,
                "1" + INVALID_BUDGET_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser,
                "1" + INVALID_BUDGET_AMOUNT_DESC, Price.MESSAGE_CONSTRAINTS);

        // invalid start date
        assertParseFailure(parser,
                "1" + INVALID_BUDGET_START_DATE_DESC, Timestamp.MESSAGE_CONSTRAINTS_GENERAL);

        // invalid period
        assertParseFailure(parser,
                "1" + INVALID_BUDGET_PERIOD_DESC, Timestamp.MESSAGE_CONSTRAINTS_PERIOD);

        // invalid amount followed by valid description
        assertParseFailure(
                parser,
                "1" + INVALID_BUDGET_AMOUNT_DESC + VALID_BUDGET_DESCRIPTION_SCHOOL,
                Price.MESSAGE_CONSTRAINTS);

        // valid amount followed by invalid amount
        assertParseFailure(parser,
                "1" + BUDGET_AMOUNT_DESC_SCHOOL + INVALID_BUDGET_AMOUNT_DESC,
                Price.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(
                parser,
                "1" + INVALID_BUDGET_DESCRIPTION_DESC + INVALID_BUDGET_AMOUNT_DESC
                        + INVALID_BUDGET_START_DATE_DESC + VALID_BUDGET_PERIOD_SCHOOL,
                Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + BUDGET_DESCRIPTION_DESC_SCHOOL
                + BUDGET_AMOUNT_DESC_SCHOOL + BUDGET_START_DATE_DESC_SCHOOL + BUDGET_PERIOD_DESC_SCHOOL;

        EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder()
                .withDescription(VALID_BUDGET_DESCRIPTION_SCHOOL)
                .withAmount(VALID_BUDGET_AMOUNT_SCHOOL)
                .withStartDate(VALID_BUDGET_START_DATE_SCHOOL)
                .withPeriod(VALID_BUDGET_PERIOD_SCHOOL)
                .build();

        EditBudgetCommand expectedCommand = new EditBudgetCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        // description and amount
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased()
                + BUDGET_DESCRIPTION_DESC_SCHOOL
                + BUDGET_AMOUNT_DESC_SCHOOL;

        EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder()
                .withDescription(VALID_BUDGET_DESCRIPTION_SCHOOL)
                .withAmount(VALID_BUDGET_AMOUNT_SCHOOL)
                .build();
        EditBudgetCommand expectedCommand = new EditBudgetCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // description
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + BUDGET_DESCRIPTION_DESC_SCHOOL;
        EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder()
                .withDescription(VALID_BUDGET_DESCRIPTION_SCHOOL)
                .build();
        EditBudgetCommand expectedCommand = new EditBudgetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = targetIndex.getOneBased() + BUDGET_AMOUNT_DESC_SCHOOL;
        descriptor = new EditBudgetDescriptorBuilder()
                .withAmount(VALID_BUDGET_AMOUNT_SCHOOL)
                .build();
        expectedCommand = new EditBudgetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start date
        userInput = targetIndex.getOneBased() + BUDGET_START_DATE_DESC_SCHOOL;
        descriptor = new EditBudgetDescriptorBuilder()
                .withStartDate(VALID_BUDGET_START_DATE_SCHOOL)
                .build();
        expectedCommand = new EditBudgetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // period
        userInput = targetIndex.getOneBased() + BUDGET_PERIOD_DESC_SCHOOL;
        descriptor = new EditBudgetDescriptorBuilder()
                .withPeriod(VALID_BUDGET_PERIOD_SCHOOL)
                .build();
        expectedCommand = new EditBudgetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + INVALID_BUDGET_AMOUNT_DESC + BUDGET_AMOUNT_DESC_SCHOOL;
        EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder()
                .withAmount(VALID_BUDGET_AMOUNT_SCHOOL)
                .build();
        EditBudgetCommand expectedCommand = new EditBudgetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_BUDGET_AMOUNT_DESC + BUDGET_AMOUNT_DESC_SCHOOL
                + BUDGET_DESCRIPTION_DESC_SCHOOL;
        descriptor = new EditBudgetDescriptorBuilder()
                .withDescription(VALID_BUDGET_DESCRIPTION_SCHOOL)
                .withAmount(VALID_BUDGET_AMOUNT_SCHOOL)
                .build();
        expectedCommand = new EditBudgetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }



}
