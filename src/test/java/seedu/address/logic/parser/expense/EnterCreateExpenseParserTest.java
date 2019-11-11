package seedu.address.logic.parser.expense;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.expense.EnterCreateExpenseCommand;

class EnterCreateExpenseParserTest {
    private EnterCreateExpenseParser parser = new EnterCreateExpenseParser();

    @Test
    public void parse_validUserInput_success() {
        assertParseSuccess(parser, "", new EnterCreateExpenseCommand());
    }

}
