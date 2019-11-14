package seedu.address.logic.parser.expense.edit;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.expense.edit.DoneEditExpenseCommand;

class DoneEditExpenseParserTest {
    private DoneEditExpenseParser parser = new DoneEditExpenseParser();

    @Test
    public void parse_validUserInput_success() {
        assertParseSuccess(parser,
                "1", new DoneEditExpenseCommand());
    }

}
