package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.PERIOD_DESC_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERIOD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_SCHOOL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.budget.AddBudgetCommand;

public class AddBudgetCommandParserTest {
    private AddBudgetCommandParser parser = new AddBudgetCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE);

        // missing description prefix
        assertParseFailure(parser, VALID_DESCRIPTION_SCHOOL + AMOUNT_DESC_SCHOOL + START_DATE_DESC_SCHOOL
                        + PERIOD_DESC_SCHOOL,
                expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, DESCRIPTION_DESC_SCHOOL + VALID_AMOUNT_SCHOOL + START_DATE_DESC_SCHOOL
                        + PERIOD_DESC_SCHOOL,
                expectedMessage);

        // missing start date prefix
        assertParseFailure(parser, DESCRIPTION_DESC_SCHOOL + AMOUNT_DESC_SCHOOL + VALID_START_DATE_SCHOOL
                        + PERIOD_DESC_SCHOOL,
                expectedMessage);

        // missing period prefix
        assertParseFailure(parser, DESCRIPTION_DESC_SCHOOL + AMOUNT_DESC_SCHOOL + START_DATE_DESC_SCHOOL
                        + VALID_PERIOD_SCHOOL,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_DESCRIPTION_SCHOOL + VALID_AMOUNT_SCHOOL + VALID_START_DATE_SCHOOL
                        + VALID_PERIOD_SCHOOL,
                expectedMessage);
    }
}
