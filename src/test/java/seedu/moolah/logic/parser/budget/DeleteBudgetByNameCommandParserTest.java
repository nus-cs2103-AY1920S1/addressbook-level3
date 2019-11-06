package seedu.moolah.logic.parser.budget;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.moolah.logic.commands.CommandTestUtil.BUDGET_DESCRIPTION_DESC_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_BUDGET_DESCRIPTION_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_DESCRIPTION_SCHOOL;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.budget.DeleteBudgetByNameCommand;
import seedu.moolah.model.expense.Description;

public class DeleteBudgetByNameCommandParserTest {
    private DeleteBudgetByNameCommandParser parser = new DeleteBudgetByNameCommandParser();

    @Test
    void parse_blankArgument_parseException() {
        assertParseFailure(
                parser,
                "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBudgetByNameCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_nullArgument_nullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBudgetByNameCommand.MESSAGE_USAGE);

        // missing description prefix
        assertParseFailure(parser, VALID_BUDGET_DESCRIPTION_SCHOOL, expectedMessage);
    }

    @Test
    public void parse_repeatedPrefix_failure() {
        assertParseFailure(parser, BUDGET_DESCRIPTION_DESC_SCHOOL + BUDGET_DESCRIPTION_DESC_SCHOOL,
                MESSAGE_REPEATED_PREFIX_COMMAND);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(
                parser, INVALID_BUDGET_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    void parse_allFieldsPresent_success() {
        // valid budget name
        Description validInput = new Description(VALID_BUDGET_DESCRIPTION_SCHOOL);
        assertParseSuccess(parser, BUDGET_DESCRIPTION_DESC_SCHOOL,
                new DeleteBudgetByNameCommand(validInput));
    }
}
