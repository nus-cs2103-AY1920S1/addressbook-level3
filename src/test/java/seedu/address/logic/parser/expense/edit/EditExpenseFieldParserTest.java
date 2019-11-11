package seedu.address.logic.parser.expense.edit;

import static seedu.address.logic.commands.expense.edit.EditExpenseFieldCommand.COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_NUMBER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.expense.edit.EditExpenseFieldCommand;
import seedu.address.model.expense.DayNumber;
import seedu.address.model.itinerary.Budget;


class EditExpenseFieldParserTest {
    private EditExpenseFieldParser parser = new EditExpenseFieldParser();


    @Test
    public void parseValidBudget_success() {
        EditExpenseFieldCommand.EditExpenseDescriptor editExpenseDescriptor =
                new EditExpenseFieldCommand.EditExpenseDescriptor();
        editExpenseDescriptor.setBudget(new Budget(123));
        assertParseSuccess(parser, COMMAND_WORD + " " + PREFIX_BUDGET + "123",
                new EditExpenseFieldCommand(editExpenseDescriptor));
    }


    @Test
    public void parseInvalidBudget_failure() {
        EditExpenseFieldCommand.EditExpenseDescriptor editExpenseDescriptor =
                new EditExpenseFieldCommand.EditExpenseDescriptor();
        assertParseFailure(parser, COMMAND_WORD + " " + PREFIX_BUDGET + "asdf",
                Budget.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parseValidDayNumber_success() {
        EditExpenseFieldCommand.EditExpenseDescriptor editExpenseDescriptor =
                new EditExpenseFieldCommand.EditExpenseDescriptor();
        editExpenseDescriptor.setDayNumber(new DayNumber("12"));
        assertParseSuccess(parser, COMMAND_WORD + " " + PREFIX_DAY_NUMBER + "12",
                new EditExpenseFieldCommand(editExpenseDescriptor));
    }


    @Test
    public void parseInvalidDayNumber_failure() {
        EditExpenseFieldCommand.EditExpenseDescriptor editExpenseDescriptor =
                new EditExpenseFieldCommand.EditExpenseDescriptor();
        assertParseFailure(parser, COMMAND_WORD + " " + PREFIX_DAY_NUMBER + "asdf",
                DayNumber.MESSAGE_CONSTRAINTS);
    }

}
