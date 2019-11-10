package seedu.address.logic.parser.expense;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.currency.EnterCreateCurrencyCommand;
import seedu.address.logic.commands.expense.DeleteExpenseCommand;
import seedu.address.logic.commands.expense.EnterCreateExpenseCommand;
import seedu.address.logic.commands.expense.EnterDayOfExpenseCommand;
import seedu.address.logic.commands.expense.EnterDaysViewCommand;
import seedu.address.logic.commands.expense.EnterEditExpenseCommand;
import seedu.address.logic.commands.expense.EnterListViewCommand;
import seedu.address.logic.commands.expense.SortExpensesCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.currency.EnterCreateCurrencyParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.navbar.NavbarViewParser;

/**
 * Parses commands related to the expense manager page.
 */
public class ExpenseManagerParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EnterCreateExpenseCommand.COMMAND_WORD + " "
            + DeleteExpenseCommand.COMMAND_WORD + " "
            + EnterEditExpenseCommand.COMMAND_WORD + " "
            + EnterDayOfExpenseCommand.COMMAND_WORD + " "
            + EnterDaysViewCommand.COMMAND_WORD + " "
            + EnterListViewCommand.COMMAND_WORD + " "
            + EnterCreateCurrencyCommand.COMMAND_WORD + " "
            + SortExpensesCommand.COMMAND_WORD + " | "
            + NavbarViewParser.MESSAGE_COMMAND_TYPES;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        ExpenseManagerCommand commandType;
        try {
            commandType = ExpenseManagerCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case CREATE:
            return new EnterCreateExpenseParser().parse(arguments);
        case DELETE:
            return new DeleteExpenseParser().parse(arguments);
        case EDIT:
            return new EnterEditExpenseParser().parse(arguments);
        case SHOWDAYS:
            return new EnterDaysViewParser().parse(arguments);
        case SHOWLIST:
            return new EnterListViewParser().parse(arguments);
        case CURRENCY:
            return new EnterCreateCurrencyParser().parse(arguments);
        case GOTO:
            return new EnterDayOfExpenseParser().parse(arguments);
        case SORT:
            return new SortExpensesParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
