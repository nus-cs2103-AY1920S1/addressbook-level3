package seedu.address.logic.parser.expense.edit;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.expenditure.edit.CancelEditExpenditureCommand;
import seedu.address.logic.commands.expenditure.edit.DoneEditExpenditureCommand;
import seedu.address.logic.commands.expenditure.edit.EditExpenditureFieldCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

/**
 * Placeholder javadoc.
 */
public class EditExpenditureParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EditExpenditureFieldCommand.COMMAND_WORD + " "
            + DoneEditExpenditureCommand.COMMAND_WORD + " "
            + CancelEditExpenditureCommand.COMMAND_WORD;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        EditExpenditureCommand commandType;
        try {
            commandType = EditExpenditureCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case EDIT:
            return new EditExpenditureFieldParser().parse(arguments);
        case DONE:
            return new DoneEditExpenditureParser().parse(arguments);
        case CANCEL:
            return new CancelEditExpenditureParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }

}
