package seedu.address.logic.parser.expense.edit;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.expense.edit.CancelEditExpenseCommand;
import seedu.address.logic.commands.expense.edit.DoneEditExpenseCommand;
import seedu.address.logic.commands.expense.edit.EditExpenseFieldCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses commands related to the expense setup page.
 */
public class EditExpenseParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EditExpenseFieldCommand.COMMAND_WORD + " "
            + DoneEditExpenseCommand.COMMAND_WORD + " "
            + CancelEditExpenseCommand.COMMAND_WORD;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        EditExpenseCommand commandType;
        try {
            commandType = EditExpenseCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case EDIT:
            return new EditExpenseFieldParser().parse(arguments);
        case DONE:
            return new DoneEditExpenseParser().parse(arguments);
        case CANCEL:
            return new CancelEditExpenseParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }

}
