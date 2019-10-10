package seedu.address.logic.parser.itinerary.dayview;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.itinerary.days.DeleteDayCommand;
import seedu.address.logic.commands.itinerary.days.EnterCreateDayCommand;
import seedu.address.logic.commands.itinerary.days.EnterDayCommand;
import seedu.address.logic.commands.itinerary.days.EnterEditDayCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

public class DayViewParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EnterCreateDayCommand.COMMAND_WORD + " "
            + DeleteDayCommand.COMMAND_WORD + " "
            + EnterDayCommand.COMMAND_WORD + " "
            + EnterEditDayCommand.COMMAND_WORD;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        DayViewCommand commandType;
        try {
            commandType = DayViewCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case CREATE:
            return new EnterCreateDayParser().parse(arguments);
        case DELETE:
            return new DeleteDayParser().parse(arguments);
        case GOTO:
            return new EnterDayParser().parse(arguments);
        case EDIT:
            return new EnterEditDayParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
