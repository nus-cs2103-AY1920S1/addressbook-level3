package seedu.address.logic.parser.expense;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.expenditure.DeleteExpenditureCommand;
import seedu.address.logic.commands.expenditure.EnterCreateExpenditureCommand;
import seedu.address.logic.commands.expenditure.EnterEditExpenditureCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

/**
 * Parses commands related to the expense manager page.
 */
public class ExpenseManagerParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EnterCreateExpenditureCommand.COMMAND_WORD + " "
            + DeleteExpenditureCommand.COMMAND_WORD + " "
            + EnterEditExpenditureCommand.COMMAND_WORD;

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
            return new EnterCreateExpenditureParser().parse(arguments);
        case DELETE:
            return new DeleteExpenditureParser().parse(arguments);
        case EDIT:
            return new EnterEditExpenditureParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
