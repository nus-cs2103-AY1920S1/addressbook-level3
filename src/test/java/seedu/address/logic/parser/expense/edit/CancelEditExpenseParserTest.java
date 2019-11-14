package seedu.address.logic.parser.expense.edit;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.expense.edit.CancelEditExpenseCommand;

class CancelEditExpenseParserTest {
    private CancelEditExpenseParser parser = new CancelEditExpenseParser();

    @Test
    public void parse_validIndex_success() {
        assertParseSuccess(parser, "", new CancelEditExpenseCommand());
    }
}
