package seedu.moolah.logic.parser.budget;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.moolah.logic.commands.CommandTestUtil.BUDGET_AMOUNT_DESC_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.BUDGET_DESCRIPTION_DESC_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.BUDGET_PERIOD_DESC_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.BUDGET_START_DATE_DESC_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_AMOUNT_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_DESCRIPTION_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_PERIOD_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_START_DATE_SCHOOL;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.budget.AddBudgetCommand;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;

public class AddBudgetCommandParserTest {
    private AddBudgetCommandParser parser = new AddBudgetCommandParser();

    @Test
    void parse_blankArgument_parseException() {
        assertParseFailure(
                parser,
                "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_nullArgument_nullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE);

        // missing description prefix
        assertParseFailure(parser, VALID_BUDGET_DESCRIPTION_SCHOOL + BUDGET_AMOUNT_DESC_SCHOOL
                        + BUDGET_START_DATE_DESC_SCHOOL + BUDGET_PERIOD_DESC_SCHOOL,
                expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, BUDGET_DESCRIPTION_DESC_SCHOOL + VALID_BUDGET_AMOUNT_SCHOOL
                        + BUDGET_START_DATE_DESC_SCHOOL + BUDGET_PERIOD_DESC_SCHOOL,
                expectedMessage);

        // missing start date prefix
        assertParseFailure(parser, BUDGET_DESCRIPTION_DESC_SCHOOL + BUDGET_AMOUNT_DESC_SCHOOL
                        + VALID_BUDGET_START_DATE_SCHOOL + BUDGET_PERIOD_DESC_SCHOOL,
                expectedMessage);

        // missing period prefix
        assertParseFailure(parser, BUDGET_DESCRIPTION_DESC_SCHOOL + BUDGET_AMOUNT_DESC_SCHOOL
                        + BUDGET_START_DATE_DESC_SCHOOL + VALID_BUDGET_PERIOD_SCHOOL,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_BUDGET_DESCRIPTION_SCHOOL + VALID_BUDGET_AMOUNT_SCHOOL
                        + VALID_BUDGET_START_DATE_SCHOOL + VALID_BUDGET_PERIOD_SCHOOL,
                expectedMessage);
    }

    @Test
    public void parse_repeatedPrefix_failure() {
        assertParseFailure(parser, BUDGET_DESCRIPTION_DESC_SCHOOL + BUDGET_AMOUNT_DESC_SCHOOL
                        + BUDGET_START_DATE_DESC_SCHOOL + BUDGET_PERIOD_DESC_SCHOOL + BUDGET_PERIOD_DESC_SCHOOL,
                MESSAGE_REPEATED_PREFIX_COMMAND);
    }

    @Test
    void parse_allFieldsPresent_success() {
        // valid budget name, amount, start date, period
        try {
            Budget budget = new Budget(
                    new Description(VALID_BUDGET_DESCRIPTION_SCHOOL),
                    new Price(VALID_BUDGET_AMOUNT_SCHOOL),
                    Timestamp.createTimestampIfValid(BUDGET_START_DATE_DESC_SCHOOL).get(),
                    ParserUtil.parsePeriod(BUDGET_PERIOD_DESC_SCHOOL));
            assertParseSuccess(parser, BUDGET_DESCRIPTION_DESC_SCHOOL + BUDGET_AMOUNT_DESC_SCHOOL
                            + BUDGET_START_DATE_DESC_SCHOOL + BUDGET_PERIOD_DESC_SCHOOL,
                    new AddBudgetCommand(budget));
        } catch (ParseException e) {
            //shouldn't give exception
        }
    }
}
