package seedu.moolah.logic.parser.expense;

import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.moolah.logic.commands.CommandTestUtil.EXPENSE_MENU_DESC_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EXPENSE_MENU_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EXPENSE_MENU_TIMESTAMP_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EXPENSE_TIMESTAMP_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.expense.AddMenuExpenseCommand;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.menu.MenuItem;

public class AddMenuExpenseCommandParserTest {

    private AddMenuExpenseCommandParser parser = new AddMenuExpenseCommandParser();

    // AddMenuExpenseCommand should never return a same command as another due to generation of
    // UUID of the expense to be added.

    @Test
    public void parse_requiredPrefixesMissing_failure() {
        // missing menu prefix
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMenuExpenseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMenuExpenseCommand.MESSAGE_USAGE));

        // invalid menu
        assertParseFailure(parser, INVALID_EXPENSE_MENU_DESC, MenuItem.MESSAGE_CONSTRAINTS);

        // invalid timestamp
        assertParseFailure(parser,
                EXPENSE_MENU_DESC_CHICKEN + INVALID_EXPENSE_TIMESTAMP_DESC,
                Timestamp.MESSAGE_CONSTRAINTS_GENERAL);

        // invalid menu and timestamp, but only invalid menu item is reported
        assertParseFailure(parser, INVALID_EXPENSE_MENU_DESC + INVALID_EXPENSE_TIMESTAMP_DESC,
                MenuItem.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_repeatedPrefix_failure() {
        assertParseFailure(parser, EXPENSE_MENU_DESC_CHICKEN + EXPENSE_MENU_DESC_CHICKEN,
                MESSAGE_REPEATED_PREFIX_COMMAND);
    }

    @Test
    public void parse_futureTimestamp_failure() {
        assertParseFailure(parser,
            EXPENSE_MENU_DESC_CHICKEN + INVALID_EXPENSE_MENU_TIMESTAMP_DESC,
            "Expense cannot be set in the future");
    }

}
