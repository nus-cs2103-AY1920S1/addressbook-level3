package seedu.address.logic.parser.currency;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.currency.CancelEditCurrencyCommand;
import seedu.address.logic.commands.currency.DeleteCurrencyCommand;
import seedu.address.logic.commands.currency.DoneEditCurrencyCommand;
import seedu.address.logic.commands.currency.EditCurrencyFieldCommand;
import seedu.address.logic.commands.currency.SelectCurrencyCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * The page parser for parsing the currency page commands.
 */
public class EditCurrencyParser implements PageParser<Command> {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EditCurrencyFieldCommand.COMMAND_WORD + " "
            + DoneEditCurrencyCommand.COMMAND_WORD + " "
            + CancelEditCurrencyCommand.COMMAND_WORD + " "
            + DeleteCurrencyCommand.COMMAND_WORD + " "
            + SelectCurrencyCommand.COMMAND_WORD + " ";

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        EditCurrencyCommand commandType;
        try {
            commandType = EditCurrencyCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case EDIT:
            return new EditCurrencyFieldParser().parse(arguments);
        case ADD:
            return new DoneEditCurrencyParser().parse(arguments);
        case RETURN:
            return new CancelEditCurrencyParser().parse(arguments);
        case DELETE:
            return new DeleteCurrencyParser().parse(arguments);
        case SELECT:
            return new SelectCurrencyParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
