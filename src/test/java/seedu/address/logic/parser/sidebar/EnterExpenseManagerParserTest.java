package seedu.address.logic.parser.sidebar;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.sidebar.EnterExpenseManagerCommand;

class EnterExpenseManagerParserTest {
    private EnterExpenseManagerParser parser = new EnterExpenseManagerParser();

    @Test
    public void parse_validUserInput_success() {
        assertParseSuccess(parser, "", new EnterExpenseManagerCommand());
    }

}
